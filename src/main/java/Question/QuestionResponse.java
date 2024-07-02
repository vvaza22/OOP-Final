package Question;

import java.util.ArrayList;

/**
 * Question Response: One question, one case-insensitive answer
 */
public class QuestionResponse extends TextQuestion {

    public QuestionResponse(String questionText, ArrayList<String> answerList) {
        super(questionText, QUESTION_RESPONSE, answerList);
    }

}
