package QuizAttempt;

import Global.SessionManager;
import Quiz.Attempt;
import Quiz.QuizManager;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuizAttemptServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Initialize the session manager
        SessionManager sessionManager =
                new SessionManager(request.getSession());

        // Get the quiz manager
        QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");

        if(sessionManager.isTakingQuiz()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You are currently taking a quiz.");
            return;
        }

        String attemptIdStr = request.getParameter("attempt_id");
        if(attemptIdStr == null || attemptIdStr.isEmpty() || !isPosNumber(attemptIdStr)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid attempt id.");
            return;
        }

        int attemptId = Integer.parseInt(attemptIdStr);

        // Get the attempt object
        Attempt attempt = qm.getAttempt(attemptId);

        request.setAttribute("attempt", attempt);
        request.getRequestDispatcher("/WEB-INF/pages/attempt.jsp")
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
