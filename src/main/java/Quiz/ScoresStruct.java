package Quiz;

public class ScoresStruct {
    private String username;
    private int score;


    public ScoresStruct(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public String getUserName() {
        return this.username;
    }
}
