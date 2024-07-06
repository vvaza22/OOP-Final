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
}

