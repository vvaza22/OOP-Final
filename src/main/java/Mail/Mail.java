package Mail;

import Account.Account;

public abstract class Mail {

    public static final int FRIEND_REQUEST = 1;
    public static final int CHALLENGE = 2;
    public static final int NOTE = 3;

    /* Who sent the mail */
    protected Account from;

    protected Account to;

    public Mail(Account from, Account to) {
        this.from = from;
        this.to = to;
    }

    public Account getFrom() {
        return this.from;
    }

    public Account getTo(){
        return this.to;
    }

    public abstract String getMessage();
    public abstract int getType();
}
