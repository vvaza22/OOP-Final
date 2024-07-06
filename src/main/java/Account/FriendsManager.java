package Account;

import Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendsManager {
    private final Database db;

    public FriendsManager(Database db) {
        this.db = db;
    }

    public void addFriend(int friend1Id, int friend2Id) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt1 = con.prepareStatement(
                    "insert into friends (friend_A, friend_B) values (?, ?)"
            );

            stmt1.setInt(1, friend1Id);
            stmt1.setInt(2, friend2Id);
            stmt1.executeUpdate();
            stmt1.close();

            PreparedStatement stmt2 = con.prepareStatement(
                    "insert into friends (friend_A, friend_B) values (?, ?)"
            );

            stmt2.setInt(1, friend2Id);
            stmt2.setInt(2, friend1Id);
            stmt2.executeUpdate();
            stmt2.close();

            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> friendsList (int userId){
        try {
            ArrayList<Integer> friendsData = new ArrayList<Integer>();
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from friends where friend_A=?"
            );
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Integer friend = rs.getInt("friend_B");
                friendsData.add(friend);
            }
            stmt.close();
            con.close();
            return friendsData;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean areFriends(int friend1Id, int friend2Id){
        boolean ifFriends = false;
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select count(*) from friends where " +
                            "(friend_A=? and friend_B=?)  or " +
                            "(friend_A=? and friend_B=?) "
            );
            stmt.setInt(1, friend1Id);
            stmt.setInt(2, friend2Id);
            stmt.setInt(3, friend2Id);
            stmt.setInt(4, friend1Id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                ifFriends = rs.getInt(1) == 2;
                stmt.close();
                con.close();
                return ifFriends;
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ifFriends;
    }

    public void deleteFromFriends(int whoRemovesID, int whoIsRemovedID) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt1 = con.prepareStatement(
                    "delete from friends where friend_A=? and friend_B=?"
            );

            stmt1.setInt(1,whoRemovesID);
            stmt1.setInt(2, whoIsRemovedID);
            stmt1.executeUpdate();
            stmt1.close();

            PreparedStatement stmt2 = con.prepareStatement(
                    "delete from friends where friend_A=? and friend_B=?"
            );

            stmt2.setInt(1,whoIsRemovedID);
            stmt2.setInt(2, whoRemovesID);
            stmt2.executeUpdate();
            stmt2.close();

            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

