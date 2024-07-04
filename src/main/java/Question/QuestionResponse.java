package Question;

import java.util.ArrayList;

/**
 * Question Response: One question, one case-insensitive answer
 */
public class QuestionResponse extends TextQuestion {

    public QuestionResponse(String questionText, int questionId, ArrayList<String> answerList) {
        super(questionText, QuestionType.QUESTION_RESPONSE, questionId, answerList);
    }

}
