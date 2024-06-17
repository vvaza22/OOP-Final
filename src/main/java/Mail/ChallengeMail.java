package Mail;

import Account.Account;

public class ChallengeMail extends Mail {

    public ChallengeMail(Account from) {
        super(from);
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public int getType() {
        return Mail.CHALLENGE;
    }
}
