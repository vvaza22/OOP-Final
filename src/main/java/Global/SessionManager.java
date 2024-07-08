package Global;

import javax.servlet.http.HttpSession;
import Account.Account;
import Practice.PracticeQuiz;
import Quiz.Quiz;

public class SessionManager {
    private final HttpSession session;

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

    public Quiz getCurrentQuiz() {
        return (Quiz) this.session.getAttribute("current_quiz");
    }

    public void setCurrentQuiz(Quiz quiz) {
        this.session.setAttribute("current_quiz", quiz);
    }

    public void endCurrentQuiz() {
        this.session.removeAttribute("current_quiz");
    }

    public boolean isTakingQuiz() {
        return getCurrentQuiz() != null;
    }

    public PracticeQuiz getCurrentPracticeQuiz() {
        return (PracticeQuiz) this.session.getAttribute("current_practice_quiz");
    }

    public void setCurrentPracticeQuiz(PracticeQuiz quiz) {
        this.session.setAttribute("current_practice_quiz", quiz);
    }

    public void endCurrentPracticeQuiz() {
        this.session.removeAttribute("current_practice_quiz");
    }

    public boolean isTakingPracticeQuiz() {
        return getCurrentPracticeQuiz() != null;
    }

    public boolean isTakingAnyQuiz() {
        return isTakingQuiz() || isTakingPracticeQuiz();
    }

}
