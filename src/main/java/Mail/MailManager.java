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
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return txtMsg;
    }



}
