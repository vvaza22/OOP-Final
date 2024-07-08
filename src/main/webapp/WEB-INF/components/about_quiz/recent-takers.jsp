<%@ page import="Quiz.Quiz" %>
<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.ScoresStruct" %>
<%@ page import="java.util.ArrayList" %>

<%
    Quiz currentQuiz = (Quiz) request.getAttribute("quizObj");
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    ArrayList<ScoresStruct> recentTakers = qm.getRecentQuizTakers(currentQuiz.getId());
%>

<section class="group-section g-recent-takers">
    <div class="g-content">
        <div class="g-title-cont">
            <div class="g-title-bg">
                <h4 class="g-title">Recent Scorers</h4>
            </div>
        </div>
        <div class="g-list pt-4">

            <% if(recentTakers.isEmpty()) {%>
            <p> Be first to take the quiz!</p>
            <% } else { %>
            <ol>
                <%for(int i=0; i<recentTakers.size(); i++) { %>
                <li><a href=<%="/profile?username="+recentTakers.get(i).getUserName()%>><%=recentTakers.get(i).getUserName()%></a> (<%=recentTakers.get(i).getScore()%> Points)</li>
                <% } %>
            </ol>
            <% } %>
        </div>
    </div>
</section>