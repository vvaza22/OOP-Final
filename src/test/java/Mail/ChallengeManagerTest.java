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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//These tests also test ChallengeMail (also makes use of MailManager)

public class ChallengeManagerTest extends TestCase {
    private ChallengeManager cmgr;
    private AccountManager amgr;
    private MailManager mailMgr;
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
        cmgr = new ChallengeManager(db);
        mailMgr = new MailManager(db, amgr);

        try {
            Connection con = db.openConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=0"); // Disabling Foreign Key Checks
            stmt.executeUpdate("truncate table users");
            stmt.executeUpdate("truncate table challenges");
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
    public void testSendChallenge() throws SQLException {
        int fromId = acc2.getUserId();
        int toId = acc1.getUserId();
        cmgr.sendChallenge(fromId, toId, 2);
        cmgr.sendChallenge(fromId, toId, 3);
        ArrayList<ChallengeMail> challenges = mailMgr.getChallenges(toId);
        assertEquals(2, challenges.get(0).getQuizId());
        assertEquals(1, challenges.get(0).getId());
        assertEquals(2, challenges.get(1).getType());

        assertEquals(acc2.getUserName() + " challenged you with \"" + challenges.get(0).quiz_name + "\"",
                challenges.get(0).getMessage());
    }

    @Test
    public void testChangeStatus() throws SQLException {
        int fromId = acc2.getUserId();
        int toId = acc1.getUserId();
        cmgr.sendChallenge(fromId, toId, 2);
        cmgr.sendChallenge(fromId, toId, 1);
        cmgr.sendChallenge(fromId, toId, 3);
        ArrayList<ChallengeMail> challenges = mailMgr.getChallenges(toId);
        assertEquals("PENDING", challenges.get(0).getStatus());
        cmgr.changeStatus(challenges.get(0).getId(), "CHL_ACCEPTED");
        cmgr.changeStatus(challenges.get(1).getId(), "CHL_REJECTED");
        challenges = mailMgr.getChallenges(toId);
        assertEquals("CHL_ACCEPTED", challenges.get(0).getStatus());
        assertTrue(cmgr.challengeExists(fromId, toId, challenges.get(0).quiz_id));
        assertTrue(cmgr.challengeExists(fromId, toId, challenges.get(2).quiz_id));
        assertFalse(cmgr.challengeExists(fromId, toId, challenges.get(1).quiz_id));
    }

    @Test
    public void testWithRemovedUsers() throws SQLException {
        int fromId = acc2.getUserId();
        int toId = acc1.getUserId();
        cmgr.sendChallenge(fromId, toId, 1);
        amgr.removeAccount(acc2.getUserName());
        ArrayList<ChallengeMail> challenges = mailMgr.getChallenges(toId);

        assertEquals("<b>[ deleted user ]</b> " + " challenged you with \""
                + challenges.get(0).quiz_name + "\"", challenges.get(0).getMessage());
    }
}
