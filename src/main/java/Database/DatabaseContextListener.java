package Database;

import Account.AccountManager;
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
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
