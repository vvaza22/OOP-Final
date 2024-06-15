package Account;

import Global.Constants;

import java.util.HashMap;

public class AccountManager {
    private final HashMap<String, Account> accountList;

    public AccountManager() {
        accountList = new HashMap<String, Account>();
        this.registerAccount(
                "Tia",
                "Alkhazishvili",
                "tia",
                "abc123"
        );
    }

    public boolean accountExists(String username) {
        return accountList.containsKey(username);
    }

    public boolean passwordMatches(String username, String password) {
        return accountList.get(username).checkPassword(password);
    }

    public Account getAccount(String username) {
        return accountList.get(username);
    }

    public void registerAccount(String firstName, String lastName, String username, String password) {
        Account ac = new Account(
                firstName,
                lastName,
                username,
                Constants.NO_IMAGE,
                Hash.hashPassword(password),
                "user"
        );
        accountList.put(username, ac);
    }

}
