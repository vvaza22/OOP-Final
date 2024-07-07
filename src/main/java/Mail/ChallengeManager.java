package Mail;

import Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChallengeManager {
    private final Database db;

    public ChallengeManager(Database db) {
        this.db = db;
    }

    public void sendChallenge(int from, int to, int quiz_id) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "insert into challenges (from_id, to_id, quiz_id, status) values (?, ?, ?, ?)"
            );

            stmt.setInt(1, from);
            stmt.setInt(2, to);
            stmt.setInt(3, quiz_id);
            stmt.setString(4, "PENDING");
            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeStatus(int id, String newStatus){
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "update challenges set status=? where id=?"
            );

            stmt.setInt(2, id);
            stmt.setString(1, newStatus);
            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean challengeExists(int fromId, int toId, int quizId){
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from challenges where from_id=? and to_id=? and quiz_id=?"
            );

            stmt.setInt(1, fromId);
            stmt.setInt(2, toId);
            stmt.setInt(3, quizId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String status = rs.getString("status");
                if(status.equals("PENDING") || status.equals("CHL_ACCEPTED")){
                    return true;
                }
            }
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
