package Account;

import Database.Database;

import java.sql.*;
import java.util.ArrayList;


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

    public Account getAccountById(int userId) {
        try {
            Connection con = db.openConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "select * from users where id=?"
            );
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Account ac = makeAccountObject(rs);

                stmt.close();
                con.close();

                return ac;
            }

            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Account does not exist
        return null;
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

                Account ac = makeAccountObject(rs);

                stmt.close();
                con.close();

                return ac;
            }

            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Account does not exist
        return null;
    }

    private Account makeAccountObject(ResultSet rs) throws SQLException {
        Account ac = new Account(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("user_name"),
                rs.getString("image"),
                rs.getString("password_hash"),
                rs.getString("about"),
                rs.getString("type")
        );
        return ac;
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
                            "about, " +
                            "type) " +
                            "values (?,?,?,?,?,?,?)"
            );



            stmt.setString(1,acc.getUserName());
            stmt.setString(2,acc.getFirstName());
            stmt.setString(3,acc.getLastName());
            stmt.setString(4,acc.getPassHash());
            stmt.setString(5,acc.getImage());
            stmt.setString(6, acc.getAboutMe());
            stmt.setString(7,acc.getUserType());

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

    public void updateAboutMeAccount(Account acc) {
        try {
            Connection con = db.openConnection();

            PreparedStatement stmt = con.prepareStatement(
                    "update users set about=? where user_name=?"
            );
            stmt.setString(1, acc.getAboutMe());
            stmt.setString(2, acc.getUserName());

            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProfilePictureAccount(Account acc) {
        try {
            Connection con = db.openConnection();

            PreparedStatement stmt = con.prepareStatement(
                    "update users set image=? where user_name=?"
            );
            stmt.setString(1, acc.getImage());
            stmt.setString(2, acc.getUserName());

            stmt.executeUpdate();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
