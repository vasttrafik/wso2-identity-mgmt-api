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
    		@QueryParam("confirmation") String confirmation) 
    	throws BadRequestException, InternalServerErrorException, NotAuthorizedException
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
	
    @GET
    @Path("/{id}")
    public Response getChallengequestion(
    		@PathParam("id") String id, 
    		@HeaderParam("Accept") String accept, 
    		@QueryParam("username") String username, 
    		@QueryParam("confirmation") String confirmation)
    	throws BadRequestException, InternalServerErrorException, NotAuthorizedException, NotFoundException
    {
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"username", 
    			true, 
    			new String[]{}, 
    			username);
    	
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"confirmation", 
    			true, 
    			new String[]{}, 
    			confirmation);
    	
        return delegate.getChallengequestion(id, username, confirmation);
    }
	
    @POST
    @Path("/{id}/answers")
    public Response verifyAnswer(
    		@PathParam("id") String id, 
    		@HeaderParam("Accept") String accept, 
    		@HeaderParam("Content-Type") String contentType, 
    		ChallengeAnswer answer)
    	throws InternalServerErrorException, NotFoundException
    {
        return delegate.verifyAnswer(id, answer);
    }
}

