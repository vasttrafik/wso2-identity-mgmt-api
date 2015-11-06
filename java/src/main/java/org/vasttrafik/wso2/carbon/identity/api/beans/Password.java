package org.vasttrafik.wso2.carbon.identity.api.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "Password")
public class Password  {
  
  private String confirmationCode = null;
  private String password = null;
  private String newPassword = null;

  public Password() {
  }
  
  /**
   **/
  public String getConfirmationCode() {
    return confirmationCode;
  }
  public void setConfirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }

  
  /**
   **/
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  
  /**
   **/
  public String getNewPassword() {
    return newPassword;
  }
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Password {\n");
    
    sb.append("  confirmationCode: ").append(confirmationCode).append("\n");
    sb.append("  password: ").append(password).append("\n");
    sb.append("  newPassword: ").append(newPassword).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
