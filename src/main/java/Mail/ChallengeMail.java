package Mail;

import Account.Account;

public class ChallengeMail extends Mail {

    public ChallengeMail(Account from, Account to, int id) {
        super(from, to, id);
    }

    @Override
    public String getMessage() {
        return from.getUserName() + " challenged you";
    }

    @Override
    public int getType() {
        return Mail.CHALLENGE;
    }
}
