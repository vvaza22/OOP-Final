<%@ page import="Question.PictureResponse" %><%
  PictureResponse qObject = (PictureResponse) request.getAttribute("currentQuestion");
  Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");
%>

<div class="row">
  <div class="col">
    <div class="question-cont">
      <div class="question">
        <h5>Question #<%= curQuestionIndex %></h5>
        <p><%= qObject.getQuestion() %></p>
        <div class="question-image-cont">
          <img src="<%= qObject.getPicture() %>" alt="<%= qObject.getQuestion() %>" />
        </div>
      </div>
      <div class="answer">
        <label class="answer-label" for="answer-text">Your answer: </label>
        <textarea id="answer-text" class="answer-text" spellcheck="false" autocomplete="off" autocapitalize="off"></textarea>
      </div>
      <div class="action">
        <button class="btn btn-round btn-outline-secondary">Previous</button>
        <button class="btn btn-round btn-outline-success">Next</button>
      </div>
    </div>
  </div>
</div>