<%@ page import="Global.SessionManager" %>
<%@ page import="Account.Account" %>
<%
    // Access the current http session
    SessionManager sessionManager = new SessionManager( request.getSession() );
%>

<header>
    <nav class="navbar navbar-expand-md navbar-dark navbar--blue">
        <div class="container">
            <a href="/" class="navbar-brand title-box">
                <span>MyCoolQuiz</span>
            </a>
            <div class="collapse navbar-collapse justify-content-between">
                <ul class="navbar-nav">
                    <li class="navbar-item menu-item">
                        <a href="#" class="nav-link">Create</a>
                    </li>
                    <li class="navbar-item menu-item">
                        <a href="#" class="nav-link">Explore</a>
                    </li>
                    <li class="navbar-item menu-item">
                        <a href="#" class="nav-link">Random</a>
                    </li>
                </ul>

                <form id="search-form" action="/profile" method="GET">
                    <div class="input-group">
                        <input class="search-box form-control" type="text" placeholder="Search by username..." name="username" id="search" autocomplete="off" />
                        <button class="btn search-button">
                            <i class="fa-solid fa-magnifying-glass"></i>
                        </button>
                    </div>
                </form>

                <% if(sessionManager.isUserLoggedIn()) { %>

                <%
                    // Get the user account
                    Account userAccount = sessionManager.getCurrentUserAccount();
                %>
                    <div class="account-buttons">
                        <div class="account-button">
                            <a href="/mail" class="mail-btn">
                                <div class="fa-solid fa-envelope"></div>
                                <div class="mail-counter"><span>3</span></div>
                            </a>

                        </div>
                        <div class="account-button">
                            <div id="user-dropdown" class="dropdown">
                                <a class="btn btn-secondary dropdown-toggle">
                                    <%= userAccount.getUserName() %>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="/profile?username=<%= userAccount.getUserName() %>">My Profile</a></li>
                                    <li><a id="user-logout" class="dropdown-item" href="#">Logout</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                <% } else { %>
                    <div class="account-buttons">
                        <a href="/login" class="btn btn-warning">Login</a>
                        <a href="/register" class="btn btn-warning">Register</a>
                    </div>
                <% } %>

            </div>
        </div>
    </nav>
</header>