package Quiz;

import Question.Question;

import java.util.ArrayList;

public class Quiz {

    public static final int ONE_PAGE = 1;
    public static final int MULTIPLE_PAGES = 2;

    /* Quiz Parameters */
    private final int id;
    private final String name;
    private final Account author;
    private final String description;
    private final boolean randomize;
    private final boolean practiceMode;
    private final boolean immediateCorrection;
    private final int displayMode;

    ArrayList<Question> questionList;

    public Quiz(
            int id,
            String name,
            Account author,
            String description,
            boolean randomize,
            boolean practiceMode,
            boolean immediateCorrection,
            int displayMode,
            ArrayList<Question> questionList
    ) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.randomize = randomize;
        this.practiceMode = practiceMode;
        this.immediateCorrection = immediateCorrection;
        this.displayMode = displayMode;
        this.questionList = questionList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPracticeAllowed() {
        return practiceMode;
    }

    public boolean isRandomized() {
        return randomize;
    }

    public boolean isImmediateCorrectionOn() {
        return immediateCorrection;
    }

    public int getDisplayMode() {
        return displayMode;
    }

    public int getAuthor() {
        return
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfQuestions() {
        return questionList.size();
    }

}
