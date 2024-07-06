<%@ page import="java.util.ArrayList" %>
<%@ page import="Mail.*" %>
<%@ page import="Account.AccountManager" %>
<%@ page import="Account.Account" %>
<%@ page import="Global.SessionManager" %>

<link rel="stylesheet" href="/css/mail.css" />

<%
    String curTab = request.getAttribute("currentTab") == null ?
            "all" : (String) request.getAttribute("currentTab");

    // Get the account manager
    AccountManager acm = ((AccountManager)
            request.getServletContext().getAttribute("accountManager"));

    // Get Mail Manager
    MailManager mmgr = ((MailManager)request.getAttribute("mailManager"));

    // Get Session Manager
    SessionManager smgr =
            new SessionManager(request.getSession());

    ArrayList<Mail> mails = new ArrayList<Mail>();
    ArrayList<FriendRequestMail> reqs = mmgr.getFriendRequests(smgr.getCurrentUserAccount().getUserId());
    ArrayList<NoteMail> notes = mmgr.getNotes(smgr.getCurrentUserAccount().getUserId());
    ArrayList<ChallengeMail> challenges = mmgr.getChallenges(smgr.getCurrentUserAccount().getUserId());

    int numFriendReqs = reqs.size();
    int numChallenges = challenges.size();
    int numNotes = notes.size();
    int numMails = numFriendReqs + numNotes + numChallenges;

    if(curTab.equals("friend_req")){
        mails.addAll(reqs);
    }else if(curTab.equals("notes")){
        mails.addAll(notes);
    }else if(curTab.equals("challenges")){
        mails.addAll(challenges);
    }else if(curTab.equals("all")){
        mails.addAll(reqs);
        mails.addAll(notes);
        mails.addAll(challenges);
    }

//    // FOR TEST
//    int numFriendReqs = 2;
//    int numChallenges = 0;
//    int numNotes = 2;
//    int numMails = numFriendReqs + numChallenges + numNotes;
//    if(curTab.equals("all") || curTab.equals("friend_req")) {
//        mails.add(new FriendRequestMail( acm.getAccount("vazzu") ));
//        mails.add(new FriendRequestMail( acm.getAccount("bero") ));
//    }
//    if(curTab.equals("all") || curTab.equals("notes")) {
//        mails.add(new NoteMail( acm.getAccount("realtia"), "Hey! Can you check out my new quiz?" ));
//        mails.add(new NoteMail( acm.getAccount("elene"), "Metallica the best" ));
//    }

%>

<div class="mail-container">
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="mail-tabs">

                    <a href="/mail?tab=all" class="mail-tab <%= curTab.equals("all") ? "active" : "" %>">
                        <span>All</span>
                        <% if(numMails > 0) { %>
                            <span class="mail-tab-counter"><%= numMails %></span>
                        <% } %>
                    </a>
                    <a href="/mail?tab=friend_req" class="mail-tab <%= curTab.equals("friend_req") ? "active" : "" %>">
                        <span>Friend Requests</span>
                        <% if(numFriendReqs > 0) { %>
                        <span class="mail-tab-counter"><%= numFriendReqs %></span>
                        <% } %>
                    </a>
                    <a href="/mail?tab=challenges" class="mail-tab <%= curTab.equals("challenges") ? "active" : "" %>">
                        <span>Challenges</span>
                        <% if(numChallenges > 0) { %>
                        <span class="mail-tab-counter"><%= numChallenges %></span>
                        <% } %>
                    </a>
                    <a href="/mail?tab=notes" class="mail-tab <%= curTab.equals("notes") ? "active" : "" %>">
                        <span>Notes</span>
                        <% if(numNotes > 0) { %>
                        <span class="mail-tab-counter"><%= numNotes %></span>
                        <% } %>
                    </a>
                </div>
            </div>
            <div class="col-9">
                <div class="table-cont">

                    <table class="table table-hover">
                        <tr>
                            <th>#</th>
                            <th>From</th>
                            <th>Notification</th>
                        </tr>

                        <%
                            for(int i=1; i<=mails.size(); i++) {
                                Mail mail = mails.get(i - 1);
                                Account from = mail.getFrom();
                                Account to = mail.getTo();
                        %>
                            <tr data-mail-type="<%= mail.getType() %>">
                                <td><%= i %></td>
                                <td>
                                    <a href="/profile?username=<%= from.getUserName() %>"><%= from.getFirstName() %> <%= from.getLastName() %></a>
                                    <span class="sender-username">(<%= from.getUserName() %>)</span>
                                </td>
                                <td><%= mail.getMessage() %></td>
                                <td>
                                    <% if(mail.getType() == Mail.FRIEND_REQUEST) { %>
                                    <% FriendRequestMail frreq = (FriendRequestMail) mail; %>
                                           <% if (frreq.getStatus().equals("PENDING")) {%>
                                                <button class="btn btn-outline-success btn-round" onclick="acceptReq(<%=frreq.getId()%>)"> Accept </button >
                                                <button class="btn btn-outline-danger btn-round" onclick="rejectReq(<%=frreq.getId()%>)"> Decline </button >
                                           <% } else if(frreq.getStatus().equals("ACCEPTED")){%>
                                                <div class="accepted">
                                                    <h3>Friend Request Accepted.</h3>
                                                </div>
                                          <% } else if(frreq.getStatus().equals("REJECTED")){%>
                                                <div class="rejected">
                                                    <h3>Friend Request Rejected.</h3>
                                                </div>
                                        <%}} else if(mail.getType() == Mail.CHALLENGE) { %>
                                            <% ChallengeMail chall = (ChallengeMail)mail; %>
                                            <% if (chall.getStatus().equals("PENDING")) {%>
                                            <button class="btn btn-outline-success btn-round" onclick="acceptChal(<%=chall.getId()%>)"> Accept </button >
                                            <button class="btn btn-outline-danger btn-round" onclick="rejectChal(<%=chall.getId()%>)"> Decline </button >
                                            <% } else if(chall.getStatus().equals("CHL_ACCEPTED")){%>
                                            <div class="challenge_accepted">
                                                <h3>Challenge Accepted.</h3>
                                            </div>
                                            <% } else if(chall.getStatus().equals("CHL_REJECTED")){%>
                                            <div class="challenge_rejected">
                                                <h3>Challenge Rejected.</h3>
                                            </div>
                                    <% }} else if(mail.getType() == Mail.NOTE) {%>
                                    <% NoteMail note = (NoteMail)mail; %>
                                       <h3> <%=note.getNote()%> </h3>
                                    <%}%>
                                </td>
                            </tr>
                        <% } %>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


