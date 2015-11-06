package org.vasttrafik.wso2.carbon.identity.api.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "Notification")
public class Notification  {
  
  private String domainName = null;
  private String firstName = null;
  private String notification = null;
  private String notificationAddress = null;
  private String notificationCode = null;
  private Boolean notificationSent = null;
  private String notificationSubject = null;
  private String notificationType = null;
  private String userId = null;

  public Notification() {
  }
  
  /**
   **/
  public String getDomainName() {
    return domainName;
  }
  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }

  
  /**
   **/
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  
  /**
   **/
  public String getNotification() {
    return notification;
  }
  public void setNotification(String notification) {
    this.notification = notification;
  }

  
  /**
   **/
  public String getNotificationAddress() {
    return notificationAddress;
  }
  public void setNotificationAddress(String notificationAddress) {
    this.notificationAddress = notificationAddress;
  }

  
  /**
   **/
  public String getNotificationCode() {
    return notificationCode;
  }
  public void setNotificationCode(String notificationCode) {
    this.notificationCode = notificationCode;
  }

  
  /**
   **/
  public Boolean getNotificationSent() {
    return notificationSent;
  }
  public void setNotificationSent(Boolean notificationSent) {
    this.notificationSent = notificationSent;
  }

  
  /**
   **/
  public String getNotificationSubject() {
    return notificationSubject;
  }
  public void setNotificationSubject(String notificationSubject) {
    this.notificationSubject = notificationSubject;
  }

  
  /**
   **/
  public String getNotificationType() {
    return notificationType;
  }
  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  
  /**
   **/
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Notification {\n");
    
    sb.append("  domainName: ").append(domainName).append("\n");
    sb.append("  firstName: ").append(firstName).append("\n");
    sb.append("  notification: ").append(notification).append("\n");
    sb.append("  notificationAddress: ").append(notificationAddress).append("\n");
    sb.append("  notificationCode: ").append(notificationCode).append("\n");
    sb.append("  notificationSent: ").append(notificationSent).append("\n");
    sb.append("  notificationSubject: ").append(notificationSubject).append("\n");
    sb.append("  notificationType: ").append(notificationType).append("\n");
    sb.append("  userId: ").append(userId).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
