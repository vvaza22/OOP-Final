package Quiz;

public class Attempt {

    private int attemptId;
    private int quizId;
    private int userId;
    private int maxScore;
    private int curScore;

    public Attempt(
            int attemptId,
            int quizId,
            int userId,
            int maxScore,
            int curScore
    ) {
        this.attemptId = attemptId;
        this.quizId = quizId;
        this.userId = userId;
        this.maxScore = maxScore;
        this.curScore = curScore;
    }
}
