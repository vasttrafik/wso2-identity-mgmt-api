package org.vasttrafik.wso2.carbon.identity.api.client;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.identity.api.beans.UserClaim;
import org.vasttrafik.wso2.carbon.common.api.utils.ClientUtils;

import org.wso2.carbon.claim.mgt.stub.ClaimManagementServiceStub;
import org.wso2.carbon.claim.mgt.stub.dto.ClaimDTO;
import org.wso2.carbon.claim.mgt.stub.dto.ClaimDialectDTO;
import org.wso2.carbon.claim.mgt.stub.dto.ClaimMappingDTO;
import org.wso2.carbon.identity.mgt.stub.dto.UserIdentityClaimDTO;

/**
 * @author Lars Andersson
 *
 */
public final class ClaimManagementClient extends UserInformationRecoveryClient {
	
	private ClaimManagementServiceStub claimsStub = ClientUtils.getClaimManagementServiceStub();
	
	public ClaimManagementClient() {
	}
	
	/**
	 * Retrieves user claims. The returned list is filtered to only return claims supported by default.
	 * @param dialect the claim dialect
	 * @return a list of claims
	 * @throws Exception if an error occurs
	 */
	public List<UserClaim> getUserClaims(String dialect) throws Exception {
		ClientUtils.authenticateIfNeeded(claimsStub._getServiceClient());
		
		try {
			List<UserClaim> claims = new ArrayList<UserClaim>();
			
			ClaimDialectDTO dto = claimsStub.getClaimMappingByDialect(dialect);
			ClaimMappingDTO[] mappings = dto.getClaimMappings();
			
			if (mappings.length != 0) {				
				for (ClaimMappingDTO mapping : mappings) {
					ClaimDTO claimDTO = mapping.getClaim();
					
					/**
					 * We use these claims to build a self sign-up form, so only retrieve 
					 * claims supported by default.
					 */
					if (Boolean.valueOf(claimDTO.getSupportedByDefault())) {
						UserClaim claim = getClaimFromDTO(claimDTO);
						claims.add(claim);
					}
				}
			}
			
			return claims;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Retrieves identity related claims
	 * @param dialect the claim dialect
	 * @return a list of claims
	 * @throws Exception if an error occurs
	 */
	public List<UserClaim> getUserIdentityClaims(String dialect) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		try {
			List<UserClaim> claims = new ArrayList<UserClaim>();
			UserIdentityClaimDTO[] identityClaims = recoveryStub.getUserIdentitySupportedClaims(dialect);
			
			if (identityClaims.length != 0) {				
				for (UserIdentityClaimDTO identityClaim : identityClaims) {
					UserClaim claim = getClaimFromIdentityClaimDTO(identityClaim);
					claims.add(claim);
				}
			}
			
			return claims;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
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
	
	protected UserClaim getClaimFromIdentityClaimDTO(UserIdentityClaimDTO identityClaim) {
		UserClaim claim = new UserClaim();
		
		claim.setClaimUri(identityClaim.getClaimUri());
		claim.setClaimValue(identityClaim.getClaimValue());
		return claim;
	}
}
