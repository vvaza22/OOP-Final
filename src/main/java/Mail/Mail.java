package Mail;

import Account.Account;

public abstract class Mail {

    public static final int FRIEND_REQUEST = 1;
    public static final int CHALLENGE = 2;
    public static final int NOTE = 3;

    /* Who sent the mail */
    protected Account from;

    protected Account to;

    protected int id;

    public Mail(Account from, Account to, int id) {
        this.from = from;
        this.to = to;
        this.id = id;
    }

    public Account getFrom() {
        return this.from;
    }

    public Account getTo(){
        return this.to;
    }

    public int getId() {return this.id;}

    public abstract String getMessage();
    public abstract int getType();
}
