package Quiz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import Global.SessionManager;
import Question.*;
import org.json.JSONObject;

public class QuizServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Initialize the session manager
        SessionManager sessionManager =
                new SessionManager(request.getSession());

        if(!sessionManager.isUserLoggedIn()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You need to be logged in to take a quiz.");
            return;
        }

        if(!sessionManager.isTakingQuiz()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You are not currently taking a quiz.");
            return;
        }

        // Get the current quiz we are taking
        Quiz currentQuiz = sessionManager.getCurrentQuiz();

        // Get which question we are on
        String questionIndex = request.getParameter("q");

        // Check for review tab
        if(questionIndex != null && questionIndex.equals("review")) {
            // Pass the arguments to the client
            request.setAttribute("currentQuiz", currentQuiz);
            request.setAttribute("curQuestionIndex", -1);
            request.setAttribute("reviewFlag", true);

            request.getRequestDispatcher("/WEB-INF/pages/quiz.jsp")
                    .forward(request, response);
            return;
        }

        if(questionIndex == null || questionIndex.isEmpty() || !isPosNumber(questionIndex)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid question index.");
            return;
        }

        // Parse the value of the question index
        Integer qIndex = Integer.parseInt(questionIndex);

        // Check if the index is inbounds: questionIndex in [1, n]
        if(qIndex > currentQuiz.getNumberOfQuestions()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Question index out of bounds.");
            return;
        }

        // Pass the arguments to the client
        request.setAttribute("currentQuiz", currentQuiz);
        request.setAttribute("curQuestionIndex", qIndex);

        request.getRequestDispatcher("/WEB-INF/pages/quiz.jsp")
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Output JSON to the client
        response.setContentType("application/json");

        // Prepare the response object
        JSONObject responseObj = new JSONObject();

        // Initialize the session manager
        SessionManager sessionManager =
                new SessionManager(request.getSession());

        if(!sessionManager.isUserLoggedIn()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You need to be logged in!");
            return;
        }

        int curUserId = sessionManager.getCurrentUserAccount().getUserId();

        // Get the quiz manager
        QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");

        // Get the action we are performing
        String action = request.getParameter("action");

        if(action.equals("take_quiz")) {

            if(sessionManager.isTakingQuiz()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You need to finish the quiz you are taking to take a new one!");
                return;
            }

            // Which quiz are we taking?
            String quizId = request.getParameter("quiz_id");

            if(quizId == null || quizId.isEmpty() || !isPosNumber(quizId)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz id.");
                return;
            }

            // Find the quiz
            Quiz curQuiz = qm.getQuiz(Integer.parseInt(quizId));

            if(curQuiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found.");
                return;
            }

            // Take the quiz
            sessionManager.setCurrentQuiz(curQuiz);

            // Print success to the client
            responseObj.put("status", "success");
            response.getWriter().print(responseObj);

        } else if(action.equals("save_answer")) {
            if(!sessionManager.isTakingQuiz()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You need to be taking quiz!");
                return;
            }

            // Get the current quiz we are taking
            Quiz currentQuiz = sessionManager.getCurrentQuiz();

            String questionIdStr = request.getParameter("question_id");
            if(questionIdStr == null || questionIdStr.isEmpty() || !isPosNumber(questionIdStr)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid question id.");
                return;
            }

            // Check if the question is in the quiz
            Integer questionId = Integer.parseInt(questionIdStr);
            if(!currentQuiz.hasQuestion(questionId)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quiz does not contain the question.");
                return;
            }

            // Get the question object
            Question currentQuestion = currentQuiz.getQuestionById(questionId);

            // Do not allow users to answer to the same question multiple times if immediate is on
            if(currentQuiz.isImmediateCorrectionOn() && currentQuestion.hasAnswer()) {
                responseObj.put("status", "fail");
                responseObj.put("errorMsg", "You have already answered this question!");
                response.getWriter().print(responseObj);
                return;
            }

            // Get data
            String data = request.getParameter("data");
            if(data == null || data.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid question id.");
                return;
            }

            JSONObject dataObj;
            try {
                dataObj = new JSONObject(data);
            } catch(Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON format.");
                return;
            }

            String answer;
            switch(currentQuestion.getType()) {
                case QuestionType.QUESTION_RESPONSE:
                    answer = dataObj.has("answer") ? dataObj.getString("answer") : "";
                    QuestionResponse questionResponse = (QuestionResponse)currentQuestion;
                    if(!answer.isEmpty()) {
                        questionResponse.setAnswer(answer);
                    }
                    break;
                case QuestionType.FILL_BLANK:
                    answer = dataObj.has("answer") ? dataObj.getString("answer") : "";
                    FillBlank fillBlank = (FillBlank)currentQuestion;
                    if(!answer.isEmpty()) {
                        fillBlank.setAnswer(answer);
                    }
                    break;
                case QuestionType.MULTIPLE_CHOICE:
                    if(dataObj.has("answer_index")) {
                        int answerIndex = dataObj.getInt("answer_index");
                        MultipleChoice multipleChoice = (MultipleChoice) currentQuestion;
                        multipleChoice.setAnswer(answerIndex);
                    }
                    break;
                case QuestionType.PICTURE_RESPONSE:
                    answer = dataObj.has("answer") ? dataObj.getString("answer") : "";
                    PictureResponse pictureResponse = (PictureResponse)currentQuestion;
                    if(!answer.isEmpty()) {
                        pictureResponse.setAnswer(answer);
                    }
                    break;
            }

            // Print success to the client
            responseObj.put("status", "success");

            // Instantly send the answer if immediate correction is on
            if(currentQuiz.isImmediateCorrectionOn()) {
                responseObj.put("points", currentQuestion.countPoints());
            }

            response.getWriter().print(responseObj);

        } else if(action.equals("finish_attempt")) {

            long attemptId = qm.saveAttempt(curUserId, sessionManager.getCurrentQuiz());

            sessionManager.endCurrentQuiz();

            if(attemptId != -1) {
                // Print success to the client
                responseObj.put("status", "success");
                responseObj.put("attempt_id", attemptId);
            } else {
                // Print success to the client
                responseObj.put("status", "error");
            }

            response.getWriter().print(responseObj);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action.");
            return;
        }

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
