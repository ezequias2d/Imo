package elfo.exception.user;

import elfo.exception.ElfoException;
import elfo.users.UserTools;

public class UserIsRegistredException extends ElfoException {
    public UserIsRegistredException(int[] cpf) {
        super("User Registred '" + UserTools.convertCpfToString(cpf) + "'");
    }
}
