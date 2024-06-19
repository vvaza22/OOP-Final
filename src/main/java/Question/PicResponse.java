package Question;

import java.util.ArrayList;

public class PicResponse extends Question{
    private String picture;

    public PicResponse(String question, boolean caseSensitive, ArrayList<String> answer,
                       String picture){
        super(question, caseSensitive, answer);
        this.picture = picture;
    }

    @Override
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public ArrayList<String> getAnswers() {
        return answers;
    }

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public int isCorrect(ArrayList<String> userAnswer) {
        if(picture=="" || userAnswer.isEmpty() || answers.isEmpty()) return 0;
        if(userAnswer.get(0).equals(answers.get(0))) return 1;
        return 0;
    }
}
