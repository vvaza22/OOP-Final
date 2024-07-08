package Practice;

import Achievement.AchievementManager;
import Global.SessionManager;
import Question.*;
import Quiz.Quiz;
import Quiz.QuizManager;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PracticeServlet extends HttpServlet {

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

        if(!sessionManager.isTakingPracticeQuiz()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You are not currently taking a practice quiz.");
            return;
        }

        // Get the current quiz we are taking
        PracticeQuiz currentQuiz = sessionManager.getCurrentPracticeQuiz();

        // Pass the arguments to the client
        request.setAttribute("currentPracticeQuiz", currentQuiz);

        request.getRequestDispatcher("/WEB-INF/pages/practice.jsp")
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

        if(action.equals("start_practice")) {

            if(sessionManager.isTakingQuiz()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You need to finish the quiz you are taking to start practice!");
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

            // Create Practice Quiz
            PracticeQuiz curPracticeQuiz = new PracticeQuiz(curQuiz);

            // Remember that the user is taking a practice quiz
            sessionManager.setCurrentPracticeQuiz(curPracticeQuiz);

            curPracticeQuiz.getNextQuestion();

            // Print success to the client
            responseObj.put("status", "success");
            response.getWriter().print(responseObj);

        } else if(action.equals("save_answer")) {
            if(!sessionManager.isTakingPracticeQuiz()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You need to be taking quiz!");
                return;
            }

            // Get the current quiz we are taking
            PracticeQuiz currentPracticeQuiz = sessionManager.getCurrentPracticeQuiz();
            Quiz currentQuiz = currentPracticeQuiz.getQuiz();

            // Get the practice question
            PracticeQuestion practiceQuestion = currentPracticeQuiz.getCurrentPracticeQuestion();

            // Get the question object
            Question currentQuestion = practiceQuestion.getQuestion();

            // Do not allow users to answer to the same question multiple times if immediate is on
            if(currentQuestion.hasAnswer()) {
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

            practiceQuestion.updateStatistics();

            // Print success to the client
            responseObj.put("status", "success");
            response.getWriter().print(responseObj);
        } else if(action.equals("next_question")) {
            if(!sessionManager.isTakingPracticeQuiz()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You need to be taking quiz!");
                return;
            }

            // Get the current quiz we are taking
            PracticeQuiz currentPracticeQuiz = sessionManager.getCurrentPracticeQuiz();
            Quiz currentQuiz = currentPracticeQuiz.getQuiz();

            if(currentPracticeQuiz.hasNextQuestion()) {
                currentPracticeQuiz.getNextQuestion();
                responseObj.put("practice_status", "continue");
            } else {
                AchievementManager achmgr = (AchievementManager) request.getServletContext().getAttribute("achievementManager");
                if(!achmgr.hasAchievement(curUserId, 6)) achmgr.addAchievement(curUserId, 6);

                responseObj.put("practice_status", "ended");
                responseObj.put("return_to", currentQuiz.getId());
                sessionManager.endCurrentPracticeQuiz();
            }

            // Print success to the client
            responseObj.put("status", "success");
            response.getWriter().print(responseObj);
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
