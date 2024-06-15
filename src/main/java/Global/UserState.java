package Global;

import Account.Account;

public class UserState {
    private boolean isLoggedIn;
    private Account userAccount;

    UserState() {
        reset();
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public void reset() {
        isLoggedIn = false;
        userAccount = null;
    }

    public void setAccount(Account account) {
        this.userAccount = account;
        this.isLoggedIn = true;
    }

    public Account getAccount() {
        return this.userAccount;
    }
}
