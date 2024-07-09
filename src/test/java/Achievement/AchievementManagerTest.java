package Achievement;

import Account.Account;
import Account.AccountManager;
import Database.Database;
import Database.DatabaseCredentials;
import Quiz.QuizManager;
import Quiz.Quiz;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class AchievementManagerTest {

    private AchievementManager achievementManager;
    private Database db;
    private Achievement achi1, achi2, achi3;
    private QuizManager quizManager;
    private AccountManager accountManager;

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
            stmt.executeUpdate("truncate table achievements");
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=1"); // Enabling Foreign Key Checks
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        achi1 = new Achievement(1, Achievement.ROOKIE_AUTHOR);
        achi2 = new Achievement(2, Achievement.PRACTITIONER);
        achi3 = new Achievement(3, Achievement.QUIZ_SLAYER);


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
    }


}
