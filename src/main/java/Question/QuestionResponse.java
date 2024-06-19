package Question;

import java.util.ArrayList;

public class QuestionResponse extends Question{

    public QuestionResponse(String question, boolean caseSensitive, String answer){
        super(question, caseSensitive, new ArrayList<String>());
        answers.add(answer);
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public int getType() {
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
    public int isCorrect(ArrayList<String> userAnswer) {
        if(userAnswer.isEmpty()) return 0;
        if(answers.isEmpty()) return 0;
        if(userAnswer.get(0).equals(answers.get(0))) return 1;
        return 0;
    }

    @Override
    public int getScore() {
        return 1;
    }
}
