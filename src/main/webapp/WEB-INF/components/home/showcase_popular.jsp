<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="java.util.ArrayList" %>
<%
    QuizManager qm = (QuizManager)(request.getServletContext().getAttribute("quizManager"));
    ArrayList<Quiz> popularsList = qm.getPopularQuizzes();
%>
<div class="row">
    <h4>Popular</h4>
    <div class="quiz-row">
        <% if(popularsList.isEmpty()) { %>
            <p>No quizzes yet.</p>
        <%} else {%>
            <%
                for(int i=0; i<popularsList.size(); i++) {
                    Quiz curQuiz = popularsList.get(i);
            %>
                <div class="quiz-wrap">
                    <a href="/about_quiz?id=<%= curQuiz.getId() %>" class="quiz-card">
                        <%
                            String image = curQuiz.getImage();
                            if(image!=null && !image.isEmpty()) {
                        %>
                            <img class="quiz-card-img" src="<%=image%>" />
                        <% } %>
                            <span><%= curQuiz.getName() %></span>
                    </a>
                </div>
            <% } %>
        <% } %>
    </div>
</div>