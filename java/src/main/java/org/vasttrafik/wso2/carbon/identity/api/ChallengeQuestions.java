package org.vasttrafik.wso2.carbon.identity.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.impl.ChallengequestionsApiServiceImpl;
import org.vasttrafik.wso2.carbon.identity.api.utils.IdentityResourceBundleAware;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 *
 */
@Path("/challengequestions")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public final class ChallengeQuestions {

   private final ChallengequestionsApiServiceImpl delegate = new ChallengequestionsApiServiceImpl();

    @GET
    public Response getChallengequestions(
    		@HeaderParam("Accept") final String accept,
    		@NotNull(message= "{param.username.notnull}") @QueryParam("username") final String username,
			@QueryParam("queryId") final String queryId,
			@NotNull(message= "{param.confirmation.notnull}") @QueryParam("confirmation") final String confirmation) 
    	throws ClientErrorException
    {
		if (queryId != null && !queryId.equals(""))
			return delegate.getChallengequestion(queryId, username, confirmation);
		else
			return delegate.getChallengequestions(username, confirmation);
    }
	
	@POST
    public Response setChallengequestion(
    		@HeaderParam("Accept") final String accept,
			@HeaderParam("X-JWT-Assertion") final String authorization,
			@NotNull(message= "{param.userid.notnull}") @QueryParam("userId") final Integer userId,
			@NotNull(message= "{question.notnull}") @Valid final ChallengeQuestion question) 
    	throws ClientErrorException
    {
        return delegate.setChallengequestion(authorization, userId, question);
    }
	
    @PUT
    public Response verifyAnswer(
    		@PathParam("id") final String id, 
    		@HeaderParam("Accept") final String accept, 
    		@HeaderParam("Content-Type") final String contentType, 
    		@NotNull(message= "{answer.notnull}") @Valid final ChallengeAnswer answer)
    	throws ClientErrorException
    {	
        return delegate.verifyAnswer(answer);
    }
}

