package Login;

import Account.Account;
import Account.AccountManager;
import Global.SessionManager;
import org.json.JSONObject;

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

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username == null || password == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Username or Password field not supplied");
            return;
        }

        // Access the current HTTP session
        SessionManager sessionManager = new SessionManager(request.getSession());
        if(sessionManager.isUserLoggedIn()) {
            return;
        }

        AccountManager acm = ((AccountManager)
                request.getServletContext().getAttribute("accountManager"));

        // Output JSON to the client
        response.setContentType("application/json");

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        if(acm.accountExists(username) && acm.passwordMatches(username, password)) {

            // Get the user account
            Account userAccount = acm.getAccount(username);

            // Remember that the user has just logged in
            sessionManager.setCurrentUser(userAccount);

            // Tell the client that the login was successful
            responseObj.put("status", "success");

        } else {
            // Tell the client that the login failed
            responseObj.put("status", "fail");
        }

        // Print the response to the client
        response.getWriter().print(responseObj);

    }

}
