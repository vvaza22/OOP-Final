package Announcements;

public class Announcement {

    private int id;
    private String title;
    private String date;
    private String text;
    private int authorId;
    private int numLike;
    private int numDislikes;
    private String createTime;

    public Announcement(int id, String title, String date, String text, int authorId, int numLike, int numDislikes){
        this.id = id;
        this.title = title;
        this.date = date;
        this.text = text;
        this.authorId = authorId;
        this.numDislikes = numDislikes;
        this.numLike = numLike;
        this.createTime = date;
    }

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    public String getBody(){
        return text;
    }

    public int getAuthorId(){
        return authorId;
    }

    public int getNumLike() {
        return numLike;
    }

    public int getNumDislike(){
        return numDislikes;
    }
    public int getId() { return id; }

    public String getCreateTime() {
        return createTime;
    }

}
