package org.vasttrafik.wso2.carbon.identity.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.impl.NotificationsApiServiceImpl;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 *
 */
@Path("/notifications")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON  })
public final class Notifications {

   private final NotificationsApiServiceImpl delegate = new NotificationsApiServiceImpl();

    @POST
    public Response sendRecoveryNotification(
    		@HeaderParam("Accept") final String accept, 
    		@HeaderParam("Content-Type") final String contentType, 
    		@NotNull(message= "{recovery.notification.notnull}") @Valid final RecoveryNotification notification) 
    	throws ClientErrorException
    {
        return delegate.sendRecoveryNotification(notification);
    }
}

