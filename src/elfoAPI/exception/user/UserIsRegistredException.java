package elfoAPI.exception.user;

import elfoAPI.exception.ElfoException;
import elfoAPI.users.UserTools;

/**
 * Representa exceçao lansada ao tentar registrar um usuario ja registrado
 *
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class UserIsRegistredException extends ElfoException {
    public UserIsRegistredException(int[] cpf) {
        super("User Registred '" + UserTools.convertCpfToString(cpf) + "'");
    }
}
