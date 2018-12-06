package elfo.exception.user;

import elfo.exception.ElfoException;

public class UserInvalidPermissionException extends ElfoException {
    public UserInvalidPermissionException(String permission) {
        super("You do not have permission here, you need PERMISSION: "+ permission);
    }
}
