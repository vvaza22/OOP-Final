<%@ page import="Question.MultipleChoice" %>
<%@ page import="Quiz.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Question.Choice" %><%
  MultipleChoice qObject = (MultipleChoice) request.getAttribute("currentQuestion");
  Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");
  ArrayList<Choice> choiceList = qObject.getChoices();

  Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");
  Integer numQuestions = currentQuiz.getNumberOfQuestions();
%>

<div class="row">
  <div class="col">
    <div class="question-cont">
      <div class="question">
        <h5>Question #<%= curQuestionIndex %></h5>
        <p><%= qObject.getQuestionText() %></p>
      </div>
      <div class="answer answer-multiple">
        <%
          for(Choice choice : choiceList) {
            String inputName = "multi_choice_" + qObject.getId();
            String inputId = "choice_" + choice.getId() + "_for_" + curQuestionIndex;
        %>
        <div class="answer-selector-cont">
          <% if(choice.getId() == qObject.getUserAnswer()) { %>
          <input id="<%= inputId %>" name="<%= inputName %>" type="radio" value="<%= choice.getId() %>" checked />
          <% } else { %>
          <input id="<%= inputId %>" name="<%= inputName %>" type="radio" value="<%= choice.getId() %>" />
          <% } %>
          <label for="<%= inputId %>"><%= choice.getText() %></label>
        </div>
        <% } %>
      </div>
    </div>
  </div>
</div>