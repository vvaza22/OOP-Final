package Practice;

import Question.Question;

public class PracticeQuestion {

    private final int defaultRepeatTimes;

    private int repeatTimes;
    private int answeredCorrectly;
    private int answeredIncorrectly;

    private final Question question;

    public PracticeQuestion(Question question, int repeatTimes) {
        this.defaultRepeatTimes = repeatTimes;
        this.question = question;
        this.repeatTimes = repeatTimes;
        this.answeredCorrectly = 0;
        this.answeredIncorrectly = 0;
    }

    public void updateStatistics() {
        if(question.countPoints() == question.getMaxScore()) {
            recordCorrectAnswer();
            reduceRepeatTimes();
        } else {
            recordIncorrectAnswer();
            resetRepeatTimes();
        }
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }

    public Question getQuestion() {
        return question;
    }

    public void reduceRepeatTimes() {
        repeatTimes--;
    }

    public void resetRepeatTimes() {
        this.repeatTimes = this.defaultRepeatTimes;
    }

    public int getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public int getAnsweredIncorrectly() {
        return answeredIncorrectly;
    }

    public void recordCorrectAnswer() {
        answeredCorrectly++;
    }

    public void recordIncorrectAnswer() {
        answeredIncorrectly++;
    }

}
