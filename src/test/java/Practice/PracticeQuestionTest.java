package Practice;

import Question.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PracticeQuestionTest {

    private static QuestionResponse q;

    @BeforeAll
    public static void setUp() {
        ArrayList<String> qList = new ArrayList<>();
        qList.add("A");
        qList.add("B");
        qList.add("C");
        q = new QuestionResponse("Hello?", 1, qList);
    }

    @Test
    public void simpleGetTest() {
        PracticeQuestion pq = new PracticeQuestion(q, 3);
        assertEquals(q, pq.getQuestion());
        assertEquals(0, pq.getAnsweredCorrectly());
        assertEquals(0, pq.getAnsweredIncorrectly());
        assertEquals(3, pq.getRepeatTimes());
    }

    @Test
    public void changeTest() {
        PracticeQuestion pq = new PracticeQuestion(q, 10);
        pq.recordIncorrectAnswer();
        assertEquals(0, pq.getAnsweredCorrectly());
        assertEquals(1, pq.getAnsweredIncorrectly());
        pq.recordIncorrectAnswer();
        assertEquals(0, pq.getAnsweredCorrectly());
        assertEquals(2, pq.getAnsweredIncorrectly());
        pq.recordCorrectAnswer();
        assertEquals(1, pq.getAnsweredCorrectly());
        assertEquals(2, pq.getAnsweredIncorrectly());
    }

    @Test
    public void repeatTest() {
        PracticeQuestion pq = new PracticeQuestion(q, 10);
        pq.resetRepeatTimes();
        assertEquals(10, pq.getRepeatTimes());
        pq.reduceRepeatTimes();
        assertEquals(9, pq.getRepeatTimes());
        pq.reduceRepeatTimes();
        assertEquals(8, pq.getRepeatTimes());
        pq.resetRepeatTimes();
        assertEquals(10, pq.getRepeatTimes());
    }

    @Test
    public void statisticsTest() {
        PracticeQuestion pq = new PracticeQuestion(q, 3);
        // Correct Answer
        q.setAnswer("A");
        pq.updateStatistics();
        assertEquals(1, pq.getAnsweredCorrectly());
        assertEquals(2, pq.getRepeatTimes());
        q.resetAnswer();
        // Correct Answer
        q.setAnswer("B");
        pq.updateStatistics();
        assertEquals(2, pq.getAnsweredCorrectly());
        assertEquals(1, pq.getRepeatTimes());
        q.resetAnswer();
        // Incorrect Answer
        q.setAnswer("K");
        pq.updateStatistics();
        assertEquals(2, pq.getAnsweredCorrectly());
        assertEquals(1, pq.getAnsweredIncorrectly());
        assertEquals(3, pq.getRepeatTimes());
        q.resetAnswer();
    }

}