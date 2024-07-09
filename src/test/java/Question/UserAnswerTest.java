package Question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

public class UserAnswerTest {
    UserAnswer userAns1, userAns2;

    @BeforeEach
    public void setUp() {
        userAns1 = new UserAnswer(
                1,
                "question1",
                "answer1",
                "answer1",
                1
        );
        userAns2 = new UserAnswer(
                1,
                "question2",
                "answer2",
                "answer 2",
                0
        );

    }

    @Test
    public void testGets(){
        assertEquals("question1", userAns1.getQuestion());
        assertEquals("answer1", userAns1.getUserAnswer());
        assertEquals("answer1", userAns1.getCorrectAnswer());
        assertEquals(1, userAns1.getPoints());

        assertEquals("question2", userAns2.getQuestion());
        assertEquals("answer2", userAns2.getUserAnswer());
        assertEquals("answer 2", userAns2.getCorrectAnswer());
        assertEquals(0, userAns2.getPoints());
    }

}
