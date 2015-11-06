package org.vasttrafik.wso2.carbon.identity.api.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "Verification")
public class Verification  {
  
  private String error = null;
  private String key = null;
  private Notification notificationData = null;
  private String redirectPath = null;
  private String userId = null;
  private Boolean verified = null;

  public Verification() {
  }
  
  /**
   **/
  public String getError() {
    return error;
  }
  public void setError(String error) {
    this.error = error;
  }

  
  /**
   **/
  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }

  
  /**
   **/
  public Notification getNotificationData() {
    return notificationData;
  }
  public void setNotificationData(Notification notificationData) {
    this.notificationData = notificationData;
  }

  
  /**
   **/
  public String getRedirectPath() {
    return redirectPath;
  }
  public void setRedirectPath(String redirectPath) {
    this.redirectPath = redirectPath;
  }

  
  /**
   **/
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }

  
  /**
   **/
  public Boolean getVerified() {
    return verified;
  }
  public void setVerified(Boolean verified) {
    this.verified = verified;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Verification {\n");
    
    sb.append("  error: ").append(error).append("\n");
    sb.append("  key: ").append(key).append("\n");
    sb.append("  notificationData: ").append(notificationData).append("\n");
    sb.append("  redirectPath: ").append(redirectPath).append("\n");
    sb.append("  userId: ").append(userId).append("\n");
    sb.append("  verified: ").append(verified).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
