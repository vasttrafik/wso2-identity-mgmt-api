package org.vasttrafik.wso2.carbon.identity.api.impl;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.vasttrafik.wso2.carbon.identity.api.beans.*;
import org.vasttrafik.wso2.carbon.identity.api.client.CaptchaManagementClient;
import org.vasttrafik.wso2.carbon.common.api.beans.Error;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

/**
 * 
 * @author Lars Andersson, Västtrafik 2015
 *
 */
public class CaptchasApiServiceImpl extends IdentityMgmtApiServiceImpl {
	
	private CaptchaManagementClient client = new CaptchaManagementClient();
	
	private static final String[] actions = {"verifyAccount", "verifyCode", "verifyUser"};
  
	/**
	 * Generate a captcha.
	 * @return a captcha
	 * @throws Exception if a captcha could not be generated
	 */
	public Response generateCaptcha() throws ServerErrorException
	{
		try {
			Captcha captcha = client.generateCaptcha();
			return Response.status(201).entity(captcha).build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	/**
	 * Verifies the answer to a captcha.
	 * @param action the rationale for verifying the captcha, which can be any of verifyAccount, verifyCode
	 * and verifyUser. Please refer to WSO2 Identity Server documentation for further information.
	 * @param captcha the captcha to verify
	 * @return the result of the verification
	 * @throws Exception if an error occurs
	 */
	public Response verifyCaptcha(String action, CaptchaVerification captcha) throws ServerErrorException
	{	
		try {
			responseUtils.checkParameter(
	    			"action", 
	    			true, 
	    			actions, 
	    			action);
			
			Verification verification = client.verifyCaptcha(action, captcha);
			return Response.ok(verification).build();
		}
		catch (BadRequestException bre) {
			// Create an error
			Error error = responseUtils.buildError(1001L, new Object[][]{null,{action, "action"}});
			// Return result
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(error)
					.build();
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
	
	/**
	 * Retrieves a previously generated captcha.
	 * @param imageId the id of the captcha
	 * @return a byte array containing the captcha image
	 * @throws Exception if the captcha could not be retrieved
	 */
	public Response getCaptcha(String id) throws ServerErrorException, NotFoundException
	{		
		try {
			Object captcha = client.getCaptcha(id);
			return Response.ok(captcha).build();
		}
		catch (NotFoundException nfe) {
			Response response = Response.status(404).build();
			throw new NotFoundException(response);
		}
		catch (Exception e) {
			Response response = ResponseUtils.serverError(e);
			throw new ServerErrorException(response);
		}
	}
}
