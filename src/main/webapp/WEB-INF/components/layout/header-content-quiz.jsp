<%@ page import="Global.SessionManager" %>
<%@ page import="Account.Account" %>
<%
    // Access the current http session
    SessionManager sessionManager = new SessionManager( request.getSession() );
%>
<div class="container d-flex justify-content-center">
    <a href="/quiz" class="navbar-brand title-box">
        <span>MyCoolQuiz</span>
    </a>
    <div class="instant-action">
        <span class="btn btn-danger" onclick="finishAttempt()">End Quiz</span>
    </div>
</div>