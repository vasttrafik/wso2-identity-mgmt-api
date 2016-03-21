package org.vasttrafik.wso2.carbon.identity.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.impl.CaptchasApiServiceImpl;
import org.vasttrafik.wso2.carbon.identity.api.utils.IdentityResourceBundleAware;
/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 * 
 *
 */
@Path("/captchas")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public final class Captchas {

	private final CaptchasApiServiceImpl delegate = new CaptchasApiServiceImpl();

    @PUT
    public Response verifyCaptcha(
    		@HeaderParam("Accept") final String accept,
    		@HeaderParam("Content-Type") final String contentType,
    		@NotNull(message= "{param.action.notnull}") @QueryParam("action") final String action, 
    		@NotNull(message= "{captcha.verification.notnull}") @Valid final CaptchaVerification captcha) 
    	throws ClientErrorException
    {
    	return delegate.verifyCaptcha(action, captcha);
    }
	
    @POST
    public Response generateCaptcha(
    		@HeaderParam("Accept") final String accept, 
    		@HeaderParam("Content-Type") final String contentType)
    	throws ClientErrorException
    {
        return delegate.generateCaptcha();
    }
	
    @GET
    @Path("/{id}")
    @Produces({ "image/jpeg" })
    public Response getCaptcha(
    		@PathParam("id") final String id, 
    		@HeaderParam("Accept") final String accept) 
    	throws ClientErrorException
    {
        return delegate.getCaptcha(id);
    }
}

