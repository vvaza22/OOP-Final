package Profile;

import Account.Account;
import Account.AccountManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String userName = request.getParameter("username");

        if(userName == null || userName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username must be non-empty.");
            return;
        }

        if(Account.isValidUsername(userName)) {
            // Get the Account Manager
            AccountManager acm = ((AccountManager)
                    request.getServletContext().getAttribute("accountManager"));

            // Get the user account
            Account userAccount = acm.getAccount(userName);

            // Pass the user account to the JSP files
            // If the user does not exist userAccount = null
            request.setAttribute("userAccount", userAccount);

            // Also pass the requested username
            request.setAttribute("reqUsername", userName);

            // Display the page
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp")
                    .forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Username.");
        }

    }
}
