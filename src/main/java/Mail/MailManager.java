package Mail;

import Account.Account;
import Database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MailManager {
    private final Database db;

    public MailManager(Database db) {
        this.db = db;
    }

    public ArrayList<Integer> getFriendRequests(int userId){
        try{
            Connection con = db.openConnection();
            ArrayList<Integer> reqs = new ArrayList<>();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from users where to_id = ?"
            );
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
//                Account from = new Account(
//                        rs.get
//                )
//                FriendRequestMail req = new FriendRequestMail(
//                        rs.getInt("from_id"),
//                        rs.getInt("to_id"),
//                        rs.getString("status")
//                );



            }




            return reqs;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

}
