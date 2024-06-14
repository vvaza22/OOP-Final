package Announcements;

public class Announcement {

    private String title;
    private String date;
    private String text;
    private String author;
    private int num_like;
    private int num_dislikes;

    public Announcement(String title, String date, String text, String author, int num_like, int num_dislikes){
        this.title = title;
        this.date = date;
        this.text = text;
        this.author = author;
        this.num_dislikes = num_dislikes;
        this.num_like = num_like;
    }

    public String get_title(){
        return title;
    }

    public String get_date(){
        return date;
    }

    public String get_text(){
        return text;
    }

    public String get_author(){
        return author;
    }

    public int get_like() {
        return num_like;
    }

    public int get_dislike(){
        return num_dislikes;
    }

}
