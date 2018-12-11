package elfo.exception.user;

import elfo.exception.ElfoException;
import elfo.users.User;


/**
 * Representa a falta de um usuario pesquisado de um tipo especifico
 * Exemplo:
 *      userController.getUser(cpf, User.LEVEL_NORMAL);
 *      lança a exceçao se, e somente se, nao existir um usuario com esse cpf e esse tipo de usuario.
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class UserDoesNotExistForThisTypeException extends ElfoException {
    public UserDoesNotExistForThisTypeException(int userType) {
        super("User does not exist for this type: " + User.STRINGS_USERS_TYPES[userType]);
    }
}
