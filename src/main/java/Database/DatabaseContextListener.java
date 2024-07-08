package Database;

import Account.AccountManager;
import Achievement.AchievementManager;
import Announcements.AnnouncementManager;
import Quiz.QuizManager;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DatabaseContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // Connect to the MySQL database
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://"+DatabaseCredentials.DB_HOST+"/"+DatabaseCredentials.DB_NAME);
        dataSource.setUsername(DatabaseCredentials.DB_USER);
        dataSource.setPassword(DatabaseCredentials.DB_PASS);

        // Create Database Object
        Database db = new Database(dataSource);
        servletContextEvent.getServletContext().setAttribute("database", db);
        // Create Account Manager Object
        AccountManager acm = new AccountManager(db);
        servletContextEvent.getServletContext().setAttribute("accountManager", acm);
        // Create Quiz Manager Object
        QuizManager qm = new QuizManager(db);
        servletContextEvent.getServletContext().setAttribute("quizManager", qm);
        // Create Announcement Manager Object
        AnnouncementManager anm = new AnnouncementManager(db);
        servletContextEvent.getServletContext().setAttribute("annoManager", anm);
        // Create Achievement Manager Object
        AchievementManager achmgr = new AchievementManager(db);
        servletContextEvent.getServletContext().setAttribute("achievementManager", achmgr);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
