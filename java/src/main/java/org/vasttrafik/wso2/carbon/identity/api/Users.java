package org.vasttrafik.wso2.carbon.identity.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.impl.UsersApiServiceImpl;
import org.vasttrafik.wso2.carbon.identity.api.utils.IdentityResourceBundleAware;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 *
 */
@Path("/users")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON  })
public final class Users {

	private final UsersApiServiceImpl delegate = new UsersApiServiceImpl();

    @PUT
    public Response confirmUserRegistration(
    		@HeaderParam("Accept") final String accept, 
    		@HeaderParam("Content-Type") final String contentType, 
    		@NotNull(message= "{confirmation.notnull}") @Valid final UserConfirmation confirmation)
    	throws ClientErrorException
    {
        return delegate.confirmUserRegistration(confirmation);
    }
	
    @POST
    public Response registerUser(
    		@HeaderParam("Accept") final String accept, 
    		@HeaderParam("Content-Type") final String contentType, 
    		@NotNull(message= "{user.notnull}") @Valid final User user)
    	throws ClientErrorException
    {
        return delegate.registerUser(user);
    }
	
    @GET
    @Path("/{userId}")
    public Response getUser(
    		@PathParam("userId") final Integer userId,
    		@HeaderParam("Accept") final String accept,
    		@HeaderParam("X-JWT-Assertion") final String authorization)
    	throws ClientErrorException
    {
		return delegate.getUser(userId, authorization);
    }
	
    @PUT
    @Path("/{userId}")
    public Response updateUser(
    		@PathParam("userId") final Integer userId, 
    		@NotNull(message= "{param.action.notnull}") @QueryParam("action") final String action, 
    		@HeaderParam("Accept") final String accept,
    		@HeaderParam("X-JWT-Assertion") final String authorization,
    		@HeaderParam("Content-Type") final String contentType, 
    		@NotNull(message= "{user.notnull}") @Valid User user)
    	throws ClientErrorException
    {
    	return delegate.updateUser(userId, action, authorization, user);
    }
}

