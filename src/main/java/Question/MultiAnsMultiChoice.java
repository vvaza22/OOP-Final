package Question;

import java.util.ArrayList;
import java.util.HashSet;

public class MultiAnsMultiChoice extends Question{
    private boolean isOrdered;
    private ArrayList<String> choices;

    public MultiAnsMultiChoice(String question, boolean caseSensitive, ArrayList<String> answers,
                               boolean isOrdered, ArrayList<String> choices){
        super(question, caseSensitive, answers);
        this.isOrdered = isOrdered;
        this.choices = choices;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return answers;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public boolean isOrdered(){
        return isOrdered;
    }

    @Override
    public int getType() {
        return 6;
    }

    @Override
    public int getScore() {
        return answers.size();
    }

    @Override
    public int isCorrect(ArrayList<String> userAnswer) {
        if(userAnswer.isEmpty() || answers.isEmpty()) return 0;
        HashSet<String> helper = new HashSet<String>();

        helper.addAll(userAnswer);
        helper.addAll(answers);

        return userAnswer.size() + answers.size() - helper.size();
    }
}
