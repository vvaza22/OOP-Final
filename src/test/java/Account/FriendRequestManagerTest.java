package Account;

import Database.Database;
import Database.DatabaseCredentials;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.*;

public class FriendRequestManagerTest {
    private FriendRequestManager friendRequestManager;
    private AccountManager accountManager;
    private Database db;
    private Account acc1, acc2;

    @BeforeEach
    public void setUp() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://" + DatabaseCredentials.DB_HOST + "/" + DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        db = new Database(dataSource);
        accountManager = new AccountManager(db);
        friendRequestManager = new FriendRequestManager(db);

        try {
            Connection con = db.openConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=0"); // Disabling Foreign Key Checks
            stmt.executeUpdate("truncate table user_answers;");
            stmt.executeUpdate("truncate table attempts;");
            stmt.executeUpdate("truncate table choices;");
            stmt.executeUpdate("truncate table text_answers;");
            stmt.executeUpdate("truncate table questions;");
            stmt.executeUpdate("truncate table reaction;");
            stmt.executeUpdate("truncate table anno;");
            stmt.executeUpdate("truncate table achievements;");
            stmt.executeUpdate("truncate table challenges;");
            stmt.executeUpdate("truncate table notes;");
            stmt.executeUpdate("truncate table frreqs;");
            stmt.executeUpdate("truncate table friends;");
            stmt.executeUpdate("truncate table quiz;");
            stmt.executeUpdate("truncate table achievements;");
            stmt.executeUpdate("truncate table users;");
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=1"); // Enabling Foreign Key Checks
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        acc1 = new Account(
                1,
                "saxeli1",
                "gvari1",
                "username1",
                "/images/default.jpg",
                Hash.hashPassword("paroli1"),
                "me1",
                "user"
        );

        acc2 = new Account(
                2,
                "saxeli2",
                "gvari2",
                "username2",
                "/images/de1fault.jpg",
                Hash.hashPassword("paroli2"),
                "me2",
                "admin"
        );
        accountManager.registerAccount(acc1);
        accountManager.registerAccount(acc2);
    }

    @Test
    public void testRequest(){
        int fromId = acc1.getUserId();
        int toId = acc2.getUserId();
        friendRequestManager.sendRequest(fromId, toId);
        boolean isPending = friendRequestManager.isStatusByIdPen(fromId, toId);
        assertTrue(isPending);

        isPending = friendRequestManager.isStatusByIdPen(toId, fromId);
        assertFalse(isPending);
    }

    @Test
    public void testStatusChange(){
        int fromId = acc2.getUserId();
        int toId = acc1.getUserId();
        boolean isPending = friendRequestManager.isStatusByIdPen(fromId, toId);
        assertFalse(isPending); // since no request was sent yet


        friendRequestManager.sendRequest(toId, fromId);
        friendRequestManager.changeStatus(1, "ACCEPTED");
        isPending = friendRequestManager.isStatusByIdPen(toId, fromId);
        assertFalse(isPending);
    }

    @Test
    public void testUsersIdByReq(){
        int fromId = acc1.getUserId();
        int toId = acc2.getUserId();
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(fromId);
        arr.add(toId);
        friendRequestManager.sendRequest(fromId, toId);

        ArrayList<Integer> data = friendRequestManager.getUserIdsByReq(1);
        assertTrue(data.equals(arr));

        ArrayList<Integer> empty = friendRequestManager.getUserIdsByReq(2);
        assertEquals(0, empty.size());
    }
}
