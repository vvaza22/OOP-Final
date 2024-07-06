<%@ page import="Quiz.Quiz" %>
<%@ page import="Account.*" %>
<%
    AccountManager acm = (AccountManager) request.getServletContext().getAttribute("accountManager");
    Quiz currentQuiz = (Quiz) request.getAttribute("quizObj");
    int authorId = currentQuiz.getAuthorId();
    Account authorAccount = acm.getAccountById(authorId);
%>

<link href="/css/about_quiz.css" rel="stylesheet" />

<main class="container about_quiz">
    <div class="row">
        <div class="quiz-heading">
            <h3><%= currentQuiz.getName() %></h3>
            <span class="quiz-flag num-questions"><%= currentQuiz.getNumberOfQuestions() %> Questions</span>
            <% if(currentQuiz.isRandomized()) { %>
            <span class="quiz-flag randomized">Randomized</span>
            <% } %>
            <% if(currentQuiz.getDisplayMode() == Quiz.ONE_PAGE) { %>
            <span class="quiz-flag page">One Page</span>
            <% } else { %>
            <span class="quiz-flag page">Multiple Pages</span>
            <% } %>
            <% if(currentQuiz.isImmediateCorrectionOn()) { %>
            <span class="quiz-flag immediate">Immediate Correction</span>
            <% } %>
        </div>
        <p>Author: <a href="/profile?username=<%= authorAccount.getUserName() %>" class="profile-link"><img class="profile-image" src="<%= authorAccount.getImage() %>" width="40" height="40" /> <%= authorAccount.getUserName() %></a></p>
    </div>
    <div class="row">
        <% if(currentQuiz.getImage() != null) { %>
            <div class="about-quiz-image-cont">
                <img src="<%= currentQuiz.getImage() %>" />
            </div>
        <% } %>
        <% if(currentQuiz.getDescription() == null) { %>
        <p><i>The quiz has no description</i></p>
        <% } else { %>
        <p><%= currentQuiz.getDescription() %></p>
        <% } %>
        <div class="action-control">
            <% if(currentQuiz.isPracticeAllowed()) { %>
            <button class="btn btn-round btn-outline-secondary">Practice</button>
            <% } %>
            <button id="take-quiz" class="btn btn-round btn-primary">Take Quiz</button>
        </div>
        <div style="display: none">
            <input id="quiz-id" type="hidden" value="<%= currentQuiz.getId() %>" />
        </div>
    </div>
</main>