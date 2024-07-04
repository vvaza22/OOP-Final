<%@ page import="java.util.ArrayList" %>
<%@ page import="Question.*" %>
<%@ page import="Quiz.*" %>

<%
    Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");
    ArrayList<Question> questionList = currentQuiz.getQuestions();
%>

<div class="review-wrapper">
    <h3>Quiz Review</h3>

    <ol>
        <% for(int i=1; i<=questionList.size(); i++) { %>
        <% if(questionList.get(i-1).hasAnswer()) { %>
            <li>Question #<%= i %> - <span style="color: green">Answered</span></li>
        <% } else { %>
            <li>Question #<%= i %> - <span style="color: red">Not Answered</span></li>
        <% } %>
        <% } %>
    </ol>

    <button class="btn btn-round btn-primary">Finish and Submit</button>
</div>