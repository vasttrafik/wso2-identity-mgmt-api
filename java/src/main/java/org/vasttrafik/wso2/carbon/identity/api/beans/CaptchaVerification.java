package org.vasttrafik.wso2.carbon.identity.api.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "CaptchaVerification")
public class CaptchaVerification  {
  
  private String userName = null;
  private List<UserClaim> claims = null;
  private Captcha captcha = null;
  private String code = null;
  private String tenantDomain = null;

  public CaptchaVerification() {
  }
  
  /**
   **/
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }

  
  public List<UserClaim> getClaims() {
	return claims;
  }
  public void setClaims(List<UserClaim> claims) {
	this.claims = claims;
  }
  
  /**
   **/
  public Captcha getCaptcha() {
    return captcha;
  }
  public void setCaptcha(Captcha captcha) {
    this.captcha = captcha;
  }

  
  /**
   **/
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

  
  /**
   **/
  public String getTenantDomain() {
    return tenantDomain;
  }
  public void setTenantDomain(String tenantDomain) {
    this.tenantDomain = tenantDomain;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class CaptchaVerification {\n");
    
    sb.append("  userName: ").append(userName).append("\n");
    sb.append("  captcha: ").append(captcha).append("\n");
    sb.append("  code: ").append(code).append("\n");
    sb.append("  tenantDomain: ").append(tenantDomain).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
