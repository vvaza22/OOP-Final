<%@ page import="Question.MultipleChoice" %>
<%@ page import="Quiz.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Question.Choice" %>
<%@ page import="Question.QuestionType" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="Practice.PracticeQuiz" %><%
  MultipleChoice qObject = (MultipleChoice) request.getAttribute("currentQuestion");
  Integer curQuestionIndex = (Integer) request.getAttribute("curQuestionIndex");
  ArrayList<Choice> choiceList = qObject.getChoices();

  // Check for practice mode
  boolean isPractice = request.getAttribute("practiceModeFlag") != null;
  PracticeQuiz curPracticeQuiz = (PracticeQuiz)
          request.getAttribute("currentPracticeQuiz");

  Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");

  HashMap<Integer, QuestionType> typeMap = QuestionType.createMap();
  QuestionType questionType = typeMap.get(qObject.getType());
  String questionTypeStr = questionType == null ? "UNKNOWN" : questionType.getTypeName();
%>

<div class="row">
  <div class="col">
    <div class="question-cont" data-index="<%= curQuestionIndex %>" data-type="<%= questionTypeStr %>" data-id="<%= qObject.getId() %>">
      <div class="question">
        <% if(!isPractice) { %>
          <h5>Question #<%= curQuestionIndex %></h5>
        <% } %>
        <p><%= qObject.getQuestionText() %></p>
      </div>
      <div class="answer answer-multiple">
        <%
          for(Choice choice : choiceList) {
            String inputName = "multi_choice_" + qObject.getId();
            String inputId = "choice_" + choice.getId() + "_for_" + curQuestionIndex;
        %>

        <% if((currentQuiz.isImmediateCorrectionOn() || isPractice) && qObject.hasAnswer()) { %>
          <div class="answer-selector-cont">
            <% if(choice.getId() == qObject.getUserAnswer()) { %>
              <% if(choice.getId() == qObject.getCorrectAnswerIndex()) { %>
                <div class="answer-radio ans-radio-correct">
                  <input id="<%= inputId %>" name="<%= inputName %>" type="radio" value="<%= choice.getId() %>" checked disabled />
                  <label for="<%= inputId %>"><%= choice.getText() %></label>
                </div>
              <% } else { %>
                <div class="answer-radio ans-radio-wrong">
                  <input id="<%= inputId %>" name="<%= inputName %>" type="radio" value="<%= choice.getId() %>" checked disabled />
                  <label for="<%= inputId %>"><%= choice.getText() %></label>
                </div>
              <% } %>
            <% } else { %>
              <% if(choice.getId() == qObject.getCorrectAnswerIndex()) { %>
                <div class="answer-radio ans-radio-correct">
                  <input id="<%= inputId %>" name="<%= inputName %>" type="radio" value="<%= choice.getId() %>" disabled />
                  <label for="<%= inputId %>"><%= choice.getText() %></label>
                </div>
              <% } else { %>
                <div class="answer-radio">
                  <input id="<%= inputId %>" name="<%= inputName %>" type="radio" value="<%= choice.getId() %>" disabled />
                  <label for="<%= inputId %>"><%= choice.getText() %></label>
                </div>
              <% } %>
            <% } %>
          </div>

        <% } else { %>

          <div class="answer-selector-cont">
            <% if(choice.getId() == qObject.getUserAnswer()) { %>
            <input id="<%= inputId %>" name="<%= inputName %>" type="radio" value="<%= choice.getId() %>" checked />
            <% } else { %>
            <input id="<%= inputId %>" name="<%= inputName %>" type="radio" value="<%= choice.getId() %>" />
            <% } %>
            <label for="<%= inputId %>"><%= choice.getText() %></label>
          </div>

        <% } %>


        <% } %>
      </div>
    </div>
  </div>
</div>