package Mail;

import Account.Account;

public class ChallengeMail extends Mail {
    int quiz_id;
    String status;
    String quiz_name;

    public ChallengeMail(Account from, Account to, int id, int quiz_id, String quiz_name, String status) {
        super(from, to, id);
        this.quiz_id = quiz_id;
        this.status = status;
        this.quiz_name = quiz_name;
    }

    public int getQuizId(){return this.quiz_id;}

    public String getStatus(){return this.status;}

    @Override
    public String getMessage() {
        if(from == null) {
            return "<b>[ deleted user ]</b> " + " challenged you with \"" + quiz_name + "\"";
        }
        return from.getUserName() + " challenged you with \"" + quiz_name + "\"";
    }

    @Override
    public int getType() {
        return Mail.CHALLENGE;
    }
}
