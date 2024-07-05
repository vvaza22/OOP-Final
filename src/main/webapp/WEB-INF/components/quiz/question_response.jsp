<%@ page import="Question.QuestionResponse" %>
<%@ page import="Quiz.*" %>
<%
    QuestionResponse qObject = (QuestionResponse) request.getAttribute("currentQuestion");
    Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");

    Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");
    Integer numQuestions = currentQuiz.getNumberOfQuestions();

    String userAnswer = qObject.getUserAnswer() == null ? "" : qObject.getUserAnswer();
%>

<div class="row">
    <div class="col">
        <div class="question-cont">
            <div class="question">
                <h5>Question #<%= curQuestionIndex %></h5>
                <p><%= qObject.getQuestionText() %></p>
            </div>
            <div class="answer">
                <label class="answer-label" for="text_<%= qObject.getId() %>">Your answer: </label>
                <textarea id="text_<%= qObject.getId() %>" class="form-control answer-text" spellcheck="false" autocomplete="off" autocapitalize="off"><%= userAnswer %></textarea>
            </div>
            <div class="action">
                <% if(curQuestionIndex > 1) { %>
                <button onclick="questionResponsePrev(<%= qObject.getId() %>, <%= curQuestionIndex %>)" class="btn btn-round btn-outline-secondary">Previous</button>
                <% } else { %>
                <button class="btn disabled btn-round btn-outline-secondary">Previous</button>
                <% } %>
                <% if(curQuestionIndex.equals(numQuestions)) { %>
                <button onclick="questionResponseRev(<%= qObject.getId() %>, <%= curQuestionIndex %>)" class="btn btn-round btn-outline-success">Review</button>
                <% } else { %>
                <button onclick="questionResponseNext(<%= qObject.getId() %>, <%= curQuestionIndex %>)" class="btn btn-round btn-outline-success">Next</button>
                <% } %>
            </div>
        </div>
    </div>
</div>