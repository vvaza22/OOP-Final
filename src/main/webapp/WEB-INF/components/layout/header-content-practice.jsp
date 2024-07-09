<%@ page import="Global.SessionManager" %>
<%@ page import="Account.Account" %>
<%
    // Access the current http session
    SessionManager sessionManager = new SessionManager( request.getSession() );
%>
<div class="container d-flex justify-content-center">
    <a href="/practice" class="navbar-brand title-box">
        <span>MyCoolQuiz</span>
    </a>
    <div class="instant-action">
        <span class="btn btn-danger" onclick="finishPractice()">End Practice</span>
    </div>
</div>