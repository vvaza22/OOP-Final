package Mail;

import Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChallengeManager {
    private final Database db;

    public ChallengeManager(Database db) {
        this.db = db;
    }

    public void sendRequest(int from, int to) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "insert into challenges (from_id, to_id, status) values (?, ?, ?)"
            );

            stmt.setInt(1, from);
            stmt.setInt(2, to);
            stmt.setString(3, "PENDING");
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
}
