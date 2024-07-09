package Practice;

import Question.*;
import Quiz.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PracticeQuizTest {

    private static Quiz q;
    @BeforeAll
    public static void setUp() {
        ArrayList<Question> qList = new ArrayList<>();

        ArrayList<String> aList1 = new ArrayList<>();
        aList1.add("A");
        aList1.add("B");
        aList1.add("C");
        Question q1 = new QuestionResponse("Question 1", 1, aList1);

        ArrayList<String> aList2 = new ArrayList<>();
        aList2.add("M");
        aList2.add("N");
        Question q2 = new QuestionResponse("Question 2", 2, aList2);

        qList.add(q1);
        qList.add(q2);

        q = new Quiz(
                1,
                "Test Quiz",
                1,
                "Test Desc",
                "some_image.jpg",
                false,
                true,
                false,
                1,
                "2024",
                qList
        );
    }

    @Test
    public void simpleTest() {
        PracticeQuiz pq = new PracticeQuiz(q);
        assertTrue(pq.hasNextQuestion());
        assertEquals(q, pq.getQuiz());
    }

    @Test
    public void nextQuestionTest() {
        PracticeQuiz pq = new PracticeQuiz(q);
        assertTrue(pq.hasNextQuestion());
        PracticeQuestion next = pq.getNextQuestion();
        assertEquals(q.getQuestions().get(0), next.getQuestion());
        assertEquals(next, pq.getCurrentPracticeQuestion());
        assertTrue(pq.hasNextQuestion());
    }

    @Test
    public void lastQuestionTest() {
        PracticeQuiz pq = new PracticeQuiz(q);

        ArrayList<Question> qs = q.getQuestions();

        PracticeQuestion p1;
        QuestionResponse q1;
        PracticeQuestion p2;
        QuestionResponse q2;

        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();

        p1 = pq.getCurrentPracticeQuestion();
        q1 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(0), q1);
        q1.setAnswer("A");
        p1.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q1.resetAnswer();

        p2 = pq.getCurrentPracticeQuestion();
        q2 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(1), q2);
        q2.setAnswer("M");
        p2.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q2.resetAnswer();

        p1 = pq.getCurrentPracticeQuestion();
        q1 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(0), q1);
        q1.setAnswer("A");
        p1.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q1.resetAnswer();

        p2 = pq.getCurrentPracticeQuestion();
        q2 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(1), q2);
        q2.setAnswer("M");
        p2.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q2.resetAnswer();

        p1 = pq.getCurrentPracticeQuestion();
        q1 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(0), q1);
        q1.setAnswer("A");
        p1.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q1.resetAnswer();

        p2 = pq.getCurrentPracticeQuestion();
        q2 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(1), q2);
        q2.setAnswer("M");
        p2.updateStatistics();
        assertFalse(pq.hasNextQuestion());
        q2.resetAnswer();
    }


    @Test
    public void wrongQuestionTest() {
        PracticeQuiz pq = new PracticeQuiz(q);

        ArrayList<Question> qs = q.getQuestions();

        PracticeQuestion p1;
        QuestionResponse q1;
        PracticeQuestion p2;
        QuestionResponse q2;

        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();

        p1 = pq.getCurrentPracticeQuestion();
        q1 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(0), q1);
        q1.setAnswer("A");
        p1.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q1.resetAnswer();

        p2 = pq.getCurrentPracticeQuestion();
        q2 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(1), q2);
        q2.setAnswer("M");
        p2.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q2.resetAnswer();

        p1 = pq.getCurrentPracticeQuestion();
        q1 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(0), q1);
        q1.setAnswer("A");
        p1.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q1.resetAnswer();

        p2 = pq.getCurrentPracticeQuestion();
        q2 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(1), q2);
        q2.setAnswer("M");
        p2.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q2.resetAnswer();

        p1 = pq.getCurrentPracticeQuestion();
        q1 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(0), q1);
        q1.setAnswer("A");
        p1.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q1.resetAnswer();

        p2 = pq.getCurrentPracticeQuestion();
        q2 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(1), q2);
        q2.setAnswer("Wrong Answer");
        p2.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q2.resetAnswer();

        // Do 3 correct answers again
        p2 = pq.getCurrentPracticeQuestion();
        q2 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(1), q2);
        q2.setAnswer("M");
        p2.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q2.resetAnswer();

        p2 = pq.getCurrentPracticeQuestion();
        q2 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(1), q2);
        q2.setAnswer("M");
        p2.updateStatistics();
        assertTrue(pq.hasNextQuestion());
        pq.getNextQuestion();
        q2.resetAnswer();

        p2 = pq.getCurrentPracticeQuestion();
        q2 = (QuestionResponse)
                pq.getCurrentPracticeQuestion().getQuestion();
        assertEquals(qs.get(1), q2);
        q2.setAnswer("M");
        p2.updateStatistics();
        assertFalse(pq.hasNextQuestion());
        q2.resetAnswer();

    }

}