package org.vasttrafik.wso2.carbon.identity.api.client;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.identity.api.beans.User;
import org.vasttrafik.wso2.carbon.identity.api.beans.UserClaim;
import org.vasttrafik.wso2.carbon.identity.api.beans.UserConfirmation;
import org.vasttrafik.wso2.carbon.identity.api.beans.Verification;
import org.vasttrafik.wso2.carbon.common.api.utils.ClientUtils;

import org.wso2.carbon.captcha.mgt.beans.xsd.CaptchaInfoBean;
import org.wso2.carbon.identity.mgt.stub.beans.VerificationBean;
import org.wso2.carbon.identity.mgt.stub.dto.UserIdentityClaimDTO;
import org.wso2.carbon.identity.user.profile.stub.types.UserFieldDTO;
import org.wso2.carbon.identity.user.profile.stub.types.UserProfileDTO;
import org.wso2.carbon.identity.user.profile.stub.UserProfileMgtServiceStub;
import org.wso2.carbon.um.ws.api.stub.ClaimValue;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceStub;

import javax.ws.rs.*;
/**
 * @author Lars Andersson
 *
 */
public final class UserAdminClient extends UserInformationRecoveryClient {
	
	private RemoteUserStoreManagerServiceStub userStoreStub = ClientUtils.getRemoteUserStoreManagerServiceStub();
	private UserProfileMgtServiceStub profileMgtStub = ClientUtils.getProfileManagementServiceStub();
	
	private static final String emailClaimURI = "http://wso2.org/claims/emailaddress";
	
	public UserAdminClient() {
		super();
	}
	
	/**
	 * Confirms a user registration
	 * @param confirmation the confirmation information
	 * @return the verification result, indicating if the user was confirmed or not
	 * @throws Exception if an error occurs
	 */
	public Verification confirmUserRegistration(UserConfirmation confirmation) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		ClientUtils.authenticateIfNeeded(userStoreStub._getServiceClient());
		
