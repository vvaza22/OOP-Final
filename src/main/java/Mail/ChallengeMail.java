package Mail;

import Account.Account;

public class ChallengeMail extends Mail {

    public ChallengeMail(Account from, Account to) {
        super(from, to);
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
