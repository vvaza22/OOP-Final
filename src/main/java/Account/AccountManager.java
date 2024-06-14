package Account;

import java.util.HashMap;

public class AccountManager {
    private final HashMap<String, Account> accounts;

    public AccountManager() {
        accounts = new HashMap<String, Account>();
    }

    public boolean accountExists(String username) {
        return accounts.containsKey(username);
    }

    public boolean passwordMatches(String username, String password) {
        return accounts.get(username).checkPassword(password);
    }

    public void registerAccount(String firstName, String lastName, String username, String image, String password) {
        Account ac = new Account(firstName, lastName, username, image, password);
        accounts.put(username,ac);
    }

}
