package Mail;

import Account.Account;

public class ChallengeMail extends Mail {
    int quiz_id;
    String status;

    public ChallengeMail(Account from, Account to, int id, int quiz_id, String status) {
        super(from, to, id);
        this.quiz_id = quiz_id;
        this.status = status;
    }

    public int getQuizId(){return this.quiz_id;}

    public String getStatus(){return this.status;}

    @Override
    public String getMessage() {
        return from.getUserName() + " challenged you";
    }

    @Override
    public int getType() {
        return Mail.CHALLENGE;
    }
}
