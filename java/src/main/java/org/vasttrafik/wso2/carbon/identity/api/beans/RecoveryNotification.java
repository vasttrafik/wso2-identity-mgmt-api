package org.vasttrafik.wso2.carbon.identity.api.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "RecoveryNotification")
public class RecoveryNotification  {
  
  private String userName = null;
  private String key = null;
  private String notificationType = null;

  public RecoveryNotification() {
  }
  
  /**
   **/
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
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
  public String getNotificationType() {
    return notificationType;
  }
  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecoveryNotification {\n");
    
    sb.append("  userName: ").append(userName).append("\n");
    sb.append("  key: ").append(key).append("\n");
    sb.append("  notificationType: ").append(notificationType).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
