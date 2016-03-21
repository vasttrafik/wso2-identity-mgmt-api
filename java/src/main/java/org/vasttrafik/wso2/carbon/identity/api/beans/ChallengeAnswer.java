package org.vasttrafik.wso2.carbon.identity.api.beans;

import javax.xml.bind.annotation.XmlRootElement;

import javax.validation.constraints.NotNull;

/**
 * @author Lars Andersson
 *
 */
@XmlRootElement(name = "ChallengeAnswer")
public class ChallengeAnswer  {
  
  @NotNull(message= "{answer.username.notnull}")
  private String userName = null;
  @NotNull(message= "{answer.confirmation.notnull}")
  private String confirmation = null;
  @NotNull(message= "{answer.questionid.notnull}")
  private String questionId = null;
  @NotNull(message= "{answer.answer.notnull}")
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
