package Question;

public class Choice {
    private final String text;
    private final int id;
    private final boolean isCorrect;

    public Choice(String text, int id, boolean isCorrect) {
        this.text = text;
        this.id = id;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}