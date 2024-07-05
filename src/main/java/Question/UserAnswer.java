package Question;

public class UserAnswer {

    private int attemptId;
    private String question;
    private String userAnswer;
    private String correctAnswer;
    private int points;

    public UserAnswer(
            int attemptId,
            String question,
            String userAnswer,
            String correctAnswer,
            int points
    ) {
        this.attemptId = attemptId;
        this.question = question;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    public String getQuestion() {
        return question;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getPoints() {
        return points;
    }
}
