package Question;

import java.util.HashMap;

/**
 * This is just a wrapper for question types for passing them to the front-end
 */
public class QuestionType {

    public static final int QUESTION_RESPONSE = 1;
    public static final int FILL_BLANK = 2;
    public static final int MULTIPLE_CHOICE = 3;
    public static final int PICTURE_RESPONSE = 4;

    private String typeName;
    private String typeText;
    private int questionType;

    public QuestionType(String typeName, String typeText, int questionType) {
        this.typeName = typeName;
        this.questionType = questionType;
        this.typeText = typeText;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getQuestionType() {
        return questionType;
    }

    public String getTypeText() {
        return typeText;
    }

    public static HashMap<Integer, QuestionType> createMap() {
        HashMap<Integer, QuestionType> typeMap = new HashMap<Integer, QuestionType>();

        // Register types
        typeMap.put(QuestionType.QUESTION_RESPONSE,
                new QuestionType("QUESTION_RESPONSE", "Question-Response", QuestionType.QUESTION_RESPONSE)
        );
        typeMap.put(QuestionType.FILL_BLANK,
                new QuestionType("FILL_BLANK", "Fill in the Blank", QuestionType.FILL_BLANK)
        );
        typeMap.put(QuestionType.MULTIPLE_CHOICE,
                new QuestionType("MULTIPLE_CHOICE", "Multiple Choice", QuestionType.MULTIPLE_CHOICE)
        );
        typeMap.put(QuestionType.PICTURE_RESPONSE,
                new QuestionType("PICTURE_RESPONSE", "Picture-Response", QuestionType.PICTURE_RESPONSE)
        );

        return typeMap;
    }
}
