package Mail;

import Account.Account;

public class FriendRequestMail extends Mail {
    private String status;

    public FriendRequestMail(Account from, Account to, int id, String status) {
        super(from, to, id);
        this.status = status;
    }

    @Override
    public String getMessage() {
        return from.getUserName() + " sent you a friend request.";
    }

    public String getStatus(){
        return status;
    }

    @Override
    public int getType() {
        return Mail.FRIEND_REQUEST;
    }
}
