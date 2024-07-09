package Account;

import Database.Database;
import Database.DatabaseCredentials;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class FriendsManagerTest {
    private FriendsManager friendsManager;
    private AccountManager accountManager;
    private Database db;
    private Account acc1, acc2, acc3;

    @BeforeEach
    public void setUp() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://" + DatabaseCredentials.DB_HOST + "/" + DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        db = new Database(dataSource);
        accountManager = new AccountManager(db);
        friendsManager = new FriendsManager(db);

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

        acc3 = new Account(
                3,
                "saxeli3",
                "gvari3",
                "username3",
                "/images/default.jpg",
                Hash.hashPassword("paroli3"),
                "me3",
                "user"
        );

        accountManager.registerAccount(acc1);
        accountManager.registerAccount(acc2);
        accountManager.registerAccount(acc3);
    }


    @Test
    public void testAddFriend(){
        int friend1Id = acc1.getUserId();
        int friend2Id = acc2.getUserId();
        int friend3Id = acc3.getUserId();
        friendsManager.addFriend(friend1Id, friend2Id);
        boolean areFriends = friendsManager.areFriends(friend1Id, friend2Id);
        assertTrue(areFriends);

        areFriends = friendsManager.areFriends(friend2Id, friend1Id);
        assertTrue(areFriends);

        areFriends = friendsManager.areFriends(friend1Id, friend3Id);
        assertFalse(areFriends);
    }

    @Test
    public void testDeleteFriend(){
        int friend1Id = acc1.getUserId();
        int friend2Id = acc2.getUserId();
        int friend3Id = acc3.getUserId();
        friendsManager.addFriend(friend1Id, friend2Id);
        friendsManager.addFriend(friend3Id, friend1Id);
        assertTrue(friendsManager.areFriends(friend1Id, friend3Id));
        assertTrue(friendsManager.areFriends(friend3Id, friend1Id));


        friendsManager.deleteFromFriends(friend1Id, friend3Id);
        assertFalse(friendsManager.areFriends(friend1Id, friend3Id));
        assertFalse(friendsManager.areFriends(friend3Id, friend1Id));
    }

    @Test
    public void testFriendsList(){
        int friend1Id = acc1.getUserId();
        int friend2Id = acc2.getUserId();
        int friend3Id = acc3.getUserId();
        friendsManager.addFriend(friend1Id, friend2Id);
        friendsManager.addFriend(friend3Id, friend1Id);
        friendsManager.addFriend(friend2Id, friend3Id);
        ArrayList<Integer> data1 = new ArrayList<>();
        data1.add(friend2Id);
        data1.add(friend3Id);

        ArrayList<Integer> friendsOf1 = friendsManager.friendsList(friend1Id);
        assertTrue(friendsOf1.equals(data1));
    }
}
