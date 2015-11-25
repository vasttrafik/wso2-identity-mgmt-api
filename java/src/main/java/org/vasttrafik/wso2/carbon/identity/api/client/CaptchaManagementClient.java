package org.vasttrafik.wso2.carbon.identity.api.client;

import java.util.List;

import javax.activation.DataHandler;
import javax.ws.rs.NotFoundException;

import org.vasttrafik.wso2.carbon.identity.api.beans.Captcha;
import org.vasttrafik.wso2.carbon.identity.api.beans.CaptchaVerification;
import org.vasttrafik.wso2.carbon.identity.api.beans.UserClaim;
import org.vasttrafik.wso2.carbon.identity.api.beans.Verification;
import org.vasttrafik.wso2.carbon.common.api.utils.ClientUtils;

import org.wso2.carbon.captcha.mgt.beans.xsd.CaptchaInfoBean;
import org.wso2.carbon.identity.mgt.stub.beans.VerificationBean;
import org.wso2.carbon.identity.mgt.stub.dto.UserIdentityClaimDTO;
import org.wso2.carbon.registry.ws.stub.WSRegistryServiceStub;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 *
 */
public final class CaptchaManagementClient extends UserInformationRecoveryClient {
		
	private static WSRegistryServiceStub registryStub = ClientUtils.getWSRegistryServiceStub();
	
	public CaptchaManagementClient() {
		super();
	}
	
	/**
	 * Generate a captcha.
	 * @return a captcha
	 * @throws Exception if a captcha could not be generated
	 */
	public Captcha generateCaptcha() throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		try {
			CaptchaInfoBean captcha = recoveryStub.getCaptcha();
			return getCaptchaFromInfoBean(captcha);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Retrieves a previously generated captcha.
	 * @param imageId the id of the captcha
	 * @return a byte array containing the captcha image
	 * @throws Exception if the captcha could not be retrieved
	 */
	public Object getCaptcha(String imageId) throws Exception {
		final String imagePath = "/_system/config/repository/components/org.wso2.carbon.captcha-images/";
		return downloadCaptcha(imagePath + imageId + ".jpg");
	}
	
	/**
	 * Verifies the answer to a captcha.
	 * @param action the rationale for verifying the captcha, which can be any of verifyAccount, verifyCode
	 * and verifyUser. Please refer to WSO2 Identity Server documentation for further information.
	 * @param captcha the captcha to verify
	 * @return the result of the verification
	 * @throws Exception if an error occurs
	 */
	public Verification verifyCaptcha(String action, CaptchaVerification captcha) throws Exception {
		Verification verification = null;
		
		if ("verifyAccount".equalsIgnoreCase(action))
			verification = verifyAccount(captcha);
		else if ("verifyCode".equalsIgnoreCase(action))
			verification =  verifyCode(captcha);
		else if ("verifyUser".equalsIgnoreCase(action))
			verification =  verifyUser(captcha);
		
		return verification;
	}
	
	protected Object downloadCaptcha(String imageURL) throws Exception, NotFoundException {
		ClientUtils.authenticateIfNeeded(registryStub._getServiceClient());
		
		try {
			DataHandler datahandler = registryStub.getContent(imageURL);
			Object content = datahandler.getContent();
			return content;
		}
		catch (Exception e) {
			throw new NotFoundException();
		}
	}
	
	protected Verification verifyAccount(CaptchaVerification captcha) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		try {
			UserIdentityClaimDTO[] claims = getUserIdentityClaims(captcha);
			CaptchaInfoBean captchaBean = getCaptchaInfoBeanFromCaptcha(captcha.getCaptcha());
			VerificationBean verification = recoveryStub.verifyAccount(claims, captchaBean, "carbon.super");
			return getVerificationFromBean(verification);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	protected Verification verifyCode(CaptchaVerification captcha) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		try {
			CaptchaInfoBean captchaBean = getCaptchaInfoBeanFromCaptcha(captcha.getCaptcha());
			VerificationBean verification = recoveryStub.verifyConfirmationCode(captcha.getUserName(), captcha.getCode(), captchaBean);
			return getVerificationFromBean(verification);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	protected Verification verifyUser(CaptchaVerification captcha) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		try {
			CaptchaInfoBean captchaBean = getCaptchaInfoBeanFromCaptcha(captcha.getCaptcha());
			VerificationBean verification = recoveryStub.verifyUser(captcha.getUserName(), captchaBean);
			return getVerificationFromBean(verification);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	protected UserIdentityClaimDTO[] getUserIdentityClaims(CaptchaVerification captcha) {
		List<UserClaim> claims = captcha.getClaims();
		UserIdentityClaimDTO[] identityClaims = new UserIdentityClaimDTO[claims.size()];
		
		for (int i = 0; i < claims.size(); i++) {
			identityClaims[i] = new UserIdentityClaimDTO();
			identityClaims[i].setClaimUri(claims.get(i).getClaimUri());
			identityClaims[i].setClaimValue(claims.get(i).getClaimValue());
			
		}
		
		return identityClaims;
	}
	
	protected Captcha getCaptchaFromInfoBean(CaptchaInfoBean bean) {
		String imageId = getImageIdFromPath(bean.getImagePath());
		
		Captcha captcha = new Captcha();
		captcha.setImageId(imageId);
		captcha.setSecretKey(bean.getSecretKey());
		captcha.setUserAnswer("");
		return captcha;
	}
	
	protected String getImageIdFromPath(String imagePath) {
		int beginIndex = imagePath.lastIndexOf("/");
		int endIndex = imagePath.lastIndexOf(".");
		return imagePath.substring(++beginIndex, endIndex);
	}
}
