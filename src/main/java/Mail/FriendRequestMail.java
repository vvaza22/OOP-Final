package Mail;

import Account.Account;

public class FriendRequestMail extends Mail {

    public FriendRequestMail(Account from, Account to) {
        super(from, to);
    }

    @Override
    public String getMessage() {
        return from.getUserName() + " sent you a friend request.";
    }

    @Override
    public int getType() {
        return Mail.FRIEND_REQUEST;
    }
}
