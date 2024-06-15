package Login;

import Account.AccountManager;
import Global.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username == null || password == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Username or Password field not supplied");
            return;
        }

        // Access the current HTTP session
        SessionManager sessionManager = new SessionManager(request.getSession());

        AccountManager acm = ((AccountManager)
                request.getServletContext().getAttribute("accountManager"));

        if(acm.accountExists(username) && acm.passwordMatches(username, password)) {

            // Remember that the user has just logged in
            sessionManager.setCurrentUser(acm.getAccount(username));

            response.getWriter().print("ok");
        } else {
            response.getWriter().print("no");
        }

    }


}
