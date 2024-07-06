package Quiz;

import Question.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class Quiz {

    public static final int ONE_PAGE = 1;
    public static final int MULTIPLE_PAGES = 2;

    /* Quiz Parameters */
    private final int id;
    private final String name;
    private final int author_id;
    private final String description;
    private final boolean randomize;
    private final boolean practiceMode;
    private final boolean immediateCorrection;
    private final int displayMode;
    private final String create_time;
    private final String quizImage;

    ArrayList<Question> questionList;
    HashMap<Integer, Question> questionMap;

    public Quiz(
            int id,
            String name,
            int author_id,
            String description,
            String quizImage,
            boolean randomize,
            boolean practiceMode,
            boolean immediateCorrection,
            int displayMode,
            String create_time,
            ArrayList<Question> questionList
    ) {
        this.id = id;
        this.name = name;
        this.author_id = author_id;
        this.description = description;
        this.quizImage = quizImage;
        this.randomize = randomize;
        this.practiceMode = practiceMode;
        this.immediateCorrection = immediateCorrection;
        this.displayMode = displayMode;
        this.create_time = create_time;
        this.questionList = questionList;

        createQuestionMap();

        if(isRandomized()) {
            shuffleQuestions();
        }
    }

    public String getImage() {
        return quizImage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAuthor_id() {
        return author_id;
    }

    private void shuffleQuestions() {
        Collections.shuffle(questionList);
    }

    public boolean isPracticeAllowed() {
        return practiceMode;
    }

    public String getCreate_time() {
        return this.create_time;
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

    public String getDescription() {
        return description;
    }

    public int getNumberOfQuestions() {
        return questionList.size();
    }

    public ArrayList<Question> getQuestions() {
        return questionList;
    }

    public boolean hasQuestion(int questionId) {
        return questionMap.containsKey(questionId);
    }

    public Question getQuestionById(int questionId) {
        return questionMap.get(questionId);
    }

    private void createQuestionMap() {
        questionMap = new HashMap<Integer, Question>();
        for(Question question : questionList) {
            questionMap.put(question.getId(), question);
        }
    }

    public int getMaxScore() {
        int maxScore = 0;
        for(Question question : questionList) {
            maxScore += question.getMaxScore();
        }
        return maxScore;
    }

    public int countScore() {
        int score = 0;
        for(Question question : questionList) {
            score += question.countPoints();
        }
        return score;
    }

}
