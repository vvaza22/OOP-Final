package Questions;

import Question.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class FillBlankTest extends TestCase {
    @Test
    public void testVars() {
        ArrayList<String> ans1 = new ArrayList<>();
        ans1.add("Sam");
        FillBlank fb1 = new FillBlank("___ is the real main character in LOTR", 0, ans1);
        assertEquals("___ is the real main character in LOTR", fb1.getQuestionText());
        assertEquals(1, fb1.getMaxScore());


        ArrayList<String> ans2 = new ArrayList<>();
        ans2.add("Sauron");
        FillBlank fb2 = new FillBlank("At one point Annatar was ___'s alias", 1,
                ans2);
        assertEquals("At one point Annatar was ___'s alias", fb2.getQuestionText());
        assertEquals(2, fb2.getType());
        assertEquals(1, fb2.getMaxScore());
        ArrayList<String> ansTest = new ArrayList<>();
        ansTest.add("sauron");
        assertEquals(fb2.getCorrectAnswers(), ansTest);
    }

    @Test
    public void testCheckAnswer() {
        ArrayList<String> correct1 = new ArrayList<>();
        correct1.add("Megadeth");
        correct1.add("Metallica");
        FillBlank fb1 = new FillBlank("Dave Mustaine formed ___ after he was kicked out of ___", 0,
                correct1);
        ArrayList<String> ans1 = new ArrayList<>();
        assertEquals(2, fb1.getCorrectAnswers().size());
        ans1.add("megadeth");
        ans1.add("metallica");
        assertEquals(ans1, fb1.getCorrectAnswers());
        ans1.add("blank");
        assertFalse(fb1.getCorrectAnswers().contains(ans1.get(2)));


        ArrayList<String> correct2 = new ArrayList<>();
        correct2.add("Miquella");
        correct2.add("Rot");
        FillBlank fb2 = new FillBlank("Malenia, Blade of ___ is cursed with Scarlet ___", 1,
                correct2);
        ArrayList<String> ans2 = new ArrayList<>();
        assertEquals(2, fb2.getCorrectAnswers().size());
        ans2.add("miquella");
        ans2.add("rot");
        assertEquals(ans2, fb2.getCorrectAnswers());
    }

}
