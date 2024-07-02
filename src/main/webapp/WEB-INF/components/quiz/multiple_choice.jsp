<%@ page import="Question.MultipleChoice" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Question.Choice" %><%
  MultipleChoice qObject = (MultipleChoice) request.getAttribute("currentQuestion");
  Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");
  ArrayList<Choice> choiceList = qObject.getChoices();
%>

<div class="row">
  <div class="col">
    <div class="question-cont">
      <div class="question">
        <h5>Question #<%= curQuestionIndex %></h5>
        <p><%= qObject.getQuestion() %></p>
      </div>
      <div class="answer answer-multiple">
        <%
          for(Choice choice : choiceList) {
            String inputName = "multi_choice_" + curQuestionIndex;
            String inputId = "choice_" + choice.getId() + "_for_" + curQuestionIndex;
        %>
        <div class="answer-selector-cont">
          <input id="<%= inputId %>" name="<%= inputName %>" type="radio" value="<%= choice.getId() %>" />
          <label for="<%= inputId %>"><%= choice.getText() %></label>
        </div>
        <% } %>
      </div>
      <div class="action">
        <button class="btn btn-round btn-outline-secondary">Previous</button>
        <button class="btn btn-round btn-outline-success">Next</button>
      </div>
    </div>
  </div>
</div>