		try {
			CaptchaInfoBean captchaBean = getCaptchaInfoBeanFromCaptcha(confirmation.getCaptcha());
			VerificationBean bean = recoveryStub.confirmUserSelfRegistration(
					confirmation.getUsername(), 
					confirmation.getCode(), 
					captchaBean, 
					confirmation.getTenantDomain());
				
			String[] roles = new String[]{"Internal/subscriber"};
			
			userStoreStub.updateRoleListOfUser(
					confirmation.getUsername(), 
					new String[0], 
					roles);
			
			return getVerificationFromBean(bean);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Registers a user
	 * @param user the user to register
	 * @return a verification indicating if the user ws registered or not
	 * @throws Exception if an error occurs
	 */
	public Verification registerUser(User user) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		try {
			String username = user.getUserName();
			
			if (isExistingUser(username))
				responseUtils.preconditionFailed(1002L, new Object[][]{{},{username}});
				
			String emailAddress = getUserEmail(user);
			
			if (isExistingAccount(emailAddress))
				responseUtils.preconditionFailed(1007L, new Object[][]{{},{emailAddress}});
				
			UserIdentityClaimDTO[] identityClaims = getUserIdentityClaimsFromUserClaims(user.getClaims());
			VerificationBean bean = recoveryStub.registerUser(
					user.getUserName(), 
					user.getPassword().getPassword(), 
					identityClaims, 
					user.getProfileName(), 
					user.getTenantDomain());
			
			return getVerificationFromBean(bean);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Retrieves the user profile of  user
	 * @param userId the user id
	 * @param authorization an OAuth2 authorization header
	 * @param ifNoneMatch the If-None-Match http header
	 * @param ifModifiedSince the If-Modified-Since http header
	 * @return the user 
	 * @throws Exception if an error occurs
	 */
	public User getUser(Integer userId, String userName) throws Exception {
		ClientUtils.authenticateIfNeeded(profileMgtStub._getServiceClient());

		try {
			if ("admin".equalsIgnoreCase(userName))
				throw new ForbiddenException();
			
			// Get the user profile
			UserProfileDTO profile = profileMgtStub.getUserProfile(userName, "default");
			return getUserFromProfile(userId, userName, profile);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Updates a user account
	 * @param userId the user id
	 * @param action what part of the user account to update, using updateProfile to indicate that the user profile should
	 * be updated, updatePassword to indicate that the password should be updated and recoverPassword to indicate that the
	 * password should be recovered.
	 * @param authorization an OAuth2 authorization header
	 * @param user the user profile to update
	 * @return a verification indicating if the user objects was updated or not
	 * @throws Exception if an error occurs
	 */
	public Verification updateUser(String action, User user) throws Exception {
		Verification verification = null;
		
		if ("admin".equalsIgnoreCase(user.getUserName()))
			throw new ForbiddenException();
		
		if ("updatePassword".equalsIgnoreCase(action) || "updateProfile".equalsIgnoreCase(action)) {
			if ("updatePassword".equalsIgnoreCase(action))
				verification = updatePassword(user);
			else
				verification = updateUserProfile(user);
		}
		else if ("recoverPassword".equals(action)) {
			verification = recoverPassword(user);
		}
		return verification;
	}
	
	protected Verification updatePassword(User user) throws Exception {
		ClientUtils.authenticateIfNeeded(userStoreStub._getServiceClient());
		
		if ("admin".equalsIgnoreCase(user.getUserName()))
			throw new ForbiddenException();
		
		try {
			userStoreStub.updateCredential(
				user.getUserName(), 
				user.getPassword().getNewPassword(),
				user.getPassword().getPassword());
			
			Verification verification = new Verification();
			verification.setUserId(String.valueOf(user.getId()));
			verification.setVerified(true);
			return verification;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	protected Verification updateUserProfile(User user) throws Exception {
		ClientUtils.authenticateIfNeeded(userStoreStub._getServiceClient());
		ClientUtils.authenticateIfNeeded(profileMgtStub._getServiceClient());
		
		if ("admin".equalsIgnoreCase(user.getUserName()))
			throw new ForbiddenException();
		
		try {
			// Get the user profile
			UserProfileDTO profile = profileMgtStub.getUserProfile(user.getUserName(), "default");
			
			if (profile == null)
				throw new NotFoundException();
				
			// Get the field values
			UserFieldDTO[] fields = profile.getFieldValues();
			// Get the current email address
			String currentEmailAddress = getUserEmailClaimValue(fields);
			// Get the new claims
			ClaimValue[] claims = getClaimsFromUserClaims(user.getClaims());
			// Get the new email address
			String newEmailAddress = getUserEmailClaimValue(claims);
			
			if (currentEmailAddress == null || !currentEmailAddress.equalsIgnoreCase(newEmailAddress)) {
				if (isExistingAccount(newEmailAddress))
					responseUtils.preconditionFailed(1007L, new Object[][]{{},{newEmailAddress}});
			}
			
			userStoreStub.setUserClaimValues(
					user.getUserName(), 
					claims, 
					user.getProfileName());
			
			Verification verification = new Verification();
			verification.setUserId(String.valueOf(user.getId()));
			verification.setVerified(true);
			return verification;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	protected Verification recoverPassword(User user) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		if ("admin".equalsIgnoreCase(user.getUserName()))
			throw new ForbiddenException();
		
		try {
			VerificationBean bean = recoveryStub.updatePassword(
					user.getUserName(), 
					user.getPassword().getConfirmationCode(), 
					user.getPassword().getNewPassword());
			
			return getVerificationFromBean(bean);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	protected String getUserEmailClaimValue(ClaimValue[] claims) {
		if (claims != null) {
			for (ClaimValue claim : claims) 
				if ("http://wso2.org/claims/emailaddress".equals(claim.getClaimURI()))
					return claim.getValue();
		}
		return null;
	}
	
	protected String getUserEmailClaimValue(UserFieldDTO[] claims) {
		if (claims != null) {
			for (UserFieldDTO claim : claims) 
				if ("http://wso2.org/claims/emailaddress".equals(claim.getClaimUri()))
					return claim.getFieldValue();
		}
		return null;
	}
	
	protected boolean isExistingUser(String userName) throws Exception {
		ClientUtils.authenticateIfNeeded(userStoreStub._getServiceClient());
		
        try {
            return userStoreStub.isExistingUser(userName);
        } 
        catch (Exception e) {
        	e.printStackTrace();
			throw e;
        }
    }
	
	protected boolean isExistingAccount(String emailAddress) throws Exception {
		ClientUtils.authenticateIfNeeded(userStoreStub._getServiceClient());
		
        try {
            String[] users = userStoreStub.getUserList(emailClaimURI, emailAddress, "default");
			return (users != null && users.length > 0);
        } 
        catch (Exception e) {
        	e.printStackTrace();
			throw e;
        }
	}
	
	protected String getUserEmail(User user) {
		List<UserClaim> claims = user.getClaims();
		
		for (UserClaim claim : claims) {
			String claimURI = claim.getClaimUri();
			
			if (emailClaimURI.equals(claimURI))
				return claim.getClaimValue();
		}
		return "";
	}
	
	protected ClaimValue[] getClaimsFromUserClaims(List<UserClaim> userClaims) {
		ClaimValue[] claims = new ClaimValue[userClaims.size()];
		
		for (int i = 0; i < claims.length; i++) {
			UserClaim userClaim = userClaims.get(i);
			claims[i] = new ClaimValue();
			claims[i].setClaimURI(userClaim.getClaimUri());
			claims[i].setValue(userClaim.getClaimValue());
		}
		return claims;
	}
	
	protected UserIdentityClaimDTO[] getUserIdentityClaimsFromUserClaims(List<UserClaim> userClaims) {
		UserIdentityClaimDTO[] identityClaims = new UserIdentityClaimDTO[userClaims.size()];
		
		for (int i = 0; i < userClaims.size(); i++) {
			UserClaim claim = userClaims.get(i);
			identityClaims[i] = new UserIdentityClaimDTO();
			identityClaims[i].setClaimUri(claim.getClaimUri());
			identityClaims[i].setClaimValue(claim.getClaimValue());
		}
		return identityClaims;
	}
	
	protected User getUserFromProfile(Integer userId, String username, UserProfileDTO profile) {
		User user = new User();
		user.setId(userId);
		user.setUserName(username);
		user.setProfileName("default");
		user.setTenantDomain("carbon.super");
		
		
		UserFieldDTO[] fields = profile.getFieldValues();
		List<UserClaim> claims = new ArrayList<UserClaim>(fields.length);
		
		for (int i = 0; i < fields.length; i++) {
			UserClaim claim = getClaimFromField(fields[i]);
			claims.add(claim);
		}
		
		user.setClaims(claims);
		return user;
	}
	
	protected UserClaim getClaimFromField(UserFieldDTO dto) {
		UserClaim claim = new UserClaim();
		claim.setClaimUri(dto.getClaimUri());
		claim.setClaimValue(dto.getFieldValue());
		claim.setDescription(dto.getDisplayName());
		//claim.setDialectURI(dto.getDialectURI());
		claim.setDisplayOrder(dto.getDisplayOrder());
		claim.setDisplayTag(dto.getDisplayName());
		claim.setRegEx(dto.getRegEx());
		claim.setRequired(String.valueOf(dto.getRequired()));
		claim.setSupportedByDefault("true");
		return claim;
	}
}
