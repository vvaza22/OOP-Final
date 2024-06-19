package Question;

import java.util.ArrayList;

public abstract class Question {
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
