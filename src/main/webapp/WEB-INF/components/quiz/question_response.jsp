<%@ page import="Question.QuestionResponse" %><%
    QuestionResponse qObject = (QuestionResponse) request.getAttribute("currentQuestion");
    Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");
%>

<div class="row">
    <div class="col">
        <div class="question-cont">
            <div class="question">
                <h5>Question #<%= curQuestionIndex %></h5>
                <p><%= qObject.getQuestion() %></p>
            </div>
            <div class="answer">
                <label class="answer-label" for="answer-text">Your answer: </label>
                <textarea id="answer-text" class="answer-text" spellcheck="false" autocomplete="off" autocapitalize="off"></textarea>
            </div>
            <div class="action">
                <button onclick="questionResponsePrev()" class="btn btn-round btn-outline-secondary">Previous</button>
                <button onclick="questionResponseNext()" class="btn btn-round btn-outline-success">Next</button>
            </div>
        </div>
    </div>
</div>