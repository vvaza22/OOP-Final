package AboutQuiz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import Quiz.*;

public class AboutQuizServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String quizIdStr = request.getParameter("id");

        if(quizIdStr == null || quizIdStr.isEmpty() || !isPosNumber(quizIdStr)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz id.");
            return;
        }

        // Get the QuizManager
        QuizManager qm = (QuizManager)
                request.getServletContext().getAttribute("quizManager");

        // Get the quiz object
        int quizId = Integer.parseInt(quizIdStr);
        Quiz currentQuiz = qm.getQuiz(quizId);

        if(currentQuiz == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found");
            return;
        }

        // Pass the quiz object to the client-side
        request.setAttribute("quizObj", currentQuiz);
        request.getRequestDispatcher("/WEB-INF/pages/about_quiz.jsp")
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