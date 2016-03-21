package org.vasttrafik.wso2.carbon.identity.api.exceptions;

import javax.ws.rs.ext.Provider;

import org.vasttrafik.wso2.carbon.common.api.exceptions.GenericExceptionMapper;

@Provider
public final class IdentityMgmtExceptionMapper extends GenericExceptionMapper {
	
	private static final String bundleName = "org.vasttrafik.wso2.carbon.identity.api.utils.IdentityErrorListResourceBundle";
	
	public IdentityMgmtExceptionMapper() {
		super(bundleName);
	}
}
