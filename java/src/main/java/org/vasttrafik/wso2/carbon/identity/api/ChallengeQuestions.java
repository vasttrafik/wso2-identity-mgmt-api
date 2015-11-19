package org.vasttrafik.wso2.carbon.identity.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.impl.ChallengequestionsApiServiceImpl;
import org.vasttrafik.wso2.carbon.identity.api.utils.IdentityResourceBundleAware;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 *
 */
@Path("/challengequestions")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ChallengeQuestions implements IdentityResourceBundleAware {

   private final ChallengequestionsApiServiceImpl delegate = new ChallengequestionsApiServiceImpl();

    @GET
    public Response getChallengequestions(
    		@HeaderParam("Accept") String accept,
    		@QueryParam("username") String username,
			@QueryParam("queryId") String queryId,
    		@QueryParam("confirmation") String confirmation) 
    	throws BadRequestException, InternalServerErrorException, NotAuthorizedException, NotFoundException
    {
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"username", 
    			true, 
    			new String[]{}, username);
    	
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"confirmation", 
    			true, 
    			new String[]{}, 
    			confirmation);
    	
		if (queryId != null && !queryId.equals(""))
			return delegate.getChallengequestion(queryId, username, confirmation);
		else
			return delegate.getChallengequestions(username, confirmation);
    }
	
	@POST
    public Response setChallengequestion(
    		@HeaderParam("Accept") String accept,
			@HeaderParam("Authorization") String authorization,
    		@QueryParam("userId") Integer userId,
			ChallengeQuestion question) 
    	throws BadRequestException, InternalServerErrorException, NotAuthorizedException, NotFoundException
    {
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"userId", 
    			true, 
    			new String[]{}, (userId != null ? String.valueOf(userId) : null));
      	
        return delegate.setChallengequestion(authorization, userId, question);
    }
	
    @PUT
    public Response verifyAnswer(
    		@PathParam("id") String id, 
    		@HeaderParam("Accept") String accept, 
    		@HeaderParam("Content-Type") String contentType, 
    		ChallengeAnswer answer)
    	throws BadRequestException, InternalServerErrorException, NotFoundException
    {
		ResponseUtils.checkParameter(
    			resourceBundle,
    			"username", 
    			true, 
    			new String[]{}, answer.getUserName());
				
		ResponseUtils.checkParameter(
    			resourceBundle,
    			"confirmation", 
    			true, 
    			new String[]{}, answer.getConfirmation());
				
		ResponseUtils.checkParameter(
    			resourceBundle,
    			"questionId", 
    			true, 
    			new String[]{}, answer.getQuestionId());
				
		ResponseUtils.checkParameter(
    			resourceBundle,
    			"answer", 
    			true, 
    			new String[]{}, answer.getAnswer());
				
        return delegate.verifyAnswer(answer);
    }
}

