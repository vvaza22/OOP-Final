package Question;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTypeTest {

    @Test
    public void getTest() {
        QuestionType type = new QuestionType("A", "My Type", 1);
        assertEquals("A", type.getTypeName());
        assertEquals("My Type", type.getTypeText());
        assertEquals(1, type.getQuestionType());
    }

    @Test
    public void mapTest() {
        HashMap<Integer, QuestionType> typeMap = QuestionType.createMap();
        QuestionType type1 = typeMap.get(QuestionType.QUESTION_RESPONSE);
        QuestionType type2 = typeMap.get(QuestionType.FILL_BLANK);
        QuestionType type3 = typeMap.get(QuestionType.MULTIPLE_CHOICE);
        QuestionType type4 = typeMap.get(QuestionType.PICTURE_RESPONSE);

        assertEquals(QuestionType.QUESTION_RESPONSE, type1.getQuestionType());
        assertEquals(QuestionType.FILL_BLANK, type2.getQuestionType());
        assertEquals(QuestionType.MULTIPLE_CHOICE, type3.getQuestionType());
        assertEquals(QuestionType.PICTURE_RESPONSE, type4.getQuestionType());
    }
}