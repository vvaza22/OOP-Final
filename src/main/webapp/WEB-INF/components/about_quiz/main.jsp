<%@ page import="Quiz.Quiz" %>
<%
    Quiz currentQuiz = (Quiz) request.getAttribute("quizObj");
%>

<link href="/css/about_quiz.css" rel="stylesheet" />

<main class="container about_quiz">
    <div class="row">
        <div class="quiz-heading">
            <h3><%= currentQuiz.getName() %></h3>
            <span class="num-questions"><%= currentQuiz.getNumberOfQuestions() %> Questions</span>
        </div>
        <p>Author: <a href="/profile?username=realtia" class="profile-link"><img class="profile-image" src="/images/profile/sample_1.jpg" width="40" height="40" /> realtia</a></p>
    </div>
    <div class="row">
        <% if(currentQuiz.getDescription() == null) { %>
        <p><i>The quiz has no description</i></p>
        <% } else { %>
        <p><%= currentQuiz.getDescription() %></p>
        <% } %>
        <div class="action-control">
            <% if(currentQuiz.isPracticeAllowed()) { %>
            <button class="btn btn-round btn-outline-secondary">Practice</button>
            <% } %>
            <button class="btn btn-round btn-primary">Take Quiz</button>
        </div>
    </div>
</main>