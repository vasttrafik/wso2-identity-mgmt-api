package org.vasttrafik.wso2.carbon.identity.api.client;

import org.vasttrafik.wso2.carbon.identity.api.beans.RecoveryNotification;
import org.vasttrafik.wso2.carbon.identity.api.beans.Verification;
import org.vasttrafik.wso2.carbon.common.api.utils.ClientUtils;

import org.wso2.carbon.identity.mgt.stub.beans.VerificationBean;

/**
 * @author Lars Andersson
 *
 */
public final class NotificationsClient extends UserInformationRecoveryClient {
	
	public NotificationsClient() {
		super();
	}
	
	/**
	 * Sends an account recovery notification email to the user owning the account that needs to be recovered.
	 * @param notification the recovery notification information
	 * @return a verification indicating if the notification was sent or not
	 * @throws Exception if an error occurs
	 */
	public Verification sendRecoveryNotification(RecoveryNotification notification) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		try {
			VerificationBean bean = recoveryStub.sendRecoveryNotification(
					notification.getUserName(), 
					notification.getKey(), 
					notification.getNotificationType());
			
			return getVerificationFromBean(bean);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
