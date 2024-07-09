package Quiz;

import Question.UserAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class AttemptTest {
    Attempt attempt;
    ArrayList<UserAnswer> answers;
    UserAnswer userAns1, userAns2;

    @BeforeEach
    public void setUp(){
        answers = new ArrayList<>();
        userAns1 = new UserAnswer(1, "question1", "answer1", "answer1", 1);
        userAns2 = new UserAnswer(1, "question2", "answer2", "answer 2", 0);
        answers.add(userAns1);
        answers.add(userAns2);
        attempt = new Attempt(
                1,
                1,
                1,
                5,
                3,
                "2024-07-09",
                answers
        );
    }

    @Test
    public void testGets() {
        assertEquals(1, attempt.getAttemptId());
        assertEquals("2024-07-09", attempt.getTime());
        assertEquals(1, attempt.getQuizId());
        assertEquals(1, attempt.getUserId());
        assertEquals(5, attempt.getMaxScore());
        assertEquals(3, attempt.getUserScore());

    }

    @Test
    public void testGetUserAnswers() {
        ArrayList<UserAnswer> userAnswers = attempt.getUserAnswers();
        assertEquals(2, userAnswers.size());
        assertEquals("question1", userAnswers.get(0).getQuestion());
        assertEquals("answer1", userAnswers.get(0).getUserAnswer());
        assertEquals("answer1", userAnswers.get(0).getCorrectAnswer());
        assertEquals(1, userAnswers.get(0).getPoints());

        assertEquals("question2", userAnswers.get(1).getQuestion());
        assertEquals("answer2", userAnswers.get(1).getUserAnswer());
        assertEquals("answer 2", userAnswers.get(1).getCorrectAnswer());
        assertEquals(0, userAnswers.get(1).getPoints());
    }
}
