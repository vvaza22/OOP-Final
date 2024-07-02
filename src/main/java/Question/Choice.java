package Question;

public class Choice {
    private final String text;
    private final int id;

    public Choice(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }
}
