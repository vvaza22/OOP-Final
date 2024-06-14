package Account;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AccountContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AccountManager acm = new AccountManager();
        servletContextEvent.getServletContext().setAttribute("accountManager", acm);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
