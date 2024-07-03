package Question;

import java.util.ArrayList;

public abstract class Question {

    protected String questionText;
    protected int questionType;

    public Question(String questionText, int questionType) {
        this.questionText = questionText;
        this.questionType = questionType;
    }

    public String getQuestion() {
        return questionText;
    }

    public int getType() {
        return questionType;
    }

    public abstract int countPoints();
}
