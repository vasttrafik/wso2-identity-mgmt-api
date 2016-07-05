package org.vasttrafik.wso2.carbon.identity.api.client;

import java.util.Locale;
import java.util.ResourceBundle;

import org.vasttrafik.wso2.carbon.common.api.utils.AbstractErrorListResourceBundle;
import org.vasttrafik.wso2.carbon.common.api.utils.ResponseUtils;

/**
 * Abstract class that only serves as a base class for all client classes with the sole purpose of making the identity resource bundle
 * available to sub-classes.
 * @author Lars Andersson
 *
 */
public abstract class AbstractClient {
	
	/**
	 * Resource bundle to use when constructing messages and responses
	 */
	private static AbstractErrorListResourceBundle resourceBundle = null;
	
	/**
	 * ResponseUtils instance
	 */
	protected static ResponseUtils responseUtils = null;
	
	private static final String bundleName = "org.vasttrafik.wso2.carbon.identity.api.utils.IdentityErrorListResourceBundle";
	
	static {
		try {
			// Get system country and language
			String country  = System.getProperty("user.country");
			String language = System.getProperty("user.language");
			
			// Create Locale
			@SuppressWarnings("unused")
			Locale locale = new Locale(language, country);
			
			// Load the resource bundle
			resourceBundle = (AbstractErrorListResourceBundle)
					ResourceBundle.getBundle(bundleName, /*locale*/new Locale("sv", "SE"), ResponseUtils.class.getClassLoader());

			// Create a ResponseUtils instance
			responseUtils = new ResponseUtils(resourceBundle);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
