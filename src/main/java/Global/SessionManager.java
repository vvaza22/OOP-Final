package Global;

import javax.servlet.http.HttpSession;
import Account.Account;

public class SessionManager {
    private HttpSession session;

    public SessionManager(HttpSession session) {
        this.session = session;
    }

    public boolean isUserLoggedIn() {
        UserState userState = getUserState();
        return userState.isLoggedIn();
    }

    public Account getCurrentUserAccount() {
        if(!isUserLoggedIn()) return null;
        UserState userState = getUserState();
        return userState.getAccount();
    }

    public void setCurrentUser(Account userAccount) {
        UserState userState = getUserState();
        userState.setAccount(userAccount);
    }

    public void resetCurrentUser() {
        UserState userState = getUserState();
        userState.reset();
    }

    private UserState getUserState() {
        // Create the user state if the session is fresh
        if(this.session.getAttribute("user_state") == null) {
            UserState userState = new UserState();
            this.session.setAttribute("user_state", userState);
        }
        return (UserState) this.session.getAttribute("user_state");
    }

}
