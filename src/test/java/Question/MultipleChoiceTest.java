package Question;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class MultipleChoiceTest {
    @Test
    public void testVars() {
        ArrayList<Choice> ans1 = new ArrayList<>();
        ans1.add(new Choice("Messmer", 0, false));
        ans1.add(new Choice("Malenia", 1, false));
        ans1.add(new Choice("Miquella", 2, false));
        ans1.add(new Choice("Ranni", 3, true));
        MultipleChoice mc1 = new MultipleChoice("Which one is Rennala's child?", 0, ans1,
                3);
        assertEquals("Which one is Rennala's child?", mc1.getQuestionText());
        assertEquals(1, mc1.getMaxScore());
        assertEquals(3, mc1.getType());

        ArrayList<Choice> ans2 = new ArrayList<>();
        ans2.add(new Choice("Dark Souls", 0, false));
        ans2.add(new Choice("Lies of P", 1, true));
        ans2.add(new Choice("Elden Ring", 2, false));
        ans2.add(new Choice("Bloodborne", 3, false));
        ans2.add(new Choice("Sekiro: Shadows Die Twice", 5, false));
        MultipleChoice mc2 = new MultipleChoice("Which one is not a FromSoftware game?", 1,
                ans2, 1);
        assertEquals("Which one is not a FromSoftware game?", mc2.getQuestionText());
        assertEquals(1, mc2.getMaxScore());
    }

    @Test
    public void testGetChoices() {
        ArrayList<Choice> ans1 = new ArrayList<>();
        ans1.add(new Choice("Messmer", 0, false));
        ans1.add(new Choice("Malenia", 1, false));
        ans1.add(new Choice("Miquella", 2, false));
        ans1.add(new Choice("Ranni", 3, true));
        MultipleChoice mc1 = new MultipleChoice("Which one is Rennala's child?", 0, ans1,
                3);
        ArrayList<Choice> pos1 = mc1.getChoices();
        assertEquals(4, pos1.size());
        assertEquals("Messmer", pos1.get(0).getText());
        assertEquals("Malenia", pos1.get(1).getText());
        assertEquals("Miquella", pos1.get(2).getText());
        assertEquals("Ranni", pos1.get(3).getText());
    }

    @Test
    public void testGetCorrectAnswer(){
        ArrayList<Choice> ans2 = new ArrayList<>();
        ans2.add(new Choice("Dark Souls", 0, false));
        ans2.add(new Choice("Lies of P", 1, true));
        ans2.add(new Choice("Elden Ring", 2, false));
        ans2.add(new Choice("Bloodborne", 3, false));
        ans2.add(new Choice("Sekiro: Shadows Die Twice", 5, false));
        MultipleChoice mc = new MultipleChoice("Which one is not a FromSoftware game?", 1,
                ans2, 1);
        ArrayList<Choice> pos = mc.getChoices();
        assertEquals(1, mc.getCorrectAnswerIndex());
        assertEquals(pos.get(1), mc.getCorrectChoice());
        mc.setAnswer(2);
        assertEquals(0, mc.countPoints());
        assertTrue(mc.hasAnswer());
        assertEquals(pos.get(2), mc.getUserChoice());
    }

    @Test
    public void testAnswer() {
        ArrayList<Choice> ans2 = new ArrayList<>();
        ans2.add(new Choice("Dark Souls", 0, false));
        ans2.add(new Choice("Lies of P", 1, true));
        ans2.add(new Choice("Elden Ring", 2, false));
        ans2.add(new Choice("Bloodborne", 3, false));
        MultipleChoice mc = new MultipleChoice("Which one is not a FromSoftware game?", 1,
                ans2, 1);
        assertFalse(mc.hasAnswer());
        mc.setAnswer(1);
        assertEquals(1, mc.countPoints());
        assertTrue(mc.hasAnswer());
        assertEquals(1, mc.getUserAnswer());
        mc.resetAnswer();
        assertFalse(mc.hasAnswer());
        mc.setAnswer(2);
        assertEquals(0, mc.countPoints());
        assertEquals(ans2.get(1), mc.getCorrectChoice());
        assertEquals(ans2.get(2), mc.getUserChoice());
        mc.resetAnswer();
        assertNull(mc.getUserChoice());
    }

    @Test
    public void noCorrectChoiceTest() {
        ArrayList<Choice> ans2 = new ArrayList<>();
        ans2.add(new Choice("Dark Souls", 0, false));
        ans2.add(new Choice("Lies of P", 1, false));
        ans2.add(new Choice("Elden Ring", 2, false));
        ans2.add(new Choice("Bloodborne", 3, false));
        MultipleChoice mc = new MultipleChoice("Which one is not a FromSoftware game?", 1,
                ans2, 1);
        assertNull(mc.getCorrectChoice());
    }

}
