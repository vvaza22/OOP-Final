package Quiz;

import Question.UserAnswer;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Attempt {

    private int attemptId;
    private int quizId;
    private int userId;
    private int maxScore;
    private int userScore;
    private String timestamp;
    private ArrayList<UserAnswer> userAnswers;

    public Attempt(
            int attemptId,
            int quizId,
            int userId,
            int maxScore,
            int userScore,
            String timestamp,
            ArrayList<UserAnswer> userAnswers
    ) {
        this.attemptId = attemptId;
        this.quizId = quizId;
        this.userId = userId;
        this.maxScore = maxScore;
        this.userScore = userScore;
        this.userAnswers = userAnswers;
        this.timestamp = timestamp;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public String getTime() {
        return timestamp;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getUserScore() {
        return userScore;
    }

    public ArrayList<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

}
