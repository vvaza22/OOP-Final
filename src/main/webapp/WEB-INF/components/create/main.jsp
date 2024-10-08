<%@ page import="java.util.HashMap" %>
<%@ page import="Question.QuestionType" %>
<link rel="stylesheet" href="/css/create.css" />

<%
    HashMap<Integer, QuestionType> qTypes = (HashMap<Integer, QuestionType>) request.getAttribute("qTypes");
%>

<div class="container create-quiz">
    <form>
        <div class="row quiz-name">
            <h3>What's the name of your quiz?</h3>
            <input id="quiz-name" class="form-control" name="quiz-name" type="text" placeholder="e.g. Geography Quiz, Theoretical CS Quiz..." />
            <label for="quiz-description">Description: </label>
            <textarea class="form-control" id="quiz-description" placeholder="e. g. This quiz tests random knowledge in every field"></textarea>
            <label for="quiz-picture">Quiz Picture Link: </label>
            <input class="form-control" id="quiz-picture" placeholder="This image will be displayed on the home page and about quiz page." />
        </div>
        <div class="row quiz-options">
            <div class="option">
                <p>Display questions in: </p>
                <input onchange="pageChange(this)" id="d_one_page" name="display" type="radio" checked />
                <label for="d_one_page">One Page</label>
                <input onchange="pageChange(this)" id="d_mul_page" name="display" type="radio" />
                <label for="d_mul_page">Multiple Pages</label>
            </div>
            <div class="option">
                <input id="randomize" type="checkbox" />
                <label for="randomize">Randomize Question Order</label>
            </div>
            <div class="option">
                <input id="immediate" type="checkbox" disabled />
                <label for="immediate">Immediate Correction <i>(users receive immediate feedback, only for multiple pages)</i></label>
            </div>
            <div class="option">
                <input id="practice_mode" type="checkbox" />
                <label for="practice_mode">Practice Mode <i>(allow users to run the quiz in a practice mode)</i></label>
            </div>
        </div>
        <div id="question-wrapper" class="row question-wrapper">
        </div>
        <div class="row">
            <div class="question-control">
                <label for="q-type">Question Type: </label>
                <select class="form-select q-type" id="q-type" name="q-type">
                    <% for(Integer typeId : qTypes.keySet()) { %>
                    <option data-name="<%= qTypes.get(typeId).getTypeName() %>" value="<%= typeId %>"><%= qTypes.get(typeId).getTypeText() %></option>
                    <% } %>
                </select>
                <button id="add-question" class="btn btn-primary btn-round">Add Question</button>
            </div>
        </div>
        <div class="quiz-control">
            <button id="publish" class="btn btn-round btn-outline-success">Publish Quiz</button>
        </div>
    </form>
</div>

<script src="/js/create.js" type="text/javascript"></script>