package Practice;

import Question.Question;
import Quiz.Quiz;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PracticeQuiz {

    private static final int N_REPEAT = 3;

    private PracticeQuestion curQuestion;
    private final LinkedList<PracticeQuestion> questionQueue;
    private final Quiz quiz;

    public PracticeQuiz(Quiz quiz) {
        questionQueue = new LinkedList<>();
        this.quiz = quiz;
        this.curQuestion = null;
        populateQueue();
    }

    private void populateQueue() {
        ArrayList<Question> questionList = quiz.getQuestions();
        for (Question question : questionList) {
            PracticeQuestion pq = new PracticeQuestion(question, N_REPEAT);
            questionQueue.add(pq);
        }
    }

    boolean hasNextQuestion() {
        return !questionQueue.isEmpty();
    }

    private void addPreviousQuestionBack() {
        if(curQuestion == null) {
            return;
        }
        Question curQuestionObj = curQuestion.getQuestion();
        if(curQuestionObj.countPoints() == curQuestionObj.getMaxScore()) {
            curQuestion.reduceRepeatTimes();
            if(curQuestion.getRepeatTimes() > 0) {
                curQuestionObj.resetAnswer();
                questionQueue.add(curQuestion);
            }
        } else {
            curQuestion.resetRepeatTimes();
            curQuestionObj.resetAnswer();
            questionQueue.add(curQuestion);
        }

        curQuestion = null;
    }

    PracticeQuestion getNextQuestion() {
        addPreviousQuestionBack();
        this.curQuestion = questionQueue.remove();
        return this.curQuestion;
    }

    PracticeQuestion getCurrentPracticeQuestion() {
        return this.curQuestion;
    }

}
