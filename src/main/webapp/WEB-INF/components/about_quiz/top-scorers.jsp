<%@ page import="Quiz.Quiz" %>
<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.ScoresStruct" %>
<%@ page import="java.util.ArrayList" %>
<%
    Quiz currentQuiz = (Quiz) request.getAttribute("quizObj");
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    ArrayList<ScoresStruct> topScorers = qm.getTopScorers(currentQuiz.getId());
%>


<section class="group-section g-top-scorers">
    <div class="g-content">
        <div class="g-title-cont">
            <div class="g-title-bg">
                <h4 class="g-title">Top Scorers</h4>
            </div>
        </div>
        <div class="g-list pt-4">

            <% if(topScorers.isEmpty()) {%>
            <p> Be first to take the quiz!</p>
            <% } else { %>
            <ol>
                <%for(int i=0; i<topScorers.size(); i++) { %>
                <li><a href=<%="/profile?username="+topScorers.get(i).getUserName()%>><%=topScorers.get(i).getUserName()%></a> (<%=topScorers.get(i).getScore()%> Points)</li>
                <% } %>
            </ol>
            <% } %>
        </div>
    </div>
</section>
