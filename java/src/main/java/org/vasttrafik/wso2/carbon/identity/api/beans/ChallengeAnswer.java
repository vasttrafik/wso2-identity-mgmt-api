package org.vasttrafik.wso2.carbon.identity.api.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "ChallengeAnswer")
public class ChallengeAnswer  {
  
  private String userName = null;
  private String confirmation = null;
  private String questionId = null;
  private String answer = null;

  public ChallengeAnswer() {
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
  public String getConfirmation() {
    return confirmation;
  }
  public void setConfirmation(String confirmation) {
    this.confirmation = confirmation;
  }

  
  /**
   **/
  public String getQuestionId() {
    return questionId;
  }
  public void setQuestionId(String questionId) {
    this.questionId = questionId;
  }

  
  /**
   **/
  public String getAnswer() {
    return answer;
  }
  public void setAnswer(String answer) {
    this.answer = answer;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChallengeAnswer {\n");
    
    sb.append("  userName: ").append(userName).append("\n");
    sb.append("  confirmation: ").append(confirmation).append("\n");
    sb.append("  questionId: ").append(questionId).append("\n");
    sb.append("  answer: ").append(answer).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
