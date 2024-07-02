package Question;

import java.util.ArrayList;

public abstract class Question {

    public static final int QUESTION_RESPONSE = 1;
    public static final int FILL_BLANK = 2;
    public static final int MULTIPLE_CHOICE = 3;
    public static final int PICTURE_RESPONSE = 4;

    protected String question;
    protected boolean caseSensitive;
    protected ArrayList<String> answers;
    public Question(String question, boolean caseSensitive, ArrayList<String> answers){
        this.question = question;
        this.caseSensitive = caseSensitive;
        this.answers = answers;
    }

    public abstract String getQuestion();
    public abstract int getType();
    public abstract boolean isCaseSensitive();
    public abstract ArrayList<String> getAnswers();
    public abstract int isCorrect(ArrayList<String> userAnswer);
    public abstract int getScore();
}
