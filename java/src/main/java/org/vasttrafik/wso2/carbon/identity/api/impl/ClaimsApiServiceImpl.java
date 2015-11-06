package org.vasttrafik.wso2.carbon.identity.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.vasttrafik.wso2.carbon.identity.api.beans.UserClaim;
import org.vasttrafik.wso2.carbon.identity.api.client.ClaimManagementClient;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;

/**
 * @author Lars Andersson
 *
 */
public class ClaimsApiServiceImpl {
  
	/**
	 * Retrieves user claims
	 * @param dialect the claim dialect
	 * @param claimType the type of claims to retrieve, which can be user (all user claims) or identity
	 * claims
	 * @return a list of claims
	 * @throws Exception if an error occurs
	 */
	public Response getClaims(String dialect, String claimType) throws InternalServerErrorException
	{
		ClaimManagementClient client = new ClaimManagementClient();
	
		try {
			List<UserClaim> claims = new ArrayList<UserClaim>();
			
			if ("user".equalsIgnoreCase(claimType))
				claims = client.getUserClaims(dialect);
			else if ("identity".equalsIgnoreCase(claimType))
				claims = client.getUserIdentityClaims(dialect);
			
			return Response.ok(claims).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new InternalServerErrorException(response);
		}
	}
}
