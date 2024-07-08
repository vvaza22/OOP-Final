package Mail;

import Account.Account;
import Database.Database;
import Account.AccountManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MailManager {
    private final Database db;
    private final AccountManager amgr;

    public MailManager(Database db, AccountManager amgr) {
        this.db = db;
        this.amgr = amgr;
    }

    public ArrayList<FriendRequestMail> getFriendRequests(int userId){
        ArrayList<FriendRequestMail> reqs = new ArrayList<>();
        try{
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from frreqs where to_id = ?"
            );
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Account from = amgr.getAccountById(rs.getInt("from_id"));
                Account to = amgr.getAccountById(rs.getInt("to_id"));
                FriendRequestMail req = new FriendRequestMail(
                        from,
                        to,
                        rs.getInt("id"),
                        rs.getString("status")
                );

                reqs.add(req);
            }
            stmt.close();
            con.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return reqs;
    }

    public ArrayList<NoteMail> getNotes(int userId){
        ArrayList<NoteMail> txtMsg = new ArrayList<>();
        try{
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from notes where to_id = ?"
            );
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Account from = amgr.getAccountById(rs.getInt("from_id"));
                Account to = amgr.getAccountById(rs.getInt("to_id"));
                NoteMail msg = new NoteMail(
                        from,
                        to,
                        rs.getString("note"),
                        rs.getInt("id")
                );

                txtMsg.add(msg);
            }
            stmt.close();
            con.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return txtMsg;
    }

    public ArrayList<ChallengeMail> getChallenges(int userId){
        ArrayList<ChallengeMail> chlg = new ArrayList<ChallengeMail>();
        try{
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from challenges c left outer join quiz q on c.quiz_id = q.quiz_id where to_id = ?"
            );
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Account from = amgr.getAccountById(rs.getInt("from_id"));
                Account to = amgr.getAccountById(rs.getInt("to_id"));
                ChallengeMail chall = new ChallengeMail(
                        from,
                        to,
                        rs.getInt("id"),
                        rs.getInt("quiz_id"),
                        rs.getString("name"),
                        rs.getString("status")
                );

                chlg.add(chall);
            }
            stmt.close();
            con.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return chlg;
    }

    public void addNote(int fromId, int toId, String note){
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "insert into notes (from_id, to_id, note) values (?, ?, ?)"
            );

            stmt.setInt(1, fromId);
            stmt.setInt(2, toId);
            stmt.setString(3, note);
            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countChallenges(int userId) {
        int challenges = 0;
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select count(*) as cnt " +
                            "from challenges " +
                            "where status='PENDING' " +
                            "and to_id =?;"
            );

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                challenges = rs.getInt("cnt");
            }
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return challenges;
    }

    public int countNotes(int userId) {
        int notes = 0;
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select count(*) as cnt " +
                            "from notes " +
                            "where to_id =?;"
            );

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                notes = rs.getInt("cnt");
            }
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return notes;
    }

    public int countFriendRequests(int userId) {
        int frreqs = 0;
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select count(*) as cnt " +
                            "from frreqs " +
                            "where status='PENDING' " +
                            "and to_id =?;"
            );

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                frreqs = rs.getInt("cnt");
            }
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return frreqs;
    }



}
