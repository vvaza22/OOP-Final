<%@ page import="Account.Account" %>
<%
    Account userAccount = (Account) request.getAttribute("userAccount");
%>

<style>
    .red-button{
        color:red;
    }

    .blue-button{
        color: blue;
    }

    .edit-text{
        width: 100%;
    }
</style>

<main class="profile-page">
    <div class="container">
        <div class="row">

            <% if(userAccount != null) { %>

            <div class="col">
                <div class="user-profile">
                    <div class="user-action">
                        <button id="add_friend" class="btn btn-round btn-outline-primary">Add Friend</button>
                        <button id="rem_friend" class="btn btn-round btn-outline-danger">Unfriend</button>
                    </div>
                    <div class="profile-row">
                        <div class="user-image-cont">
                            <img src="/images/profile/sample_1.jpg" width="200" height="200" />
                            <div class="owner-action">
                                <div class="btn-action">
                                    <a href="#">Change Profile Picture</a>
                                </div>
                            </div>
                            <div class="achievement-cont">
                                <h4><i class="fa-solid fa-trophy"></i> My Achievements </h4>
                                <ul>
                                    <li style="color: green">Amateur Author</li>
                                    <li style="color: purple">Prolific Author</li>
                                    <li style="color: red">I am the greatest</li>
                                    <li style="color: blue">Practice Makes Perfect</li>
                                </ul>
                            </div>
                        </div>
                        <div class="user-details-cont">
                            <div class="name-lastname-cont">
                                <h3><%= userAccount.getFirstName() %> <%= userAccount.getLastName() %></h3>
                            </div>
                            <div class="username-cont">
                                <p><%= userAccount.getUserName() %></p>
                            </div>
                            <div class="about-cont">
                                <h4>About Me <a id="edit-about-me" class="about-me-edit" style="display: block;" href="#">edit</a></h4>
                                <p style="display: block" id="text-about-me" class="original-text"> Hi! My name is <%=userAccount.getFirstName()%> and I am a professional programmer. I hack websites for fun and I program stuff all day and night.</p>
                                <textarea style="display: none" id="write-about-me" class="edit-text">Hi! My name is <%=userAccount.getFirstName()%> and I am a professional programmer. I hack websites for fun and I program stuff all day and night.</textarea>
                                <button style="display: none" id="cancel-button" class="red-button" >Cancel</button>
                                <button style="display: none" id="save-button" class="blue-button" >Save</button>
                            </div>

                            <div class="profile-sub-row">
                                <div class="profile-note friends-cont">
                                    <h4>My Friends <span class="num-friends">(3)</span></h4>
                                    <ul>
                                        <li><a href="#">Elene Kvitsiani</a></li>
                                        <li><a href="#">Vasiko Vazagaevi</a></li>
                                        <li><a href="#">Gio Beridze</a></li>
                                    </ul>
                                </div>
                                <div class="profile-note my-quizzes-cont">
                                    <h4>Quizzes I Created</h4>
                                    <ul>
                                        <li><a href="#">Geography Quiz</a></li>
                                        <li><a href="#">Astronomy Quiz</a></li>
                                        <li><a href="#">Cool Exoplanets</a></li>
                                    </ul>
                                </div>
                                <div class="profile-note quizzes-took-cont">
                                    <h4>Quizzes I Took</h4>
                                    <ul>
                                        <li><a href="#">P = EXPTIME?</a></li>
                                        <li><a href="#">RP = BPP = P???</a></li>
                                        <li><a href="#">NSPACE is cool</a></li>
                                        <li><a href="#">Is NP cooler than P?</a></li>
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