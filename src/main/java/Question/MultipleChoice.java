package Question;

import java.util.ArrayList;

public class MultipleChoice extends Question{
    protected ArrayList<String> choices;
    public MultipleChoice(String question, ArrayList<String> answers,
                       ArrayList<String> choices){
        super(question, false, answers);
        this.choices = choices;
    }

    @Override
    public int getType() {
        return Question.MULTIPLE_CHOICE;
    }
    
    public ArrayList<String> getChoices() {
        return choices;
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return answers;
    }

    @Override
    public boolean isCaseSensitive() {
        return false;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean isCorrect(ArrayList<String> userAnswer) {
        if(userAnswer.isEmpty()) return false;
        if(answers.isEmpty()) return false;
        if(userAnswer.get(0).equals(answers.get(0))) return true;
        return false;
    }
}
