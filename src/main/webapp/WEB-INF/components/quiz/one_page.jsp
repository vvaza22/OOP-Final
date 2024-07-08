<%@ page import="Quiz.Quiz" %>
<%@ page import="Question.*" %>
<%@ page import="java.util.ArrayList" %>
<%
    Quiz currentQuiz = (Quiz) request.getAttribute("currentQuiz");
    ArrayList<Question> questions = currentQuiz.getQuestions();
%>

<div class="quiz-cont">
    <div class="container">

        <% for(int i=1; i<=questions.size(); i++) {
            Question curQuestion = questions.get(i-1);

            // Pass the current question attribute to the component
            request.setAttribute("currentQuestion", curQuestion);
            request.setAttribute("curQuestionIndex", i);

                switch(curQuestion.getType()) {
                    case QuestionType.QUESTION_RESPONSE:
                    %><jsp:include page="question_response.jsp" /><%
                        break;

                    case QuestionType.FILL_BLANK:
                    %><jsp:include page="fill_blank.jsp" /><%
                            break;

                    case QuestionType.MULTIPLE_CHOICE:
                    %><jsp:include page="multiple_choice.jsp" /><% break;

                    case QuestionType.PICTURE_RESPONSE:
                    %><jsp:include page="picture_response.jsp" /><%
                    break;
            }
        }
    %>
        <div class="one-page-action">
            <button onclick="finishAttemptForOnePage(this)" class="btn btn-round btn-primary">Finish and Submit</button>
        </div>
    </div>
</div>