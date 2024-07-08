package Questions;

import Question.FillBlank;
import Question.QuestionResponse;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class QuestionResponseTest extends TestCase {
    @Test
    public void testVars() {
        ArrayList<String> ans1 = new ArrayList<>();
        ans1.add("Orchid");
        QuestionResponse qr1 = new QuestionResponse("What is the first album of Opeth called?", 0, ans1);
        assertEquals("What is the first album of Opeth called?", qr1.getQuestionText());
        assertEquals(1, qr1.getMaxScore());


        ArrayList<String> ans2 = new ArrayList<>();
        ans2.add("Edge of Sanity");
        QuestionResponse qr2 = new QuestionResponse("Which band released two concept albums \"Crimson\" and \"Crimson II\"?", 1,
                ans2);
        assertEquals("Which band released two concept albums \"Crimson\" and \"Crimson II\"?", qr2.getQuestionText());
        assertEquals(1, qr2.getType());
        assertEquals(1, qr2.getMaxScore());
        ArrayList<String> ansTest = new ArrayList<>();
        ansTest.add("edge of sanity");
        assertEquals(ansTest, qr2.getCorrectAnswers());
    }

    @Test
    public void testCheckAnswer() {
        ArrayList<String> correct1 = new ArrayList<>();
        correct1.add("Black Sabbath");
        QuestionResponse qr1 = new QuestionResponse("Which band is regarded as the inventors of Metal?", 0,
                correct1);
        ArrayList<String> ans1 = new ArrayList<>();
        assertEquals(1, qr1.getCorrectAnswers().size());
        ans1.add("black sabbath");
        assertEquals(ans1, qr1.getCorrectAnswers());
        ans1.add("blank");
        assertFalse(qr1.getCorrectAnswers().contains(ans1.get(1)));


        ArrayList<String> correct2 = new ArrayList<>();
        correct2.add("Radahn");
        QuestionResponse qr2 = new QuestionResponse("Which character comes back as a boss in the Elden Ring DLC?", 1,
                correct2);
        ArrayList<String> ans2 = new ArrayList<>();
        assertFalse(2 == qr2.getCorrectAnswers().size());
        ans2.add("radahn");
        assertEquals(ans2, qr2.getCorrectAnswers());
        assertFalse(qr2.getId()==0);
        assertTrue(qr2.getType()==1);
    }
}
