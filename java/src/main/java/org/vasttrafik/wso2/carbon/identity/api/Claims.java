package org.vasttrafik.wso2.carbon.identity.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.vasttrafik.wso2.carbon.identity.api.impl.ClaimsApiServiceImpl;
import org.vasttrafik.wso2.carbon.identity.api.utils.IdentityResourceBundleAware;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 *
 */
@Path("/claims")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON  })
public class Claims implements IdentityResourceBundleAware {

   private final ClaimsApiServiceImpl delegate = new ClaimsApiServiceImpl();

    @GET
    public Response getClaims(
    		@QueryParam("dialect") String dialect, 
    		@QueryParam("type") String type, 
    		@HeaderParam("Accept") String accept)
    	throws BadRequestException, InternalServerErrorException
    {
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"dialect", 
    			true, 
    			new String[]{}, 
    			dialect);
    	
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"type", 
    			true, 
    			new String[]{"user", "identity"}, 
    			type);
    	
    	return delegate.getClaims(dialect,type);
    }
}

