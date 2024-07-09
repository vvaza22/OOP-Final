<%@ page import="Account.Account" %>
<%@ page import="Global.SessionManager" %>
<%@ page import="Database.Database" %>
<%@ page import="Account.FriendRequestManager" %>
<%@ page import="Account.AccountManager" %>
<%@ page import="Account.FriendsManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quiz.QuizManager" %>
<%@ page import="Quiz.Quiz" %>
<%@ page import="Achievement.*" %>
<%@ page import="org.owasp.encoder.Encode" %>


<%
    Account userAccount = (Account) request.getAttribute("userAccount");

    SessionManager sessionManager = new SessionManager(request.getSession());
    Account myAccount = sessionManager.getCurrentUserAccount();

    Database db = ((Database) request.getServletContext().getAttribute("database"));
    FriendRequestManager frm = new FriendRequestManager(db);

    FriendsManager fm = new FriendsManager(db);
    AccountManager acm = ((AccountManager) request.getServletContext().getAttribute("accountManager"));
    QuizManager qm = (QuizManager)(request.getServletContext().getAttribute("quizManager"));
    AchievementManager achmgr = (AchievementManager)(request.getServletContext().getAttribute("achievementManager"));
%>

<style>
    .red-button{
        color:red;
    }

    .blue-button{
        color: blue;
    }

    .buttons{
        display: flex;
        gap: 10px;
    }

    .edit-text{
        width: 100%;
    }

    .original-text{
        width: 100%;
    }

    .user-details-cont {
        flex-grow: 1;
    }

    .user-action{
        display: flex;
        gap: 10px;
    }

</style>

