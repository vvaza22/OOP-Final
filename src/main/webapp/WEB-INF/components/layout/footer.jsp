<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>

<%
    ArrayList<String> authorList = new ArrayList<String>(
            Arrays.asList(
                    "Tia Alkhazishvili",
                    "Vasiko Vazagaevi",
                    "Elene Kvitsiani",
                    "Gio Beridze"
            )
    );
%>

<footer class="page-footer">
    <div class="page-detail">
        <b class="project-name">MyCoolQuiz.</b>
        <span>Project Authors: </span>
        <% for(int i=0; i<authorList.size(); i++) { %>
            <a href="#" class="author"><%= authorList.get(i) %></a>

            <% if(i != authorList.size() - 1)  { %>
                <span>, </span>
            <% } else { %>
                <span>. </span>
            <% } %>

        <% } %>
    </div>
</footer>