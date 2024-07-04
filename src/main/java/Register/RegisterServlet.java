package Register;

import Account.Account;
import Account.AccountManager;
import Account.Hash;
import Global.Constants;
import Global.SessionManager;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");

        if(
                username == null ||
                password == null ||
                firstName == null ||
                lastName == null ||
                username.isEmpty() ||
                firstName.isEmpty() ||
                lastName.isEmpty() ||
                password.isEmpty()
        ) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Username or Password field not supplied");
            return;
        }

        // Access the current HTTP session
        SessionManager sessionManager = new SessionManager(request.getSession());

        AccountManager acm = ((AccountManager)
                request.getServletContext().getAttribute("accountManager"));

        // Output JSON to the client
        response.setContentType("application/json");

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        if(!acm.accountExists(username)) {
            String defaultAboutMe = "Hello everyone, I am " + username + ", hope we get along well.";
            String defaultImageURL = "https://static.vecteezy.com/system/resources/thumbnails/020/765/399/small/default-profile-account-unknown-icon-black-silhouette-free-vector.jpg";
            Account acc = new Account(firstName, lastName, username, defaultImageURL, Hash.hashPassword(password), defaultAboutMe,"user");
            // Register account
            acm.registerAccount(acc);

            // Remember that the user has just logged in
            sessionManager.setCurrentUser(acm.getAccount(username));

            // Tell the client that the register was successful
            responseObj.put("status", "success");

        } else {
            // Tell the client that the register failed
            responseObj.put("status", "fail");
            responseObj.put("errorMsg", "User \"" + username + "\" already exists.");
        }

        // Print the response to the client
        response.getWriter().print(responseObj);

    }
}
