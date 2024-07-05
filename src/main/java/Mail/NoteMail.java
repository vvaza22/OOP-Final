package Mail;

import Account.Account;

public class NoteMail extends Mail {
    private String note;

    public NoteMail(Account from, Account to, String note) {
        super(from, to);
        this.note = note;
    }

    @Override
    public String getMessage() {
        return note;
    }

    @Override
    public int getType() {
        return Mail.NOTE;
    }
}
