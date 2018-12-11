package elfo.exception.user.permission;

import elfo.exception.ElfoException;

/**
 * Represente um erro de senha(corrupçao da senha)
 * ou seja, senha do usuario e invalida para o sistema.
 *
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class UserPermissionCorruptedException extends ElfoException {
    public UserPermissionCorruptedException() {
        super("Password is corrupted");
    }
}
