package Quiz;

import Account.Account;
import Account.Hash;
import Account.AccountManager;
import Database.Database;
import Database.DatabaseCredentials;
import Question.Question;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import Question.QuestionResponse;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class QuizManagerTest {

    @Test
    public void test1() {
        // Connect to the MySQL database
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://"+ DatabaseCredentials.DB_HOST+"/"+DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        Database db = new Database(dataSource);
        QuizManager qm = new QuizManager(db);
        AccountManager acm = new AccountManager(db);

        Account ac = acm.getAccount("realtia");   // id 2
        ArrayList<String> answers = new ArrayList<String>();
        answers.add(0, "answer1");
        answers.add(1, "answer2");
        QuestionResponse quest = new QuestionResponse("question1", 0, answers);
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(quest);

        Quiz quiz = new Quiz(0, "quiz1", ac.getUserId(), "first quiz about nothing", false, false, false,
                1, "2024-04-07", questions);

        qm.addQuiz(quiz);
    }
// quiz id 3,     question id 5,  answers id 7,8

    @Test
    public void test2() {
        // Connect to the MySQL database
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://"+ DatabaseCredentials.DB_HOST+"/"+DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        Database db = new Database(dataSource);
        QuizManager qm = new QuizManager(db);
        AccountManager acm = new AccountManager(db);

        Account ac = acm.getAccount("realtia");


    }


}
