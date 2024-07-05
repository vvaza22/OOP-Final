<%@ page import="java.util.ArrayList" %>
<%@ page import="Question.*" %>
<%@ page import="Quiz.*" %>

<%
    Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");
    ArrayList<Question> questionList = currentQuiz.getQuestions();
%>

<div class="review-wrapper">
    <h3>Quiz Review</h3>

    <%
        if(currentQuiz.isImmediateCorrectionOn()) {
    %>
        <ol>
            <% for(int i=1; i<=questionList.size(); i++) {
                Question curQuestion = questionList.get(i-1);
            %>
                <% if(curQuestion.hasAnswer()) { %>
                    <li><a href="/quiz?id=<%= i %>">Question #<%= i %></a> - <span style="color: green">Answered</span> <i>(<%= curQuestion.countPoints() %> Points)</i></li>
                <% } else { %>
                    <li><a href="/quiz?id=<%= i %>">Question #<%= i %></a> - <span style="color: red">*Not Answered</span></li>
                <% } %>
            <% } %>
        </ol>
        <button onclick="finishAttempt()" class="btn btn-round btn-primary">End Quiz</button>
    <% } else { %>
        <ol>
            <% for(int i=1; i<=questionList.size(); i++) { %>
                <% if(questionList.get(i-1).hasAnswer()) { %>
                    <li><a href="/quiz?id=<%= i %>">Question #<%= i %></a> - <span style="color: green">Answered</span></li>
                <% } else { %>
                    <li><a href="/quiz?id=<%= i %>">Question #<%= i %></a> - <span style="color: red">*Not Answered</span></li>
                <% } %>
            <% } %>
        </ol>

        <button onclick="finishAttempt()" class="btn btn-round btn-primary">Finish and Submit</button>
    <% } %>

</div>