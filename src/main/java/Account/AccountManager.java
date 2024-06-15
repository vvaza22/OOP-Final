package Account;

import Database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountManager {
    private final Database db;

    public AccountManager(Database db) {
        this.db = db;
    }

    public boolean accountExists(String username) {
        return this.getAccount(username) != null;
    }

    public boolean passwordMatches(String username, String password) {
        return this.getAccount(username).checkPassword(password);
    }

    public Account getAccount(String username) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from users where user_name=?"
            );
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Account ac = new Account(
                                        rs.getString("first_name"),
                                        rs.getString("last_name"),
                                        rs.getString("user_name"),
                                        rs.getString("image"),
                                        rs.getString("password_hash"),
                                        rs.getString("type")
                );
                stmt.close();
                con.close();
                return ac;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Account does not exist
        return null;
    }

    public void registerAccount(Account acc) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "insert into users (user_name, " +
                                            "first_name, " +
                                            "last_name, " +
                                            "password_hash, " +
                                            "image, " +
                                            "type) " +
                                            "values (?,?,?,?,?,?)"
            );


            stmt.setString(1,acc.getUserName());
            stmt.setString(2,acc.getFirstName());
            stmt.setString(3,acc.getLastName());
            stmt.setString(4,acc.getPassHash());
            stmt.setString(5,acc.getImage());
            stmt.setString(6,acc.getUserType());

            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // not yet used.
    public void removeAccount(String username) {
        try {
            // if account exists:
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "delete from users where user_name=?"
            );

            stmt.setString(1,username);
            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
