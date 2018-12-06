package elfo.exception.user;

import elfo.exception.ElfoException;

public class UserInvalidException extends ElfoException {
    public UserInvalidException() {
        super("Invalid user information.");
    }
}
