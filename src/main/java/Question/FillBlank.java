package Question;

import java.util.ArrayList;

public class FillBlank extends Question{
    public FillBlank(String question, boolean caseSensitive, String answer){
        super(question, caseSensitive, new ArrayList<String>());
        answers.add(answer);
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return answers;
    }

    @Override
    public boolean isCorrect(ArrayList<String> userAnswer) {
        if(userAnswer.isEmpty()) return false;
        if(answers.isEmpty()) return false;
        if(userAnswer.get(0).equals(answers.get(0))) return true;
        return false;
    }

    @Override
    public String getQuestion() {
        return question;
    }
}
