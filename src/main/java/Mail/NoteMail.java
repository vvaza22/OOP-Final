package Mail;

import Account.Account;

public class NoteMail extends Mail {
    private String note;

    public NoteMail(Account from, Account to, String note, int id) {
        super(from, to, id);
        this.note = note;
    }

    public String getNote() { return note; }

    @Override
    public String getMessage() {
        if(this.from == null) {
            return "<b>[ deleted user ]</b> sent you a message";
        }
        return this.from.getUserName() + " sent you a message.";
    }

    @Override
    public int getType() {
        return Mail.NOTE;
    }
}
