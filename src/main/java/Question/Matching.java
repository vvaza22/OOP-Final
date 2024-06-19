package Question;

import java.util.ArrayList;
import java.util.HashSet;

public class Matching extends Question{
    ArrayList<String> choices;
    public Matching(String question, ArrayList<String> answers,
                    ArrayList<String> choices){
        super(question, false, answers);
        this.choices = choices;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return answers;
    }

    @Override
    public int getType() {
        return 7;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public int getScore() {
        return answers.size();
    }

    @Override
    public boolean isCaseSensitive() {
        return false;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    @Override
    public int isCorrect(ArrayList<String> userAnswer) {
        if(userAnswer.isEmpty() || answers.isEmpty()) return 0;

        HashSet<String> helper = new HashSet<String>();
        helper.addAll(answers);
        helper.addAll(userAnswer);

        return userAnswer.size() + answers.size() - helper.size();
    }
}
