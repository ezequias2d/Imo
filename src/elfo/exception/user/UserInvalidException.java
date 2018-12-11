package elfo.exception.user;

import elfo.exception.ElfoException;

/**
 * Representa exceçao de informaçoes invalidas para um usuario.
 * Exemplo: Tentar registrar um usuario com um CPF invalido.
 *
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class UserInvalidException extends ElfoException {
    public UserInvalidException() {
        super("Invalid user information.");
    }
}
