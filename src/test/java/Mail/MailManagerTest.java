package Mail;

import Account.*;
import Account.AccountManager;
import Account.FriendsManager;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import Database.Database;
import Database.DatabaseCredentials;
import Mail.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//These tests also test NoteMail and FriendRequestMail Classes
//(makes use of ChallengeMail and ChallengeManager because of some methods)

public class MailManagerTest extends TestCase {
    private MailManager mailMgr;
    private FriendRequestManager frmgr;
    private ChallengeManager cmgr;
    private AccountManager amgr;
    private Database db;
    private Account acc1, acc2, acc3;

    @BeforeEach
    protected void setUp() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://" + DatabaseCredentials.DB_HOST + "/" + DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        db = new Database(dataSource);
        amgr = new AccountManager(db);
        mailMgr = new MailManager(db, amgr);
        frmgr = new FriendRequestManager(db);
        cmgr = new ChallengeManager(db);

        try {
            Connection con = db.openConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=0"); // Disabling Foreign Key Checks
            stmt.executeUpdate("truncate table users");
            stmt.executeUpdate("truncate table notes");
            stmt.executeUpdate("truncate table challenges");
            stmt.executeUpdate("truncate table frreqs");
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

        amgr.registerAccount(acc1);
        amgr.registerAccount(acc2);
        amgr.registerAccount(acc3);
    }


    @Test
    public void testNoteMail() throws SQLException {
        int fromId = acc1.getUserId();
        int toId = acc2.getUserId();
        mailMgr.addNote(fromId, toId, "hello there");
        mailMgr.addNote(fromId, toId, "hello again");
        ArrayList<NoteMail> notes = mailMgr.getNotes(toId);
        assertEquals(2, notes.size());
        assertEquals(mailMgr.countNotes(toId), notes.size());
        assertEquals("hello again", notes.get(1).getNote());
        assertEquals(3, notes.get(0).getType());
        assertEquals(amgr.getAccountById(fromId).getUserName() + " sent you a message.", notes.get(0).getMessage());
        mailMgr.setNotesAsSeen(toId);
    }

    @Test
    public void testFriendRequestMail() throws SQLException {
        int fromId = acc2.getUserId();
        int toId = acc1.getUserId();
        frmgr.sendRequest(fromId, toId);
        frmgr.sendRequest(acc3.getUserId(), toId);

        ArrayList<FriendRequestMail> reqs = mailMgr.getFriendRequests(toId);
        assertEquals(2, reqs.size());
        assertEquals(mailMgr.countFriendRequests(toId), reqs.size());
        assertEquals(1, reqs.get(0).getType());
        assertEquals(1, reqs.get(0).getId());
        assertEquals(acc3.getUserName() + " sent you a friend request.", reqs.get(1).getMessage());
        assertEquals("PENDING", reqs.get(1).getStatus());
        assertEquals(fromId, reqs.get(0).getFrom().getUserId());
        assertEquals(toId, reqs.get(0).getTo().getUserId());
    }

    @Test
    public void testGetChallenges() throws SQLException {
        int fromId = acc2.getUserId();
        int toId = acc1.getUserId();
        cmgr.sendChallenge(fromId, toId, 2);
        cmgr.sendChallenge(fromId, toId, 3);
        ArrayList<ChallengeMail> challenges = mailMgr.getChallenges(toId);
        assertEquals(2, challenges.size());
        assertEquals(mailMgr.countChallenges(toId), challenges.size());
    }

    @Test
    public void testWithRemovedUsers() throws SQLException {
        int fromId = acc2.getUserId();
        int toId = acc1.getUserId();
        mailMgr.addNote(fromId, toId, "sorrow");
        amgr.removeAccount(acc2.getUserName());
        ArrayList<NoteMail> notes = mailMgr.getNotes(toId);

        assertEquals("<b>[ deleted user ]</b> sent you a message",
                notes.get(0).getMessage());
    }


}
