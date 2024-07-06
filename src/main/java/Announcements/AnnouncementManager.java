package Announcements;
import Database.Database;
import Question.Question;
import Quiz.Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AnnouncementManager {
    private final Database db;
    public AnnouncementManager(Database db) {
        this.db = db;
    }

    public int addAnnouncement(Announcement anno) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "insert into anno(author_id, title, body, likes, dislikes, create_time) " +
                            "values (?,?,?,?,?,?);", PreparedStatement.RETURN_GENERATED_KEYS
            );

            stmt.setInt(1, anno.getAuthorId());
            stmt.setString(2, anno.getTitle());
            stmt.setString(3, anno.getBody());
            stmt.setInt(4, anno.getNumLike());
            stmt.setInt(5, anno.getNumDislike());
            stmt.setString(6, anno.getCreateTime());

            stmt.executeUpdate();
            int annoNewId = 0;
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                annoNewId = rs.getInt(1);
            }
            stmt.close();
            con.close();
            return annoNewId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Gives a list of all announcements, starting from latest to earliest.
     */

    public ArrayList<Announcement> getAnnouncements() {
        ArrayList<Announcement> list = new ArrayList<>();
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select anno_id AS ID, create_time " +
                            "from anno " +
                            "order by 2 desc;"
            );

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("ID");
                list.add(getAnnouncement(id));
            }
            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public Announcement getAnnouncement(int id) {
        try {
            // Open connection to the database
            Connection con = db.openConnection();

            // Retrieve the quiz
            PreparedStatement stmt = con.prepareStatement(
                    "select * from anno where anno_id=?"
            );
            stmt.setInt(1, id);

            // Create Quiz Object
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Announcement anno = new Announcement(
                        rs.getInt("anno_id"),
                        rs.getString("title"),
                        (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("create_time"))),
                        rs.getString("body"),
                        rs.getInt("author_id"),
                        rs.getInt("likes"),
                        rs.getInt("dislikes")
                );

                // Close connection to the database
                stmt.close();
                con.close();

                return anno;
            }

            // Close connection to the database
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Announcement does not exist
        return null;
    }

}
