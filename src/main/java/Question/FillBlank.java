package Question;

import java.util.ArrayList;

public class FillBlank extends TextQuestion {

    public FillBlank(String questionText, ArrayList<String> correctAnswerList){
        super(questionText, FILL_BLANK, correctAnswerList);
    }

}