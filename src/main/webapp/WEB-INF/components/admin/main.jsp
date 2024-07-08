<%@ page import="Quiz.Quiz" %>
<%@ page import="Quiz.QuizManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Account.AccountManager" %>
<%@ page import="Account.Account" %>

<%
    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    AccountManager acm = (AccountManager) request.getServletContext().getAttribute("accountManager");
    ArrayList<Quiz> quizzes = qm.getRecentQuizzes();
    ArrayList<Account> accounts = acm.getAccounts();
%>

<style>
    .table {
        margin:0;
    }

    .admin-page-parent {
        display: flex;
        justify-content: space-around;
        padding: 2em;
    }

    .admin-page-accounts {
        margin-right: 3em;
    }

    .control-wrapper {
        flex-grow: 1;
    }

    .admin-page-quizzes, .admin-page-accounts {
        border: 1px solid #ccc;
        border-bottom: none;
    }

</style>

<div class="container admin-page-parent">
    <div class="control-wrapper">
        <div class="admin-page-accounts">
            <% if(accounts.isEmpty()) { %>
            <p>No users yet.</p>
            <% }else{%>
            <table class="table table-admin-page-accounts">
                <tr>
                    <th>Username</th>
                    <th>Action</th>
                </tr>
                <% for(int i=0; i<accounts.size(); i++) { %>
                <tr>
                    <td><a href="/profile?username=<%=accounts.get(i).getUserId()%>"><%=accounts.get(i).getUserName()%></a></td>
                    <td><button onclick="sendDeleteAccountRequest(<%=accounts.get(i).getUserId()%>)" class="btn btn-round btn-danger">Delete</button></td>
                </tr>
                <% } %>
            </table>
            <% } %>
        </div>
    </div>
    <div class="control-wrapper">
        <div class="admin-page-quizzes">
            <% if(quizzes.isEmpty()) { %>
                <p>No quizzes yet.</p>
            <% }else{%>
                <table class="table table-admin-page-quizzes">
                    <tr>
                        <th>Name</th>
                        <th>Author</th>
                        <th>Action</th>
                    </tr>
                    <% for(int i=0; i<quizzes.size(); i++) { %>
                    <tr>
                        <%
                            String author = acm.getAccountById(quizzes.get(i).getAuthorId()).getUserName();
                        %>
                        <td><a href="/about_quiz?id="<%=String.valueOf(quizzes.get(i).getId())%>><%=quizzes.get(i).getName()%></a></td>
                        <td><a href="/profile?username=<%=author%>"><%=author%></a></td>
                        <td><button onclick="sendDeleteQuizRequest(<%=quizzes.get(i).getId()%>)" class="btn btn-round btn-danger">Delete</button></td>
                    </tr>
                    <% } %>
                </table>
            <% } %>
        </div>
    </div>
</div>