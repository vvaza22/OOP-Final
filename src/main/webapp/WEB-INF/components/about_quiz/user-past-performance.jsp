<%@ page import="Quiz.Attempt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="Global.SessionManager" %>
<%@ page import="Account.*" %>


<%
    SessionManager smgr = new SessionManager(request.getSession());
    Account curr = smgr.getCurrentUserAccount();
    Quiz currentQuiz = (Quiz) request.getAttribute("quizObj");
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    ArrayList<Attempt> pastPerformance = qm.getAttemptList(curr.getUserId(), currentQuiz.getId());
%>

<% if(pastPerformance.isEmpty()) { %>
    <p>You have not taken this quiz yet.</p>
<% }else { %>
    <table class="g-past-performance">
        <p>Your Past Performance</p>
            <tr>
                <th>Nth</th>
                <th>Score</th>
                <th>Time</th>
            </tr>
            <% for(int i=0; i<pastPerformance.size(); i++) { %>
            <tr>
                <td><%= pastPerformance.size() - i %></td>
                <td><%= pastPerformance.get(i).getUserScore() %>/<%= pastPerformance.get(i).getMaxScore() %></td>
                <td><%= pastPerformance.get(i).getTime() %></td>
            </tr>
            <% } %>
    </table>
<% } %>