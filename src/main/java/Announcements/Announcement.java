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

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    public String getText(){
        return text;
    }

    public String getAuthor(){
        return author;
    }

    public int getNumLike() {
        return num_like;
    }

    public int getNumDislike(){
        return num_dislikes;
    }

}
