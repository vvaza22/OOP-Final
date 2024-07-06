package Account;

import Database.Database;

import java.sql.*;
import java.util.ArrayList;

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

    public boolean isStatusByIdPen(int fromId, int toId){
        boolean isPending = false;
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select count(*) from frreqs where from_id=? and to_id=? and status='PENDING'"
            );
            stmt.setInt(1, fromId);
            stmt.setInt(2, toId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                isPending = rs.getInt(1) > 0;
                stmt.close();
                con.close();
                return isPending;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isPending;
    }

    public ArrayList<Integer> getUserIdsByReq(int req){
        ArrayList<Integer> users = new ArrayList<>();
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from frreqs where id=?"
            );
            stmt.setInt(1, req);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                int from = rs.getInt("from_id");
                int to = rs.getInt("to_id");
                users.add(from);
                users.add(to);
                stmt.close();
                con.close();
            }
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
