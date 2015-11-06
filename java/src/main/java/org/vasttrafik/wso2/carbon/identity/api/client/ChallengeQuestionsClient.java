package org.vasttrafik.wso2.carbon.identity.api.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;

import org.vasttrafik.wso2.carbon.identity.api.beans.ChallengeAnswer;
import org.vasttrafik.wso2.carbon.identity.api.beans.ChallengeQuestion;
import org.vasttrafik.wso2.carbon.identity.api.beans.Verification;
import org.vasttrafik.wso2.carbon.identity.api.utils.ClientUtils;

import org.wso2.carbon.identity.mgt.stub.beans.VerificationBean;
import org.wso2.carbon.identity.mgt.stub.dto.ChallengeQuestionIdsDTO;
import org.wso2.carbon.identity.mgt.stub.dto.UserChallengesDTO;

/**
 * @author Lars Andersson
 *
 */
public final class ChallengeQuestionsClient extends UserInformationRecoveryClient {
	
	public ChallengeQuestionsClient() {
		super();
	}
	
	//
	// TO-DO: Replace with UserIdentityManagementAdminService getChallengeQuestionsOfUser?
	//
	/**
	 * Retrieves all challenge questions of a user
	 * @param username the username of the user 
	 * @param confirmation a confirmation code
	 * @return a list of challenge questions
	 * @throws Exception if an error occurs
	 */
	public List<ChallengeQuestion> getChallengequestions(String username, String confirmation) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		try {
			List<ChallengeQuestion> questions = new ArrayList<ChallengeQuestion>();
			ChallengeQuestionIdsDTO questionIdsDTO = recoveryStub.getUserChallengeQuestionIds(username, confirmation);
		
			if (questionIdsDTO.isErrorSpecified())
				throw new NotAuthorizedException(questionIdsDTO.getError());
			
			String[] questionIds = questionIdsDTO.getIds();
			
			for (String questionId : questionIds) {
				ChallengeQuestion question = getChallengequestion(questionId, username, questionIdsDTO.getKey());
				questions.add(question);
			}
			
			return questions;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Retrieves a challenge question.
	 * @param questionId the id of the challenge question to retrieve
	 * @param username the username of the user to which the challenge question belongs
	 * @param confirmation the confirmation code previously retrieved when fetching a list of questions 
	 * @return the challenge question
	 * @throws Exception
	 */
	public ChallengeQuestion getChallengequestion(String questionId, String username, String confirmation) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		try {
			UserChallengesDTO questionDTO = recoveryStub.getUserChallengeQuestion(
					username, 
					confirmation, 
					questionId);
		
			if (questionDTO == null) {
				throw new NotFoundException();
			}
			else if (questionDTO.isErrorSpecified()) {
				throw new NotAuthorizedException(questionDTO.getError());
			}
			else {
				return getChallengeQuestionFromDTO(questionDTO);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Verifies the answer to a challenge question
	 * @param questionId the id of the challenge question to verify
	 * @param answer the answer to the challenge question
	 * @return the result of the verification
	 * @throws Exception if an error occurs
	 */
	public Verification verifyAnswer(String questionId, ChallengeAnswer answer) throws Exception {
		ClientUtils.authenticateIfNeeded(recoveryStub._getServiceClient());
		
		try {
			VerificationBean bean = recoveryStub.verifyUserChallengeAnswer(
					answer.getUserName(), 
					answer.getConfirmation(), 
					questionId, 
					answer.getAnswer());
			
			return getVerificationFromBean(bean);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	protected ChallengeQuestion getChallengeQuestionFromDTO(UserChallengesDTO dto) {
		ChallengeQuestion question = new ChallengeQuestion();
		
		question.setError(dto.getError());
		question.setId(dto.getId());
		question.setKey(dto.getKey());
		question.setOrder(dto.getOrder());
		question.setPrimary(dto.getPrimary());
		question.setQuestion(dto.getQuestion());
		return question;
	}
}