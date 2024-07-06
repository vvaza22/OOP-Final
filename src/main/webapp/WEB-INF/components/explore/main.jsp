<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    ArrayList<Quiz> list = qm.getRecentQuizzes();
%>

<div class="row">

    <h4>Explore</h4>
    <div class="quiz-row">

        <%
            for(int i=0; i<list.size(); i++) {
                Quiz curQuiz = list.get(i);
        %>
        <div class="quiz-wrap">
            <a href="/about_quiz?id=<%= curQuiz.getId() %>" class="quiz-card">
                <img class="quiz-card-img" src="<%= curQuiz.getImage() %>" />
                <span><%= curQuiz.getName() %></span>
            </a>
        </div>
        <% } %>

    </div>
</div>


