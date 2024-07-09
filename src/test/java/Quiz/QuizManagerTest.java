package Quiz;

import Account.Account;
import Account.AccountManager;
import Achievement.Achievement;
import Achievement.AchievementManager;
import Database.Database;
import Database.DatabaseCredentials;
import Question.Question;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Question.QuestionResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class QuizManagerTest {
    private Database db;
    private Connection con;
    private QuizManager quizManager;
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

        qList.add(q1);
        qList.add(q2);

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

        qList.remove(1);
        qz2 = new Quiz(
                2,
                "Test2 Quiz",
                1,
                "Test Desc",
                "some_image.jpg",
                false,
                true,
                false,
                1,
                "2023-10-12",
                qList
        );
    }

    @BeforeEach
    public void setUp2() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://" + DatabaseCredentials.DB_HOST + "/" + DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        db = new Database(dataSource);
        quizManager = new QuizManager(db);
        try {
            con = db.openConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=0"); // Disabling Foreign Key Checks
            stmt.executeUpdate("truncate table quiz");
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=1"); // Enabling Foreign Key Checks
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            con = db.openConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=0"); // Disabling Foreign Key Checks
            stmt.executeUpdate("truncate table attempts");
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=1"); // Enabling Foreign Key Checks
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            con = db.openConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=0"); // Disabling Foreign Key Checks
            stmt.executeUpdate("truncate table user_answers");
            stmt.executeUpdate("set FOREIGN_KEY_CHECKS=1"); // Enabling Foreign Key Checks
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void test1() {
        int id = quizManager.addQuiz(qz1);
        assertEquals(1, id);
        int id2 = quizManager.addQuiz(qz1);
        assertEquals(2, id2);

        ArrayList<Quiz> list = quizManager.getRecentQuizzes();
        assertEquals(2, list.size());
    }

    @Test
    public void test2() {
        quizManager.addQuiz(qz1);
        assertEquals(1, quizManager.getRecentlyCreatedQuizzes(1).size());

        quizManager.addQuiz(qz2);
        int cnt = quizManager.getQuizCount(1);
        assertEquals(2, cnt);
        int cnt2 = quizManager.getQuizCount(2);
        assertEquals(0, cnt2);
    }

    @Test
    public void test3() {
        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        Quiz qz = quizManager.getQuiz(1);
        assertEquals(qz.getId(), qz1.getId());
        Quiz qzz = quizManager.getQuiz(2);
        assertEquals(qzz.getId(), qz2.getId());
    }

    @Test
    public void test4() {
        assertNull(quizManager.getRandomQuiz());

        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        Quiz qz = quizManager.getRandomQuiz();
        assertTrue(qz.getId()== 1 || qz.getId()==2 );
    }

    @Test
    public void test5() {
        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        quizManager.deleteQuiz(1);
        ArrayList<Quiz> list = quizManager.getRecentQuizzes();
        assertEquals(1, list.size());

        quizManager.deleteQuiz(1);
        ArrayList<Quiz> list2 = quizManager.getRecentQuizzes();
        assertEquals(1, list2.size());

        quizManager.deleteQuiz(2);
        ArrayList<Quiz> list3 = quizManager.getRecentQuizzes();
        assertEquals(0, list3.size());
    }

    @Test
    public void test6() {
        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        quizManager.saveAttempt(1, qz1);
        quizManager.saveAttempt(2, qz2);

        assertEquals(1, quizManager.getAttempt(1).getQuizId());
        assertEquals(1, quizManager.getAttempt(1).getUserId());
        assertEquals(2, quizManager.getAttempt(2).getUserId());
        assertEquals(2, quizManager.getAttempt(2).getQuizId());

        assertEquals(1, quizManager.getAttemptList(1,1).size());
        assertEquals(0, quizManager.getAttemptList(1,2).size());

        assertEquals(0, quizManager.getAttemptList(2,1).size());
        assertEquals(1, quizManager.getAttemptList(2,2).size());

    }

    @Test
    public void test7() {
        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        quizManager.saveAttempt(1, qz1);
        quizManager.saveAttempt(2, qz2);

        assertEquals(1, quizManager.getDoneQuizzes(1));
        assertEquals(1, quizManager.getDoneQuizzes(2));
        assertEquals(0, quizManager.getDoneQuizzes(3));
    }

    @Test
    public void test8() {
        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        quizManager.saveAttempt(1, qz1);
        quizManager.saveAttempt(2, qz2);

        assertEquals(1, quizManager.getUserAnswers(1, con).size());
        assertEquals(0, quizManager.getUserAnswers(3, con).size());
    }


    @Test
    public void test9() {
        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        quizManager.saveAttempt(1, qz1);
        quizManager.saveAttempt(2, qz2);

        assertEquals(0, quizManager.getFriendCreatedQuizzes(1).size());
        assertEquals(0, quizManager.getFriendCreatedQuizzes(2).size());
        assertEquals(0, quizManager.getFriendTakenQuizzes(1).size());
        assertEquals(0, quizManager.getFriendTakenQuizzes(2).size());

    }

    @Test
    public void test11() {
        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        quizManager.saveAttempt(1, qz1);
        quizManager.saveAttempt(1, qz1);
        quizManager.saveAttempt(2, qz2);

        assertEquals(1, quizManager.getPopularQuizzes().get(0).getId());
        quizManager.saveAttempt(2, qz2);
        quizManager.saveAttempt(2, qz2);
        quizManager.saveAttempt(2, qz2);

        assertEquals(2, quizManager.getPopularQuizzes().get(0).getId());
    }


    @Test
    public void test12() {
        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        quizManager.saveAttempt(1, qz1);
        quizManager.saveAttempt(2, qz1);

        assertEquals(0, quizManager.getAverageScore(1));
        assertEquals(0, quizManager.getAverageScore(2));

        assertEquals(2, quizManager.getTodaysTopScorers(1).size());
        quizManager.saveAttempt(2, qz1);
        assertEquals(2, quizManager.getTodaysTopScorers(1).size());
    }


    @Test
    public void test13() {
        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        quizManager.saveAttempt(1, qz1);
        quizManager.saveAttempt(2, qz1);

        assertEquals(2, quizManager.getRecentQuizTakers(1).size());
        assertEquals(0, quizManager.getRecentQuizTakers(2).size());
    }

    @Test
    public void test14() {
        quizManager.addQuiz(qz1);
        quizManager.addQuiz(qz2);

        quizManager.saveAttempt(1, qz1);
        quizManager.saveAttempt(2, qz1);

        assertEquals(1, quizManager.getRecentlyTakenQuizzes(1).size());
        assertEquals(2, quizManager.getTopScorers(1).size());
        assertEquals(0, quizManager.getTopScorers(2).size());
    }


}
