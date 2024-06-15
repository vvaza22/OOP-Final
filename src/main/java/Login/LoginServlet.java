package Login;

import Account.AccountManager;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AccountManager acm = ((AccountManager) request.getServletContext().getAttribute("accountManager"));

        request.getSession();

        response.setContentType("text/html");
        if(acm.accountExists(username)) {
            if(acm.passwordMatches(username, password)) {
                response.getWriter().print("ok");
                return;
            }
        }
        response.getWriter().print("no");

    }


}
