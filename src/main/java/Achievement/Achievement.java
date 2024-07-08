package Achievement;

public class Achievement {
    public static final int ROOKIE_AUTHOR = 1;
    public static final int PROLIFIC_AUTHOR = 2;
    public static final int MASTER_AUTHOR = 3;
    public static final int QUIZ_SLAYER = 4;
    public static final int LORD_OF_THE_QUIZZES = 5;
    public static final int PRACTITIONER = 6;
    private int userId;
    private int type;

    public Achievement (int userId, int type) {
        this.userId = userId;
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public int getType(){
        return type;
    }
}
