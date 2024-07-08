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

    public Quiz getQuiz() {
        return this.quiz;
    }

    private void populateQueue() {
        ArrayList<Question> questionList = quiz.getQuestions();
        for (Question question : questionList) {
            PracticeQuestion pq = new PracticeQuestion(question, N_REPEAT);
            questionQueue.add(pq);
        }
    }

    public boolean hasNextQuestion() {
        if(curQuestion != null) {
            // If the current question can repeat another time, we have the next question
            if(curQuestion.getRepeatTimes() > 0) {
                return true;
            }
        }
        return !questionQueue.isEmpty();
    }

    private void addPreviousQuestionBack() {
        if(curQuestion == null) {
            return;
        }
        Question curQuestionObj = curQuestion.getQuestion();
        if(curQuestionObj.countPoints() == curQuestionObj.getMaxScore()) {
            if(curQuestion.getRepeatTimes() > 0) {
                curQuestionObj.resetAnswer();
                questionQueue.add(curQuestion);
            }
        } else {
            curQuestionObj.resetAnswer();
            questionQueue.add(curQuestion);
        }

        curQuestion = null;
    }

    public PracticeQuestion getNextQuestion() {
        addPreviousQuestionBack();
        this.curQuestion = questionQueue.remove();
        return this.curQuestion;
    }

    public PracticeQuestion getCurrentPracticeQuestion() {
        return this.curQuestion;
    }

}
