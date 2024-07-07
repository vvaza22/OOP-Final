<%@ page import="Quiz.Quiz" %>
<%@ page import="Quiz.QuizManager" %>
<%@ page import="Account.*" %>
<%@ page import="Database.Database" %>
<%@ page import="Quiz.ScoresStruct" %>
<%@ page import="Quiz.Attempt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Global.SessionManager" %>

<%
    AccountManager acm = (AccountManager) request.getServletContext().getAttribute("accountManager");
    Quiz currentQuiz = (Quiz) request.getAttribute("quizObj");
    int authorId = currentQuiz.getAuthorId();
    Account authorAccount = acm.getAccountById(authorId);

    Database db = ((Database) request.getServletContext().getAttribute("database"));
    SessionManager smgr = new SessionManager(request.getSession());
    Account curr = smgr.getCurrentUserAccount();

    QuizManager qm = (QuizManager) request.getServletContext().getAttribute("quizManager");
    ArrayList<ScoresStruct> topScorers = qm.getTopScorers(currentQuiz.getId());
    ArrayList<ScoresStruct> todaystopScorers = qm.getTodaysTopScorers(currentQuiz.getId());
    ArrayList<ScoresStruct> recentTakers = qm.getRecentQuizTakers(currentQuiz.getId());
    ArrayList<Attempt> pastPerformance = qm.getAttemptList(curr.getUserId(), currentQuiz.getId());
%>

<link href="/css/about_quiz.css" rel="stylesheet" />

<div class="about-quiz-parent">
    <div class="container about_quiz">
        <div class="row">
            <div class="quiz-heading">
                <h3><%= currentQuiz.getName() %></h3>
                <span class="quiz-flag num-questions"><%= currentQuiz.getNumberOfQuestions() %> Questions</span>
                <% if(currentQuiz.isRandomized()) { %>
                <span class="quiz-flag randomized">Randomized</span>
                <% } %>
                <% if(currentQuiz.getDisplayMode() == Quiz.ONE_PAGE) { %>
                <span class="quiz-flag page">One Page</span>
                <% } else { %>
                <span class="quiz-flag page">Multiple Pages</span>
                <% } %>
                <% if(currentQuiz.isImmediateCorrectionOn()) { %>
                <span class="quiz-flag immediate">Immediate Correction</span>
                <% } %>
            </div>
            <p>Author: <a href="/profile?username=<%= authorAccount.getUserName() %>" class="profile-link"><img class="profile-image" src="<%= authorAccount.getImage() %>" width="40" height="40" /> <%= authorAccount.getUserName() %></a></p>
        </div>
        <div class="row">
            <% if(currentQuiz.getImage() != null) { %>
                <div class="about-quiz-image-cont">
                    <img src="<%= currentQuiz.getImage() %>" />
                </div>
            <% } %>
            <% if(currentQuiz.getDescription() == null) { %>
            <p><i>The quiz has no description</i></p>
            <% } else { %>
            <p><%= currentQuiz.getDescription() %></p>
            <% } %>
            <div class="action-control">
                <% if(currentQuiz.isPracticeAllowed()) { %>
                <button class="btn btn-round btn-outline-secondary">Practice</button>
                <% } %>
                <button id="take-quiz" class="btn btn-round btn-primary">Take Quiz</button>
                <% if (curr != null){ %>
                <div>
                    <div class="btn-action">
                        <button id="challenge-friend" class="btn btn-round btn-primary" onclick="sendChallenge(<%=currentQuiz.getId()%>)">Challenge a friend</button>
                    </div>
                </div>
                <%}%>
            </div>
            <div style="display: none">
                <input id="quiz-id" type="hidden" value="<%= currentQuiz.getId() %>" />
            </div>
        </div>
    </div>

    <div class="about-quiz-right-side">
        <section class="group-section g-top-scorers">
            <div class="g-content">
                <div class="g-title-cont">
                    <div class="g-title-bg">
                        <h4 class="g-title">Today's Top Scorers</h4>
                    </div>
                </div>
                <div class="g-list pt-4">

                    <% if(todaystopScorers.isEmpty()) {%>
                    <p> Be first to take the quiz!</p>
                    <% } else { %>
                    <ol>
                        <%for(int i=0; i<todaystopScorers.size(); i++) { %>
                        <li><a href=<%="/profile?username="+todaystopScorers.get(i).getUserName()%>><%=todaystopScorers.get(i).getUserName()%></a> (<%=todaystopScorers.get(i).getScore()%> Points)</li>
                        <% } %>
                    </ol>
                    <% } %>
                </div>
            </div>
        </section>
        <section class="group-section g-todays-top-scorers">
            <div class="g-content">
                <div class="g-title-cont">
                    <div class="g-title-bg">
                        <h4 class="g-title">Top Scorers</h4>
                    </div>
                </div>
                <div class="g-list pt-4">

                    <% if(topScorers.isEmpty()) {%>
                    <p> Be first to take the quiz!</p>
                    <% } else { %>
                    <ol>
                        <%for(int i=0; i<topScorers.size(); i++) { %>
                        <li><a href=<%="/profile?username="+topScorers.get(i).getUserName()%>><%=topScorers.get(i).getUserName()%></a> (<%=topScorers.get(i).getScore()%> Points)</li>
                        <% } %>
                    </ol>
                    <% } %>
                </div>
            </div>
        </section>
        <section class="group-section g-recent-takers">
            <div class="g-content">
                <div class="g-title-cont">
                    <div class="g-title-bg">
                        <h4 class="g-title">Recent Scorers</h4>
                    </div>
                </div>
                <div class="g-list pt-4">

                    <% if(recentTakers.isEmpty()) {%>
                    <p> Be first to take the quiz!</p>
                    <% } else { %>
                    <ol>
                        <%for(int i=0; i<recentTakers.size(); i++) { %>
                        <li><a href=<%="/profile?username="+recentTakers.get(i).getUserName()%>><%=recentTakers.get(i).getUserName()%></a> (<%=recentTakers.get(i).getScore()%> Points)</li>
                        <% } %>
                    </ol>
                    <% } %>
                </div>
            </div>
        </section>
        <p>Average Score: <%=qm.getAverageScore(currentQuiz.getId())%></p>
        <table class="g-past-performance">
            <p>Your Past Performance</p>
            <tr>
                <th>Nth</th>
                <th>Score</th>
                <th>Time</th>
            </tr>
            <% for(int i=0; i<pastPerformance.size(); i++) { %>
            <tr>
                <td><%= pastPerformance.size() - i %></td>
                <td><%= pastPerformance.get(i).getUserScore() %>/<%= pastPerformance.get(i).getMaxScore() %></td>
                <td><%= pastPerformance.get(i).getTime() %></td>
            </tr>
            <% } %>
        </table>
    </div>

</div>