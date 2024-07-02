package Question;

import java.util.ArrayList;

public class MultipleChoice extends Question {

    /* List of choices the user has */
    private final ArrayList<Choice> choiceList;
    private final int correctAnswerIndex;
    private int userAnswer;

    public MultipleChoice(
            String questionText,
            ArrayList<Choice> choiceList,
            int correctAnswerIndex
    ) {
        super(questionText, MULTIPLE_CHOICE);
        this.choiceList = choiceList;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public ArrayList<Choice> getChoices() {
        return choiceList;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }

    private boolean isAnswerCorrect() {
        return correctAnswerIndex == userAnswer;
    }

    @Override
    public int countPoints() {
        return isAnswerCorrect() ? 1 : 0;
    }
}
