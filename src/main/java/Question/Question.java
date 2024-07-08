package Question;

import java.util.ArrayList;

public abstract class Question {

    protected int questionId;
    protected String questionText;
    protected int questionType;

    public Question(String questionText, int questionType, int questionId) {
        this.questionText = questionText;
        this.questionType = questionType;
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getType() {
        return questionType;
    }

    public int getId() {
        return questionId;
    }

    public abstract int getMaxScore();
    public abstract int countPoints();
    public abstract boolean hasAnswer();
    public abstract void resetAnswer();
}
