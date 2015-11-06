package org.vasttrafik.wso2.carbon.identity.api.client;

import org.vasttrafik.wso2.carbon.identity.api.beans.Captcha;
import org.vasttrafik.wso2.carbon.identity.api.beans.Verification;
import org.vasttrafik.wso2.carbon.identity.api.utils.ClientUtils;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

import org.wso2.carbon.captcha.mgt.beans.xsd.CaptchaInfoBean;
import org.wso2.carbon.identity.mgt.stub.UserInformationRecoveryServiceStub;
import org.wso2.carbon.identity.mgt.stub.beans.VerificationBean;

/**
 * Base class for clients working with account recovery related functionality.
 * @author Lars Andersson
 *
 */
public class UserInformationRecoveryClient extends AbstractClient {
	
	/**
	 * Stub to use when calling WSO2 SOAP service
	 */
	protected UserInformationRecoveryServiceStub recoveryStub = ClientUtils.getUserInformationRecoveryServiceStub();
	
	public UserInformationRecoveryClient() {
	}
	
	/**
	 * Transforms a captcha bean into a WSO2 identity management captcha bean
	 * @param captcha the captcha bean to transform
	 * @return a captcha bean
	 */
	protected CaptchaInfoBean getCaptchaInfoBeanFromCaptcha(Captcha captcha) {
		CaptchaInfoBean bean = new CaptchaInfoBean();
		
		bean.setImagePath(captcha.getImageId());
		bean.setSecretKey(captcha.getSecretKey());
		bean.setUserAnswer(captcha.getUserAnswer());
		return bean;
	}
	
	/**
	 * Transforms a WSO2 verification bean into a verification bean
	 * @param ben the WSO2 verification bean to transform
	 * @return a verification bean
	 */
	protected Verification getVerificationFromBean(VerificationBean bean) {
		Verification verification = new Verification();
		
		// Check if the verifiction was successful or not
		if (!bean.getVerified()) {
			// Get the error code
			String error = ResponseUtils.getErrorMessage(resourceBundle, bean.getError(), null);
			// Lookup a localized error message
			verification.setError(error);
		}
		else {
			verification.setError(bean.getError());
		}
		
		verification.setKey(bean.getKey());
		verification.setUserId(bean.getUserId());
		verification.setVerified(bean.getVerified());
		return verification;
	}
}
