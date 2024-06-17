package Mail;

import Account.Account;

public abstract class Mail {

    public static final int FRIEND_REQUEST = 1;
    public static final int CHALLENGE = 2;
    public static final int NOTE = 3;

    /* Who sent the mail */
    protected Account from;

    public Mail(Account from) {
        this.from = from;
    }

    public Account getFrom() {
        return this.from;
    }

    public abstract String getMessage();
    public abstract int getType();
}
