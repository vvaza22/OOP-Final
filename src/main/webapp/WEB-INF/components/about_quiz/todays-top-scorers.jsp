<%@ page import="Quiz.Quiz" %>
<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.ScoresStruct" %>
<%@ page import="java.util.ArrayList" %>

<%
    Quiz currentQuiz = (Quiz) request.getAttribute("quizObj");
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    ArrayList<ScoresStruct> todaystopScorers = qm.getTodaysTopScorers(currentQuiz.getId());
%>


<section class="group-section g-top-scorers">
    <div class="g-content">
        <div class="g-title-cont">
            <div class="g-title-bg">
                <h4 class="g-title">Today's Top Scorers</h4>
            </div>
        </div>
        <div class="g-list pt-4">

            <% if(todaystopScorers.isEmpty()) {%>
            <p> Be first to take the quiz!</p>
            <% } else { %>
            <ol>
                <%for(int i=0; i<todaystopScorers.size(); i++) { %>
                <li><a href=<%="/profile?username="+todaystopScorers.get(i).getUserName()%>><%=todaystopScorers.get(i).getUserName()%></a> (<%=todaystopScorers.get(i).getScore()%> Points)</li>
                <% } %>
            </ol>
            <% } %>
        </div>
    </div>
</section>
