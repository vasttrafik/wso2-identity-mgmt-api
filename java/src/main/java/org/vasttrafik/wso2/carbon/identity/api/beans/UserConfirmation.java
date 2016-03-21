package org.vasttrafik.wso2.carbon.identity.api.beans;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "UserConfirmation")
public class UserConfirmation  {
  
  @NotNull(message= "{confirmation.username.notnull}")
  private String username = null;
  @NotNull(message= "{confirmation.code.notnull}")
  private String code = null;
  @NotNull(message= "{confirmation.captcha.notnull}") @Valid
  private Captcha captcha = null;
  private String tenantDomain = null;

  public UserConfirmation() {
  }
  
  /**
   **/
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
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
  public Captcha getCaptcha() {
    return captcha;
  }
  public void setCaptcha(Captcha captcha) {
    this.captcha = captcha;
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
    sb.append("class UserConfirmation {\n");
    
    sb.append("  username: ").append(username).append("\n");
    sb.append("  code: ").append(code).append("\n");
    sb.append("  captcha: ").append(captcha).append("\n");
    sb.append("  tenantDomain: ").append(tenantDomain).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
