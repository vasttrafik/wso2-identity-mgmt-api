package org.vasttrafik.wso2.carbon.identity.api.client;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.identity.api.beans.User;
import org.vasttrafik.wso2.carbon.identity.api.beans.UserClaim;
import org.vasttrafik.wso2.carbon.identity.api.beans.UserConfirmation;
import org.vasttrafik.wso2.carbon.identity.api.beans.Verification;
import org.vasttrafik.wso2.carbon.identity.api.utils.ClientUtils;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;
import org.vasttrafik.wso2.carbon.identity.api.utils.UserAdminUtils;

import org.wso2.carbon.captcha.mgt.beans.xsd.CaptchaInfoBean;
import org.wso2.carbon.identity.mgt.stub.beans.VerificationBean;
import org.wso2.carbon.identity.mgt.stub.dto.UserIdentityClaimDTO;
import org.wso2.carbon.um.ws.api.stub.ClaimDTO;
import org.wso2.carbon.um.ws.api.stub.ClaimValue;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceStub;

/**
 * @author Lars Andersson
 *
 */
public final class UserAdminClient extends UserInformationRecoveryClient {
	
	private RemoteUserStoreManagerServiceStub userStoreStub = ClientUtils.getRemoteUserStoreManagerServiceStub();
	
	//private static final String resourceBundle = "org.vasttrafik.se.carbon.identity.api.utils.IdentityErrorListResourceBundle";
	
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
				ResponseUtils.preconditionFailed(resourceBundle, 1002L, new Object[][]{{},{username}});
				
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
	public User getUser(Integer userId, String authorization, String ifNoneMatch, String ifModifiedSince) throws Exception {
		ClientUtils.authenticateIfNeeded(userStoreStub._getServiceClient());
		
		try {
			// Validate the token and get the username
			String userName = UserAdminUtils.validateToken(userId, authorization);
			// Get the user profile
			ClaimDTO[] dtos = userStoreStub.getUserClaimValues(userName, "default");
			
			return getUserFromClaimDTO(userId, userName, dtos);
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
	public Verification updateUser(Integer userId, String action, String authorization, User user) throws Exception {
		Verification verification = null;
		
		if ("updatePassword".equalsIgnoreCase(action) || "updateProfile".equalsIgnoreCase(action)) {
			// Validate the token and get the username
			String userName = UserAdminUtils.validateToken(userId, authorization);
			user.setUserName(userName);
				
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
		
		try {
			userStoreStub.updateCredential(
				user.getUserName(), 
				user.getPassword().getPassword(), 
				user.getPassword().getNewPassword());
			
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
		
		try {
			ClaimValue[] claims = getClaimsFromUserClaims(user.getClaims());
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
	
	protected User getUserFromClaimDTO(Integer userId, String username, ClaimDTO[] dtos) {
		User user = new User();
		user.setId(userId);
		user.setUserName(username);
		// TO-DO: How to retrieve last modified date?
		//user.setLastModifiedDate(lastModifiedDate);
		user.setProfileName("default");
		user.setTenantDomain("carbon.super");
		
		List<UserClaim> claims = new ArrayList<UserClaim>(dtos.length);
		
		for (int i = 0; i < dtos.length; i++) {
			UserClaim claim = getClaimFromDTO(dtos[i]);
			claims.add(claim);
		}
		
		user.setClaims(claims);
		return user;
	}
	
	protected UserClaim getClaimFromDTO(ClaimDTO dto) {
		UserClaim claim = new UserClaim();
		claim.setClaimUri(dto.getClaimUri());
		claim.setClaimValue(dto.getValue());
		claim.setDescription(dto.getDescription());
		claim.setDialectURI(dto.getDialectURI());
		claim.setDisplayOrder(dto.getDisplayOrder());
		claim.setDisplayTag(dto.getDisplayTag());
		claim.setRegEx(dto.getRegEx());
		claim.setRequired(String.valueOf(dto.getRequired()));
		claim.setSupportedByDefault(String.valueOf(dto.getSupportedByDefault()));
		return claim;
	}
}
