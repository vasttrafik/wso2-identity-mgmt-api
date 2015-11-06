package org.vasttrafik.wso2.carbon.identity.api.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "Captcha")
public class Captcha  {
  
  private String imageId = null;
  private String secretKey = null;
  private String userAnswer = null;

  public Captcha() {
  }
  
  /**
   **/
  public String getImageId() {
    return imageId;
  }
  public void setImageId(String imageId) {
    this.imageId = imageId;
  }

  
  /**
   **/
  public String getSecretKey() {
    return secretKey;
  }
  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  
  /**
   **/
  public String getUserAnswer() {
    return userAnswer;
  }
  public void setUserAnswer(String userAnswer) {
    this.userAnswer = userAnswer;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Captcha {\n");
    
    sb.append("  imageId: ").append(imageId).append("\n");
    sb.append("  secretKey: ").append(secretKey).append("\n");
    sb.append("  userAnswer: ").append(userAnswer).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