<main class="profile-page">
    <div class="container">
        <div class="row">

            <% if(userAccount != null) { %>
            <div class="col">
                <div class="user-profile">

                    <%if(myAccount != null && userAccount != null){
                        int myId = myAccount.getUserId();
                        int userId = userAccount.getUserId();
                        boolean status = frm.isStatusByIdPen(myId, userId);
                        boolean reqSent = frm.isStatusByIdPen(userId, myId);
                        boolean areFriends = fm.areFriends(myId, userId);
                        if(sessionManager.isUserLoggedIn() && myAccount.getUserId() != userAccount.getUserId()) { %>
                            <div class="user-action">
                                <button onclick="sendNote('<%=userAccount.getUserName()%>')" id="note" class="btn btn-round btn-outline-info" style="display: block">send note</button>
                                <% if (reqSent){ %>
                                    <p>this user sent you a friend request</p>
                                <%}else  if (status){%>
                                    <button id="request" class="btn btn-round btn-outline-success" style="display: block">request sent</button>
                                <%} else if(!areFriends){ %>
                                    <button onclick="addFriend('<%=userAccount.getUserName()%>')" id="add_friend" class="btn btn-round btn-outline-primary" style="display: block">Add Friend</button>
                                <%} else if (areFriends){%>
                                    <button onclick="removeFriend('<%=userAccount.getUserName()%>')" id="rem_friend" class="btn btn-round btn-outline-danger" style="display: block">Unfriend</button>
                                <%} %>
                            </div>
                        <% } %>
                    <% } %>
                    <div class="profile-row">
                        <div class="user-image-cont">
                            <img id="profile-picture" src="<%=userAccount.getImage()%>" width="200" height="200" />
                            <% if (myAccount != null && myAccount.getUserId() == userAccount.getUserId()){ %>
                                <div class="owner-action">
                                    <div class="btn-action">
                                        <a id="change-profile-pic" class="changed-profile-pic" style="display: block;" href="#">Change Profile Picture</a>
                                    </div>
                                </div>
                            <% } %>
                            <div class="achievement-cont">
                                <h4><i class="fa-solid fa-trophy"></i> My Achievements </h4>
                                <%  ArrayList<Achievement> achs = achmgr.getAchievements(userAccount.getUserId()); %>
                                <ol>
                                <%    for(int i=0; i<achs.size(); i++){
                                        int achType = achs.get(i).getType();
                                        switch(achType){
                                          case 1: %>
                                        <li style="color: deepskyblue">Rookie Author</li>
                                        <% break;
                                          case 2: %>
                                        <li style="color: darkblue">Prolific Author</li>
                                        <% break;
                                          case 3: %>
                                        <li style="color: purple">Master Author</li>
                                        <% break;
                                          case 4: %>
                                        <li style="color: green">Quiz Slayer</li>
                                        <% break;
                                          case 5: %>
                                        <li style="color: black">Lord Of The Quizzes</li>
                                        <% break;
                                         case 6: %>
                                        <li style="color: yellow">Practitioner</li>
                                        <% break;
                                        }
                                    }%>
                                </ol>
                            </div>
                        </div>
                        <div class="user-details-cont">
                            <div class="name-lastname-cont">
                                <h3><%= userAccount.getFirstName() %> <%= userAccount.getLastName() %></h3>
                            </div>
                            <div class="username-cont">
                                <p><%= userAccount.getUserName() %></p>
                            </div>
                            <form id="profile-form" action="/profile" method="post">
                                <div class="about-cont">
                                    <h4>About Me
                                        <% if (myAccount != null && myAccount.getUserId() == userAccount.getUserId()) { %>
                                            <a id="edit-about-me" class="about-me-edit" style="display: inline;" href="#">edit</a>
                                        <% } %>
                                    </h4>
                                    <p style="display: block" id="text-about-me" class="original-text"> <%= Encode.forHtml(userAccount.getAboutMe())%></p>
<%--                                    this wont work if edit button isnt clicked but just for insurance--%>
                                    <% if(myAccount != null && myAccount.getUserId() == userAccount.getUserId()) { %>
                                        <textarea style="display: none" id="write-about-me" name="aboutMe" class="edit-text"><%= Encode.forHtml(userAccount.getAboutMe())%></textarea>
                                        <input type="hidden" id="username" value="<%= userAccount.getUserName() %>">
                                        <div class="buttons">
                                            <button style="display: none" id="cancel-button" class="btn btn-round btn-danger" >Cancel</button>
                                            <button style="display: none" id="save-button" class="btn btn-round btn-primary" >Save</button>
                                        </div>
                                    <% } %>
                                </div>
                            </form>
                            <div class="profile-sub-row">
                                <%
                                    int quantityOfFriends = 0;
                                    ArrayList<Integer> friendsListById = new ArrayList<Integer>();
                                    if(userAccount != null) {
                                        friendsListById = fm.friendsList(userAccount.getUserId());
                                        quantityOfFriends = friendsListById.size();
                                    }
                                %>
                                <div class="profile-note friends-cont">
                                    <h4>My Friends <span class="num-friends"><%=quantityOfFriends%></span></h4>
                                    <ul>
                                        <%if(quantityOfFriends != 0){
                                                for(Integer userId: friendsListById){
                                                    Account account = acm.getAccountById(userId); %>
                                                    <li><a href=<%="/profile?username="+ account.getUserName()%>><%=account.getUserName()%></a></li>
                                                <%}%>
                                        <%}%>
                                    </ul>
                                </div>
                                <div class="profile-note my-quizzes-cont">
                                    <h4>Quizzes I Created</h4>
                                    <ul>
                                        <% if(userAccount != null){
                                            ArrayList<Quiz> createdQuizzes = qm.getRecentlyCreatedQuizzes(userAccount.getUserId());
                                            if(createdQuizzes.isEmpty()) { %>
                                        <p> Unlock your creativity and inspire others. Create your first quiz today!</p>
                                        <% } else { %>
                                            <% for(int i=0; i<Math.min(5, createdQuizzes.size()); i++) { %>
                                                <%
                                                    Quiz quiz = createdQuizzes.get(i);
                                                    if(quiz==null) { %>
                                                        <li><span>[deleted quiz]</span></li>
                                                    <% } else { %>
                                                        <li><a href="/about_quiz?id=<%=quiz.getId()%>"><%=quiz.getName()%></a></li>
                                                    <%}%>
                                            <% } %>
                                            <%}%>
                                        <%}%>
                                    </ul>
                                </div>
                                <div class="profile-note quizzes-took-cont">
                                    <h4>Quizzes I Took</h4>
                                    <ul>
                                        <% if(userAccount != null){
                                            ArrayList<Quiz> takenQuizzes = qm.getRecentlyTakenQuizzes(userAccount.getUserId());
                                            if(takenQuizzes.isEmpty()) { %>
                                                <p> Challenge yourself and have fun. Take your first quiz today!</p>
                                            <% } else { %>
                                                 <% for(int i=0; i<Math.min(5, takenQuizzes.size()); i++) { %>
                                                <%
                                                    Quiz quiz = takenQuizzes.get(i);
                                                    if(quiz==null) { %>
                                                        <li><span>[deleted quiz]</span></li>
                                                    <% } else { %>
                                                        <li><a href="/about_quiz?id=<%=quiz.getId()%>"><%=quiz.getName()%></a></li>
                                                    <%}%>
                                                <% } %>
                                             <%}%>
                                        <%}%>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <% } else { %>

            <!-- User was not found message -->
            <div class="user-not-found">
                <h3>User "<%= request.getAttribute("reqUsername") %>" does not exist.</h3>
            </div>

            <% } %>

        </div>
    </div>
</main>