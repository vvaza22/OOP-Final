package Question;

import Account.Hash;

import java.util.ArrayList;
import java.util.HashSet;

public class MultipleAns extends Question{
    private boolean isOrdered;
    public MultipleAns(String question, boolean caseSensitive, ArrayList<String> answers,
                       boolean isOrdered){
        super(question, caseSensitive, answers);
        this.isOrdered = isOrdered;
    }

    public boolean isOrdered(){
        return isOrdered;
    }

    @Override
    public boolean isCaseSensitive() {
        return caseSensitive;
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
    public ArrayList<String> getAnswers() {
        return answers;
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public int isCorrect(ArrayList<String> userAnswer) {
        if(userAnswer.isEmpty() || answers.isEmpty()) return 0;
        int correct = 0;
        if(isOrdered){
            for(int i=0; i<userAnswer.size(); i++){
                if(userAnswer.get(i).equals(answers.get(i))) correct++;
            }
        }else{
            HashSet<String> helper = new HashSet<String>();

            helper.addAll(userAnswer);
            helper.addAll(answers);

            correct = userAnswer.size() + answers.size() - helper.size();
        }
        return correct;
    }

}
