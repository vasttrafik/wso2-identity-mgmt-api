package org.vasttrafik.wso2.carbon.identity.api;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.impl.UsersApiServiceImpl;
import org.vasttrafik.wso2.carbon.identity.api.utils.IdentityResourceBundleAware;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 *
 */
@Path("/users")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON  })
public class Users implements IdentityResourceBundleAware {

	private final UsersApiServiceImpl delegate = new UsersApiServiceImpl();

    @PUT
    public Response confirmUserRegistration(
    		@HeaderParam("Accept") String accept, 
    		@HeaderParam("Content-Type") String contentType, 
    		UserConfirmation confirmation)
    	throws BadRequestException, InternalServerErrorException, NotFoundException
    {
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"username", 
    			true, 
    			new String[]{}, 
    			confirmation.getUsername());
    	
        return delegate.confirmUserRegistration(confirmation);
    }
	
    @POST
    public Response registerUser(
    		@HeaderParam("Accept") String accept, 
    		@HeaderParam("Content-Type") String contentType, 
    		User user)
    	throws BadRequestException, InternalServerErrorException
    {
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"username", 
    			true, 
    			new String[]{}, 
    			user.getUserName());
    	
        return delegate.registerUser(user);
    }
	
    @GET
    @Path("/{userId}")
    public Response getUser(
    		@PathParam("userId") Integer userId,
    		@HeaderParam("Accept") String accept,
    		@HeaderParam("Authorization") String authorization)
    	throws InternalServerErrorException, NotAuthorizedException, NotFoundException
    {
		return delegate.getUser(userId, authorization);
    }
	
    @PUT
    @Path("/{userId}")
    public Response updateUser(
    		@PathParam("userId") Integer userId, 
    		@QueryParam("action") String action, 
    		@HeaderParam("Accept") String accept,
    		@HeaderParam("Authorization") String authorization,
    		@HeaderParam("Content-Type") String contentType, 
    		User user)
    	throws BadRequestException,  ForbiddenException, InternalServerErrorException, NotAuthorizedException, NotFoundException
    {
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"action", 
    			true, 
    			new String[]{}, 
    			action);
    	
    	return delegate.updateUser(userId, action, authorization, user);
    }
}

