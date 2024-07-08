package Practice;

import Question.Question;

public class PracticeQuestion {

    private final int defaultRepeatTimes;
    private int repeatTimes;
    private final Question question;

    public PracticeQuestion(Question question, int repeatTimes) {
        this.defaultRepeatTimes = repeatTimes;
        this.question = question;
        this.repeatTimes = repeatTimes;
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

}
