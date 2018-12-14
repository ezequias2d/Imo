package elfoAPI.exception.user.permission;

import elfoAPI.exception.ElfoException;

/**
 * Representa a execeçao de tentativa de uma permiçao por um usuario que nao e do tipo do pedido.
 * Exemplo: Usuario logado e do tipo NORMAL
 * Programa pede permiçao do tipo ADM2
 * Execeçao e lansada por usuario nao ser do tipo de permissao pedido
 *
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class UserInvalidPermissionException extends ElfoException {
    public UserInvalidPermissionException(String permission) {
        super("You do not have permission here, you need PERMISSION: "+ permission);
    }
}
