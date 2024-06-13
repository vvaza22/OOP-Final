package Profile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String pathInfo = request.getPathInfo();

        if(pathInfo.matches("^/[A-Za-z0-9]+$")) {
            String userName = pathInfo.substring(1);
            // Pass the username to the JSP files
            request.setAttribute("username", userName);
            request.getRequestDispatcher("/WEB-INF/pages/profile.jsp")
                    .forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Username.");
        }

    }
}
