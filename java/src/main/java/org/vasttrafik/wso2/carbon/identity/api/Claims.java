package org.vasttrafik.wso2.carbon.identity.api;

import javax.validation.constraints.NotNull;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.vasttrafik.wso2.carbon.identity.api.impl.ClaimsApiServiceImpl;
import org.vasttrafik.wso2.carbon.identity.api.utils.IdentityResourceBundleAware;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 *
 */
@Path("/claims")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON  })
public final class Claims {

   private final ClaimsApiServiceImpl delegate = new ClaimsApiServiceImpl();

    @GET
    public Response getClaims(
    		@NotNull(message= "{param.dialect.notnull}") @QueryParam("dialect") final String dialect, 
    		@NotNull(message= "{param.type.notnull}") @QueryParam("type") final String type, 
    		@HeaderParam("Accept") final String accept)
    	throws ClientErrorException
    {
    	return delegate.getClaims(dialect,type);
    }
}

