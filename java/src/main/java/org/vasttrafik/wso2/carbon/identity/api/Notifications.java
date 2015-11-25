package org.vasttrafik.wso2.carbon.identity.api;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.impl.NotificationsApiServiceImpl;
import org.vasttrafik.wso2.carbon.identity.api.utils.IdentityResourceBundleAware;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 *
 */
@Path("/notifications")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON  })
public class Notifications implements IdentityResourceBundleAware {

   private final NotificationsApiServiceImpl delegate = new NotificationsApiServiceImpl();

    @POST
    public Response sendRecoveryNotification(
    		@HeaderParam("Accept") String accept, 
    		@HeaderParam("Content-Type") String contentType, 
    		RecoveryNotification notification) 
    	throws InternalServerErrorException
    {
        return delegate.sendRecoveryNotification(notification);
    }
}

