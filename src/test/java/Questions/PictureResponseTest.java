package Questions;

import Question.PictureResponse;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PictureResponseTest extends TestCase {
    @Test
    public void testVars() {
        ArrayList<String> ans1 = new ArrayList<String>();
        ans1.add("Elden Ring");
        PictureResponse pr1 = new PictureResponse("What game is this screenshot from?", "erdtree.png",
                1, ans1);
        assertEquals("What game is this screenshot from?", pr1.getQuestionText());
        assertEquals("erdtree.png", pr1.getPicture());
        assertEquals(1, pr1.getMaxScore());
        assertEquals(4, pr1.getType());

        ArrayList<String> ans2 = new ArrayList<String>();
        ans2.add("Opeth");
        PictureResponse pr2 = new PictureResponse("Which band does this logo belong to?", "ologo.png",
                2, ans2);
        assertEquals("Which band does this logo belong to?", pr2.getQuestionText());
        assertEquals("ologo.png", pr2.getPicture());
        assertEquals(4, pr2.getType());
        assertEquals(1, pr2.getMaxScore());
        assertEquals("opeth", pr2.getCorrectAnswers().get(0));
    }

    @Test
    public void testGetCorrectAnswer() {
        ArrayList<String> ans1 = new ArrayList<String>();
        ans1.add("Elden Ring");
        PictureResponse pr1 = new PictureResponse("What game is this screenshot from?", "erdtree.png",
                1, ans1);
        ArrayList<String> correct1 = pr1.getCorrectAnswers();
        assertEquals("elden ring", correct1.get(0));
        assertEquals(1, correct1.size());

        ArrayList<String> ans2 = new ArrayList<String>();
        ans2.add("Opeth");
        PictureResponse pr2 = new PictureResponse("Which band does this logo belong to?", "ologo.png",
                2, ans2);
        ArrayList<String> correct2 = pr2.getCorrectAnswers();
        assertFalse(correct2.size()>1);
    }

    @Test
    public void testUserAnswer() {
        ArrayList<String> ans1 = new ArrayList<String>();
        ans1.add("Elden Ring");
        PictureResponse pr1 = new PictureResponse("What game is this screenshot from?", "erdtree.png",
                1, ans1);
        ArrayList<String> correct1 = pr1.getCorrectAnswers();
        pr1.setAnswer("Dark Souls");
        assertFalse(correct1.get(0).equals("Dark Souls"));
        assertEquals("Dark Souls", pr1.getUserAnswer());

        ArrayList<String> ans2 = new ArrayList<String>();
        ans2.add("Opeth");
        PictureResponse pr2 = new PictureResponse("Which band does this logo belong to?", "ologo.png",
                2, ans2);
        ArrayList<String> correct2 = pr2.getCorrectAnswers();
        pr2.setAnswer("Opeth");
        assertEquals(pr2.getUserAnswer().toLowerCase(), correct2.get(0));
        assertEquals(1, pr2.countPoints());
    }
}
