<%@ page import="Question.PictureResponse" %>
<%@ page import="Quiz.*" %>
<%
  PictureResponse qObject = (PictureResponse) request.getAttribute("currentQuestion");
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
        <div class="question-image-cont">
          <img src="<%= qObject.getPicture() %>" alt="<%= qObject.getQuestionText() %>" />
        </div>
      </div>
      <div class="answer">
        <label class="answer-label" for="text_<%= qObject.getId() %>">Your answer: </label>
        <textarea id="text_<%= qObject.getId() %>" class="form-control answer-text" spellcheck="false" autocomplete="off" autocapitalize="off"><%= userAnswer %></textarea>
      </div>
      <div class="action">
        <% if(curQuestionIndex > 1) { %>
        <button onclick="pictureResponsePrev(<%= qObject.getId() %>, <%= curQuestionIndex %>)" class="btn btn-round btn-outline-secondary">Previous</button>
        <% } else { %>
        <button class="btn disabled btn-round btn-outline-secondary">Previous</button>
        <% } %>
        <% if(curQuestionIndex.equals(numQuestions)) { %>
        <button onclick="pictureResponseRev(<%= qObject.getId() %>, <%= curQuestionIndex %>)" class="btn btn-round btn-outline-success">Review</button>
        <% } else { %>
        <button onclick="pictureResponseNext(<%= qObject.getId() %>, <%= curQuestionIndex %>)" class="btn btn-round btn-outline-success">Next</button>
        <% } %>
      </div>
    </div>
  </div>
</div>