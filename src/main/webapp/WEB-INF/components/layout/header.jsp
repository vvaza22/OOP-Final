<%@ page import="Global.SessionManager" %>
<%@ page import="Account.Account" %>
<%
    // Access the current http session
    SessionManager sessionManager = new SessionManager( request.getSession() );
%>

<header>
    <nav class="navbar navbar-expand-md navbar-dark navbar--blue">

        <% if(sessionManager.isTakingQuiz()) { %>
            <jsp:include page="header-content-quiz.jsp" />
        <% } else { %>
            <jsp:include page="header-content-default.jsp" />
        <% } %>

    </nav>
</header>