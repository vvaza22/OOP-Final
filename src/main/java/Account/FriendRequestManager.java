package Account;

import Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendRequestManager {
    private final Database db;

    public FriendRequestManager(Database db) {
        this.db = db;
    }

    public void sendRequest(int from, int to) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "insert into frreqs (from_id, to_id, status) values (?, ?, ?)"
            );

            stmt.setString(1, String.valueOf(from));
            stmt.setString(2, String.valueOf(to));
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
                    "update frreqs set status=? where id=?"
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

//    public void requestResponse(int from, int to) {
//        try {
//            Connection con = db.openConnection();
//            PreparedStatement stmt = con.prepareStatement(
//                    "update frreqs set from_id=?, to_id=?, status=? where id=?"
//            );
//
//            stmt.setString(1, String.valueOf(from));
//            stmt.setString(2, String.valueOf(to));
//            stmt.setString(3, "PENDING");
//            stmt.setString(4, String.valueOf(from));
//
//            stmt.executeUpdate();
//            stmt.close();
//            con.close();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
