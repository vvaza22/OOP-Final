package Question;

import java.util.ArrayList;

public abstract class Question {

    public static final int QUESTION_RESPONSE = 1;
    public static final int FILL_BLANK = 2;
    public static final int MULTIPLE_CHOICE = 3;
    public static final int PICTURE_RESPONSE = 4;

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
