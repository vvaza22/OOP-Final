package Question;

import java.util.ArrayList;

public class FillBlank extends TextQuestion {

    public FillBlank(String questionText, int questionId, ArrayList<String> correctAnswerList){
        super(questionText, QuestionType.FILL_BLANK, questionId, correctAnswerList);
    }

}