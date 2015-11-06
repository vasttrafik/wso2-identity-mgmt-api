package org.vasttrafik.wso2.carbon.identity.api.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "ChallengeQuestion")
public class ChallengeQuestion  {
  
  private String answer = null;
  private String error = null;
  private String id = null;
  private String key = null;
  private Integer order = null;
  private Boolean primary = null;
  private String question = null;
  private Boolean verfied = null;

  public ChallengeQuestion() {
  }
  
  /**
   **/
  public String getAnswer() {
    return answer;
  }
  public void setAnswer(String answer) {
    this.answer = answer;
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
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
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
  public Integer getOrder() {
    return order;
  }
  public void setOrder(Integer order) {
    this.order = order;
  }

  
  /**
   **/
  public Boolean getPrimary() {
    return primary;
  }
  public void setPrimary(Boolean primary) {
    this.primary = primary;
  }

  
  /**
   **/
  public String getQuestion() {
    return question;
  }
  public void setQuestion(String question) {
    this.question = question;
  }

  
  /**
   **/
  public Boolean getVerfied() {
    return verfied;
  }
  public void setVerfied(Boolean verfied) {
    this.verfied = verfied;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChallengeQuestion {\n");
    
    sb.append("  answer: ").append(answer).append("\n");
    sb.append("  error: ").append(error).append("\n");
    sb.append("  id: ").append(id).append("\n");
    sb.append("  key: ").append(key).append("\n");
    sb.append("  order: ").append(order).append("\n");
    sb.append("  primary: ").append(primary).append("\n");
    sb.append("  question: ").append(question).append("\n");
    sb.append("  verfied: ").append(verfied).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
