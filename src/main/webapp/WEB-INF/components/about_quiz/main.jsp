<%@ page import="Quiz.Quiz" %>
<%@ page import="Quiz.QuizManager" %>
<%@ page import="Account.*" %>
<%@ page import="Global.SessionManager" %>

<%
    AccountManager acm = (AccountManager) request.getServletContext().getAttribute("accountManager");
    Quiz currentQuiz = (Quiz) request.getAttribute("quizObj");
    int authorId = currentQuiz.getAuthorId();
    Account authorAccount = acm.getAccountById(authorId);

    SessionManager smgr = new SessionManager(request.getSession());
    Account curr = smgr.getCurrentUserAccount();
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
%>

<link href="/css/about_quiz.css" rel="stylesheet" />

<div class="about-quiz-parent">
    <div class="container about_quiz">
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
                <% if(smgr.isUserLoggedIn()) { %>
                <button id="challenge-friend" class="btn btn-round btn-outline-danger" onclick="sendChallenge(<%=currentQuiz.getId()%>)">Challenge a friend</button>
                <% } %>
                <% if(currentQuiz.isPracticeAllowed()) { %>
                <button class="btn btn-round btn-outline-secondary">Practice</button>
                <% } %>
                <button id="take-quiz" class="btn btn-round btn-primary">Take Quiz</button>
            </div>
            <div style="display: none">
                <input id="quiz-id" type="hidden" value="<%= currentQuiz.getId() %>" />
            </div>
        </div>
    </div>

    <div class="about-quiz-right-side">
        <jsp:include page="todays-top-scorers.jsp" />
        <jsp:include page="top-scorers.jsp" />
        <jsp:include page="recent-takers.jsp" />
        <p>Average Score: <%=qm.getAverageScore(currentQuiz.getId())%></p>
        <% if(curr!=null) { %>
            <jsp:include page="user-past-performance.jsp"/>
        <% } %>
    </div>

</div>