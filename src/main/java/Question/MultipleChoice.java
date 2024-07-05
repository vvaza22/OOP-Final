package Question;

import java.util.ArrayList;

public class MultipleChoice extends Question {

    /* List of choices the user has */
    private final ArrayList<Choice> choiceList;
    private final int correctAnswerIndex;
    private int userAnswer;

    public MultipleChoice(
            String questionText,
            int questionId,
            ArrayList<Choice> choiceList,
            int correctAnswerIndex
    ) {
        super(questionText, QuestionType.MULTIPLE_CHOICE, questionId);
        this.choiceList = choiceList;
        this.correctAnswerIndex = correctAnswerIndex;
        this.userAnswer = -1;
    }

    public ArrayList<Choice> getChoices() {
        return choiceList;
    }

    public Choice getCorrectChoice() {
        for(Choice choice : choiceList) {
            if(choice.isCorrect()) {
                return choice;
            }
        }
        return null;
    }

    public Choice getUserChoice() {
        for(Choice choice : choiceList) {
            if(choice.getId() == userAnswer) {
                return choice;
            }
        }
        return null;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    private boolean isAnswerCorrect() {
        return correctAnswerIndex == userAnswer;
    }

    @Override
    public int getMaxScore() {
        return 1;
    }

    @Override
    public boolean hasAnswer() {
        return this.userAnswer != -1;
    }

    @Override
    public int countPoints() {
        return isAnswerCorrect() ? 1 : 0;
    }
}
