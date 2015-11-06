package org.vasttrafik.wso2.carbon.identity.api.impl;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.client.NotificationsClient;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;

/**
 * @author Lars Andersson
 *
 */
public class NotificationsApiServiceImpl  {
	
	private NotificationsClient client = new NotificationsClient();
  
	/**
	 * Sends an account recovery notification email to the user owning the account that needs to be recovered.
	 * @param notification the recovery notification information
	 * @return a verification indicating if the notification was sent or not
	 * @throws Exception if an error occurs
	 */
	public Response sendRecoveryNotification(RecoveryNotification notification) throws BadRequestException, InternalServerErrorException
	{
		try {
			Verification verification = client.sendRecoveryNotification(notification);
			return Response.ok(verification).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new InternalServerErrorException(response);
		}
	}
}
