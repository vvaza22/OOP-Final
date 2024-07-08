<%@ page import="Quiz.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Account.AccountManager" %>
<%@ page import="Question.UserAnswer" %>
<%
    Attempt curAttempt = (Attempt) request.getAttribute("attempt");
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    AccountManager am = (AccountManager) request.getServletContext().getAttribute("accountManager");
%>

<link rel="stylesheet" href="/css/attempt.css" />

<main>
    <div class="container attempt-page">
        <% if(curAttempt == null) { %>
            <h3>Attempt does not exist</h3>
        <% } else { %>
        <%
            Quiz attemptedQuiz = qm.getQuiz(curAttempt.getQuizId());
            ArrayList<Attempt> prevAttempts = qm.getAttemptList(curAttempt.getUserId(), curAttempt.getQuizId());
            // Account userAccount = am.getAccount()
        %>

        <h3><%= attemptedQuiz.getName() %></h3>
        <p>Score: <b><%= curAttempt.getUserScore() %></b> out of <b><%= curAttempt.getMaxScore() %></b>.</p>

        <h4>Performance Report: </h4>
        <table class="table">
            <tr>
                <th>#</th>
                <th>Question</th>
                <th>Answer</th>
                <th>Correct Answer</th>
                <th>Points</th>
            </tr>
            <%
                ArrayList<UserAnswer> answerList = curAttempt.getUserAnswers();
                for(int i=1; i<=answerList.size(); i++) {
                    UserAnswer userAnswer = answerList.get(i-1);
            %>
                <tr>
                    <td><%= i %></td>
                    <td><%= userAnswer.getQuestion() %></td>
                    <td><%= userAnswer.getUserAnswer() %></td>
                    <td><%= userAnswer.getCorrectAnswer() %></td>
                    <td><%= userAnswer.getPoints() %></td>
                </tr>
            <% } %>
        </table>

        <h4>Previous Attempts: </h4>
        <table class="table">
            <tr>
                <th>Nth</th>
                <th>Score</th>
                <th>Time</th>
            </tr>
            <% for(int i=0; i<prevAttempts.size(); i++) { %>
            <tr>
                <td><%= prevAttempts.size() - i %></td>
                <td><%= prevAttempts.get(i).getUserScore() %>/<%= prevAttempts.get(i).getMaxScore() %></td>
                <td><%= prevAttempts.get(i).getTime() %></td>
            </tr>
            <% } %>
        </table>

        <% } %>
    </div>
</main>