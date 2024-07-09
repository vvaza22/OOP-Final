package Mail;

import Quiz.QuizManager;
import Account.*;
import Account.AccountManager;
import Account.FriendsManager;
import Question.*;
import Quiz.Quiz;
import junit.framework.TestCase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
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
    private QuizManager qm;

    private static Quiz qz1, qz2;
    @BeforeAll
    public static void setUp1() {
        ArrayList<Question> qList = new ArrayList<>();

        ArrayList<String> aList1 = new ArrayList<>();
        aList1.add("A");
        aList1.add("B");
        aList1.add("C");
        Question q1 = new QuestionResponse("Question 1", 1, aList1);

        ArrayList<String> aList2 = new ArrayList<>();
        aList2.add("M");
        aList2.add("N");
        Question q2 = new QuestionResponse("Question 2", 2, aList2);


        ArrayList<Choice> aList3 = new ArrayList<Choice>();
        aList3.add(new Choice("A", 1, true));
        aList3.add(new Choice("B", 2, false));

        Question q3 = new MultipleChoice("Question 3", 3, aList3, 1);

        qList.add(q1);
        qList.add(q2);
        qList.add(q3);

        qz1 = new Quiz(
                1,
                "Test1 Quiz",
                1,
                "Test Desc",
                "some_image.jpg",
                false,
                true,
                false,
                1,
                "2024-05-10",
                qList
        );

        ArrayList<Question> qList2 = new ArrayList<>();
        qList2.add(q1);
        qList2.add(q2);

        qz2 = new Quiz(
                2,
                "Test2 Quiz",
                2,
                "Test Desc",
                "some_image.jpg",
                false,
                true,
                false,
                1,
                "2023-10-12",
                qList2
        );
    }

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
        qm = new QuizManager(db);

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

        amgr.registerAccount(acc1);
        amgr.registerAccount(acc2);
        amgr.registerAccount(acc3);

        qm.addQuiz(qz1);
        qm.addQuiz(qz2);
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
        cmgr.sendChallenge(fromId, toId, 1);
        cmgr.sendChallenge(fromId, toId, 2);
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
