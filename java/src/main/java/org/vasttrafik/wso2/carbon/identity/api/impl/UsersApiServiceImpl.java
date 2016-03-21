package org.vasttrafik.wso2.carbon.identity.api.impl;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;



import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.client.UserAdminClient;
import org.vasttrafik.wso2.carbon.common.api.beans.AuthenticatedUser;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

/**
 * @author Lars Andersson
 *
 */
public class UsersApiServiceImpl extends IdentityMgmtApiServiceImpl {
	
	private UserAdminClient client = new UserAdminClient();
  
	/**
	 * Confirms a user registration
	 * @param confirmation the confirmation information
	 * @return the verification result, indicating if the user was confirmed or not
	 * @throws Exception if an error occurs
	 */
	public Response confirmUserRegistration(UserConfirmation confirmation) 
		throws ServerErrorException, NotFoundException
	{
		try {
			Verification verification = client.confirmUserRegistration(confirmation);
			return Response.ok(verification).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
  
	/**
	 * Registers a user
	 * @param user the user to register
	 * @return a verification indicating if the user ws registered or not
	 * @throws Exception if an error occurs
	 */
	public Response registerUser(User user) 
		throws ServerErrorException
	{
		try {
			Verification verification = client.registerUser(user);
			return Response.ok(verification).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
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
	public Response getUser(Integer userId, String authorization) 
		throws ServerErrorException
	{
		try {
			AuthenticatedUser authenticatedUser = authorize(authorization);
			
			if (!isOwnerOrAdmin(userId))
				throw new Exception("User id in JWT token does not match the user id in request");
			
			User user = client.getUser(userId, authenticatedUser.getUserName());
			return Response.ok(user).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
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
	public Response updateUser(Integer userId, String action, String authorization, User user) 
		throws ForbiddenException, ServerErrorException, NotAuthorizedException, NotFoundException
	{
		try {
			AuthenticatedUser authenticatedUser = authorize(authorization);
			
			if (!isOwnerOrAdmin(userId))
				throw new Exception("User id in JWT token does not match the user id in request");
			
			user.setUserName(authenticatedUser.getUserName());
			
			Verification verification = client.updateUser(action, user);
			return Response.ok(verification).build();
		}
		catch (ForbiddenException ne) {
			throw ne;
		}
		catch (NotFoundException ne) {
			throw ne;
		}
		catch (NotAuthorizedException nae) {
			throw nae;
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
}
