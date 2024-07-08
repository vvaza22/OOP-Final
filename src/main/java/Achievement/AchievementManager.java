package Achievement;

import Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AchievementManager {
    private final Database db;

    public AchievementManager(Database db) {
        this.db = db;
    }

    public void addAchievement(int userId, int type){
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "insert into achievements (type, user_id) values (?, ?)"
            );

            stmt.setInt(1, type);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Achievement> getAchievements(int userId){
        ArrayList<Achievement> achs = new ArrayList<>();
        try{
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from achievements where user_id = ?"
            );
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                achs.add(new Achievement(userId, rs.getInt("type")));
            }
            stmt.close();
            con.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return achs;
    }

    public boolean hasAchievement(int userId, int type){
        try{
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from achievements where user_id = ? and type = ?"
            );
            stmt.setInt(1,userId);
            stmt.setInt(2,type);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            }
            stmt.close();
            con.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public void removeAchievement(int userId, int type){
        try{
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from achievements where user_id = ? and type = ?"
            );
            stmt.setInt(1,userId);
            stmt.setInt(2,type);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                PreparedStatement stmt2 = con.prepareStatement(
                        "delete from achievements where user_id = ? and type = ?"
                );
                stmt2.setInt(1,userId);
                stmt2.setInt(2,type);
                stmt2.executeUpdate();
                stmt2.close();
            }
            stmt.close();
            con.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

}
