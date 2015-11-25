package org.vasttrafik.wso2.carbon.identity.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.impl.CaptchasApiServiceImpl;
import org.vasttrafik.wso2.carbon.identity.api.utils.IdentityResourceBundleAware;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 * 
 *
 */
@Path("/captchas")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class Captchas implements IdentityResourceBundleAware {

	private final CaptchasApiServiceImpl delegate = new CaptchasApiServiceImpl();

    @PUT
    public Response verifyCaptcha(
    		@HeaderParam("Accept") String accept,
    		@HeaderParam("Content-Type") String contentType,
    		@QueryParam("action") String action, 
    		CaptchaVerification captcha) 
    	throws BadRequestException, InternalServerErrorException
    {
    	ResponseUtils.checkParameter(
    			resourceBundle,
    			"action", 
    			true, 
    			new String[]{"verifyAccount", "verifyCode", "verifyUser"}, 
    			action);
    	
    	return delegate.verifyCaptcha(action, captcha);
    }
	
    @POST
    public Response generateCaptcha(
    		@HeaderParam("Accept") String accept, 
    		@HeaderParam("Content-Type") String contentType)
    	throws InternalServerErrorException
    {
        return delegate.generateCaptcha();
    }
	
    @GET
    @Path("/{id}")
    @Produces({ "image/jpeg" })
    public Response getCaptcha(
    		@PathParam("id") String id, 
    		@HeaderParam("Accept") String accept) 
    	throws InternalServerErrorException, NotFoundException
    {
        return delegate.getCaptcha(id);
    }
}

