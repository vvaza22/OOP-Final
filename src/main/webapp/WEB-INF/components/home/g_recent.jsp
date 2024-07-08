<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="java.util.ArrayList" %>

<%
    QuizManager qm = (QuizManager)(request.getServletContext().getAttribute("quizManager"));
    ArrayList<Quiz> recentsList = qm.getRecentQuizzes();
%>

<section class="group-section g-recent">
    <div class="g-content">
        <div class="g-title-cont">
            <div class="g-title-bg">
                <h4 class="g-title">Recent</h4>
            </div>
        </div>
        <div class="g-list pt-4">
            <%if(recentsList.isEmpty()) { %>
                <p> No quizzes yet:( </p>
            <%}else {%>
                <ol>
                    <%for(int i=0; i<Math.min(5,recentsList.size()); i++) { %>
                    <li><a href="/about_quiz?id=<%=recentsList.get(i).getId()%>"><%=recentsList.get(i).getName()%></a></li>
                    <% } %>
                </ol>
            <%}%>
        </div>
    </div>
</section>