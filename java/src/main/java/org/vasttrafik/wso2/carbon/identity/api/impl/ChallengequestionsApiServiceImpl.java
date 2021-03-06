package org.vasttrafik.wso2.carbon.identity.api.impl;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.client.ChallengeQuestionsClient;
import org.vasttrafik.wso2.carbon.common.api.beans.AuthenticatedUser;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

/**
 * @author Lars Andersson
 *
 */
public class ChallengequestionsApiServiceImpl extends IdentityMgmtApiServiceImpl {
	
	private ChallengeQuestionsClient client = new ChallengeQuestionsClient();

	/**
	 * Retrieves all challenge questions available
	 * @return a list of challenge questions
	 * @throws Exception if an error occurs
	 */
	public Response getAllChallengequestions()
		  throws NotAuthorizedException, ServerErrorException
	{
		try {
			List<ChallengeQuestion> questions = client.getAllChallengequestions();
			return Response.ok(questions).build();
		}
		catch (NotAuthorizedException na) {
			Response response = Response.status(401).build();
			throw new NotAuthorizedException(response);
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	/**
	 * Retrieves all challenge questions of a user
	 * @param username the username of the user 
	 * @param confirmation a confirmation code
	 * @return a list of challenge questions
	 * @throws Exception if an error occurs
	 */
	public Response getChallengequestions(String username, String confirmation)
		  throws NotAuthorizedException, ServerErrorException
	{
		try {
			List<ChallengeQuestion> questions = client.getChallengequestions(username, confirmation);
			return Response.ok(questions).build();
		}
		catch (NotAuthorizedException na) {
			Response response = Response.status(401).build();
			throw new NotAuthorizedException(response);
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
  
	/**
	 * Retrieves a challenge question.
	 * @param questionId the id of the challenge question to retrieve
	 * @param username the username of the user to which the challenge question belongs
	 * @param confirmation the confirmation code previously retrieved when fetching a list of questions 
	 * @return the challenge question
	 * @throws Exception
	 */
	public Response getChallengequestion(String questionId, String username, String confirmation) 
		throws ServerErrorException, NotFoundException, NotAuthorizedException
	{
		try {
			ChallengeQuestion question = client.getChallengequestion(questionId, username, confirmation);
			return Response.ok(question).build();
		}
		catch (NotAuthorizedException na) {
			Response response = Response.status(401).build();
			throw new NotAuthorizedException(response);
		}
		catch (Exception e) { 
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	/**
	 * Sets a challenge question.
	 * @param authorization the OAuth2 access token
	 * @param username the username of the user to which the challenge question belongs
	 * @param question the challenge question to set
	 * @return the result of the operation
	 * @throws Exception
	 */
	public Response setChallengequestion(String authorization, Integer userId, ChallengeQuestion question) 
		throws ServerErrorException, NotFoundException, NotAuthorizedException
	{
		try {
			AuthenticatedUser user = authorize(authorization);
			
			if (!user.getUserId().equals(userId))
				throw new NotAuthorizedException("JWT token user id does not match the id of the user.");
			
			client.setChallengequestion(user, question);
			return Response.ok().build();
		}
		catch (BadRequestException bre) {
			throw bre;
		}
		catch (NotAuthorizedException na) {
			Response response = Response.status(401).build();
			throw new NotAuthorizedException(response);
		}
		catch (NotFoundException nf) {
			Response response = Response.status(404).build();
			throw new NotFoundException(response);
		}
		catch (Exception e) { 
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
  
	/**
	 * Verifies the answer to a challenge question
	 * @param questionId the id of the challenge question to verify
	 * @param answer the answer to the challenge question
	 * @return the result of the verification
	 * @throws Exception if an error occurs
	 */
	public Response verifyAnswer(ChallengeAnswer answer) 
			throws ServerErrorException, NotFoundException
	{
		try {
			Verification verification = client.verifyAnswer(answer);
			return Response.ok(verification).build();
		}
		catch (NotFoundException nfe) {
			Response response = Response.status(404).build();
			throw new NotFoundException(response);
		}
		catch (Exception e) { 
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
}
