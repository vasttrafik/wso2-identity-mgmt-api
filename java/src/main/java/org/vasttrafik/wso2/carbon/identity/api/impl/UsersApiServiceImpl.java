package org.vasttrafik.wso2.carbon.identity.api.impl;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.client.UserAdminClient;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 * @author Lars Andersson
 *
 */
public class UsersApiServiceImpl {
	
	private UserAdminClient client = new UserAdminClient();
  
	/**
	 * Confirms a user registration
	 * @param confirmation the confirmation information
	 * @return the verification result, indicating if the user was confirmed or not
	 * @throws Exception if an error occurs
	 */
	public Response confirmUserRegistration(UserConfirmation confirmation) 
		throws InternalServerErrorException, NotFoundException
	{
		try {
			Verification verification = client.confirmUserRegistration(confirmation);
			return Response.ok(verification).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new InternalServerErrorException(response);
		}
	}
  
	/**
	 * Registers a user
	 * @param user the user to register
	 * @return a verification indicating if the user ws registered or not
	 * @throws Exception if an error occurs
	 */
	public Response registerUser(User user) 
		throws InternalServerErrorException
	{
		try {
			Verification verification = client.registerUser(user);
			return Response.ok(verification).build();
		}
		catch (BadRequestException be) {
			throw be;
		}
		catch (ClientErrorException ce) {
			throw ce;
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new InternalServerErrorException(response);
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
		throws InternalServerErrorException, NotAuthorizedException, NotFoundException
	{
		try {
			User user = client.getUser(userId, authorization);
			return Response.ok(user).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			Response response = ResponseUtils.serverError(e);
			throw new InternalServerErrorException(response);
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
		throws InternalServerErrorException, NotAuthorizedException, NotFoundException
	{
		try {
			Verification verification = client.updateUser(userId, action, authorization, user);
			return Response.ok(verification).build();
		}
		catch (NotFoundException ne) {
			throw ne;
		}
		catch( ClientErrorException ce) {
			throw ce;
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new InternalServerErrorException(response);
		}
	}
}
