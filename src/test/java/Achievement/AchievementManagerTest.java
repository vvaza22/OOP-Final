package Achievement;

import Account.Account;
import Account.AccountManager;
import Database.Database;
import Database.DatabaseCredentials;
import Question.*;
import Quiz.QuizManager;
import Quiz.Quiz;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Account.*;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class AchievementManagerTest {

    private AchievementManager achievementManager;
    private Database db;
    private Achievement achi1, achi2, achi3;
    private QuizManager quizManager;
    private AccountManager accountManager;

    private Account acc1, acc2;
    private static Quiz qz1, qz2, qz3;
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

        qList.add(q1);
        qList.add(q2);

        qz1 = new Quiz(
                1,
                "Test Quiz 1",
                1,
                "Test Desc",
                "some_image.jpg",
                false,
                true,
                false,
                1,
                "2024-07-03",
                qList
        );

        qz2 = new Quiz(
                2,
                "Test Quiz 2",
                1,
                "Test Desc",
                "some_image.jpg",
                false,
                true,
                false,
                1,
                "2024-07-03",
                qList
        );

        qz3 = new Quiz(
                3,
                "Test Quiz 3",
                1,
                "Test Desc",
                "some_image.jpg",
                false,
                true,
                false,
                1,
                "2024-07-03",
                qList
        );
    }

    @BeforeEach
    public void setUp() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://" + DatabaseCredentials.DB_HOST + "/" + DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        db = new Database(dataSource);
        achievementManager = new AchievementManager(db);
        quizManager = new QuizManager(db);
        accountManager = new AccountManager(db);

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

        achi1 = new Achievement(1, Achievement.ROOKIE_AUTHOR);
        achi2 = new Achievement(2, Achievement.PRACTITIONER);
        achi3 = new Achievement(2, Achievement.QUIZ_SLAYER);


        acc1 = new Account(1,
                "Anakin",
                "Skywalker",
                "jedi_knight",
                "/images/default.jpg",
                Hash.hashPassword("padme"),
                "I hate sand",
                "user"
        );

        acc2 = new Account(2,
                "obi-wan",
                "kenobi",
                "jedi master",
                "/images/default.jpg",
                Hash.hashPassword("padawan"),
                "Hello there",
                "admin"
        );

        AccountManager acm = new AccountManager(db);
        acm.registerAccount(acc1);
        acm.registerAccount(acc2);

        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);
        quizManager.addQuiz(qz3);
    }

    @Test
    public void testAddAchievement(){
        int userId1 = achi1.getUserId();
        int userType1 = achi1.getType();
        achievementManager.addAchievement(userId1, userType1);
        boolean hasAchievement = achievementManager.hasAchievement(userId1, userType1);
        assertTrue(hasAchievement);

        int userId2 = achi2.getUserId();
        int userType2 = achi2.getType();
        assertFalse(achievementManager.hasAchievement(userId2, userType2));
    }

    @Test
    public void testRemoveAchievement(){
        int userId3 = achi3.getUserId();
        int userType3 = achi3.getType();
        achievementManager.addAchievement(userId3, userType3);
        boolean hasAchievment = achievementManager.hasAchievement(userId3, userType3);
        assertTrue(hasAchievment);

        achievementManager.removeAchievement(userId3, userType3);
        hasAchievment = achievementManager.hasAchievement(userId3, userType3);
        assertFalse(hasAchievment);
    }

    @Test
    public void testGetAchievements(){
        int userId1 = achi1.getUserId();
        int userType1 = achi1.getType();
        achievementManager.addAchievement(userId1, userType1);
        int userType2 = Achievement.PROLIFIC_AUTHOR;
        achievementManager.addAchievement(userId1, userType2);
        int userType3 = Achievement.MASTER_AUTHOR;
        achievementManager.addAchievement(userId1, userType3);

        ArrayList<Achievement> data = achievementManager.getAchievements(achi1.getUserId());
        assertTrue(data.size() == 3);

        int type1 = data.get(0).getType();
        assertTrue(type1 == userType1);

        int type2 = data.get(1).getType();
        assertTrue(type2 == userType2);

        int type3 = data.get(2).getType();
        assertTrue(type3 == userType3);
    }

    @Test
    public void testCheckQuizSlayer(){
        boolean check = achievementManager.checkQuizSlayer(achi1.getUserId(), quizManager);
        assertFalse(check);
    }

    @Test
    public void testCheckLordOfTheQuizzes(){
        int userId = achi2.getUserId();
        Account account = accountManager.getAccountById(userId);
        boolean check = achievementManager.checkLordOfTheQuizzes(userId, account, quizManager);
        assertFalse(check);
        quizManager.saveAttempt(account.getUserId(),qz1);
        check = achievementManager.checkLordOfTheQuizzes(1, account, quizManager);
        assertTrue(check);
    }


}
