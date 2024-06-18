package Quiz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuizServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String quizId = request.getParameter("id");

        if(quizId == null || quizId.isEmpty() || !isPosNumber(quizId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz id.");
            return;
        }

        // Parse the value of the id string
        Integer id = Integer.parseInt(quizId);

        request.getRequestDispatcher("/WEB-INF/pages/quiz.jsp")
                .forward(request, response);
    }

    private boolean isPosNumber(String s) {
        try {
            if(Integer.parseInt(s) >= 1) {
                return true;
            }
        } catch(NumberFormatException e) {
            return false;
        }
        return false;
    }
}
