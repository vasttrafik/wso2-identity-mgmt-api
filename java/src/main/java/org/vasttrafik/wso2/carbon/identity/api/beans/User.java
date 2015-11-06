package org.vasttrafik.wso2.carbon.identity.api.beans;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "User")
public class User  {
  
  private Integer id = null;
  private String userName = null;
  private Password password = null;
  private List<UserClaim> claims = new ArrayList<UserClaim>();
  private String profileName = null;
  private String tenantDomain = null;
  private Date lastModifiedDate = null;

  public User() {
  }
  
  /**
   **/
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
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
  public Password getPassword() {
    return password;
  }
  public void setPassword(Password password) {
    this.password = password;
  }

  
  /**
   **/
  public List<UserClaim> getClaims() {
    return claims;
  }
  public void setClaims(List<UserClaim> claims) {
    this.claims = claims;
  }

  
  /**
   **/
  public String getProfileName() {
    return profileName;
  }
  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  
  /**
   **/
  public String getTenantDomain() {
    return tenantDomain;
  }
  public void setTenantDomain(String tenantDomain) {
    this.tenantDomain = tenantDomain;
  }

  
  /**
   **/
  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }
  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  userName: ").append(userName).append("\n");
    sb.append("  password: ").append(password).append("\n");
    sb.append("  claims: ").append(claims).append("\n");
    sb.append("  profileName: ").append(profileName).append("\n");
    sb.append("  tenantDomain: ").append(tenantDomain).append("\n");
    sb.append("  lastModifiedDate: ").append(lastModifiedDate).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
