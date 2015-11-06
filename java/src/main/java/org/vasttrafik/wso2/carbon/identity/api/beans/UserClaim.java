package org.vasttrafik.wso2.carbon.identity.api.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "UserClaim")
public class UserClaim  {
  
  private String claimUri = null;
  private String claimValue = null;
  private String description = null;
  private String dialectURI = null;
  private Integer displayOrder = null;
  private String displayTag = null;
  private String regEx = null;
  private String required = null;
  private String supportedByDefault = null;

  public UserClaim() {
  }
  
  /**
   **/
  public String getClaimUri() {
    return claimUri;
  }
  public void setClaimUri(String claimUri) {
    this.claimUri = claimUri;
  }

  
  /**
   **/
  public String getClaimValue() {
    return claimValue;
  }
  public void setClaimValue(String claimValue) {
    this.claimValue = claimValue;
  }

  
  /**
   **/
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  
  /**
   **/
  public String getDialectURI() {
    return dialectURI;
  }
  public void setDialectURI(String dialectURI) {
    this.dialectURI = dialectURI;
  }

  
  /**
   **/
  public Integer getDisplayOrder() {
    return displayOrder;
  }
  public void setDisplayOrder(Integer displayOrder) {
    this.displayOrder = displayOrder;
  }

  
  /**
   **/
  public String getDisplayTag() {
    return displayTag;
  }
  public void setDisplayTag(String displayTag) {
    this.displayTag = displayTag;
  }

  
  /**
   **/
  public String getRegEx() {
    return regEx;
  }
  public void setRegEx(String regEx) {
    this.regEx = regEx;
  }

  
  /**
   **/
  public String getRequired() {
    return required;
  }
  public void setRequired(String required) {
    this.required = required;
  }

  
  /**
   **/
  public String getSupportedByDefault() {
    return supportedByDefault;
  }
  public void setSupportedByDefault(String supportedByDefault) {
    this.supportedByDefault = supportedByDefault;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserClaim {\n");
    
    sb.append("  claimUri: ").append(claimUri).append("\n");
    sb.append("  claimValue: ").append(claimValue).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  dialectURI: ").append(dialectURI).append("\n");
    sb.append("  displayOrder: ").append(displayOrder).append("\n");
    sb.append("  displayTag: ").append(displayTag).append("\n");
    sb.append("  regEx: ").append(regEx).append("\n");
    sb.append("  required: ").append(required).append("\n");
    sb.append("  supportedByDefault: ").append(supportedByDefault).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
