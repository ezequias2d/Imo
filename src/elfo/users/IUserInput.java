package elfo.users;


import java.io.IOException;

/**
 * Interface que implementa uma funçao de pedido de permiçao de uma conta.
 * Exemplo:
 *          Uma janela pedindo uma senha do usuario para verificar se o manuseador do sistema
 *          e realmente o dono da conta.
 *          ¬============================-=#=X¬
 *          | Pedido de permisao de adm       |
 *          ===================================
 *          | Senha:                          |
 *          |    _____________________        |
 *          |   |_____________________|       |
 *          |                       |  ok!  | |
 *          ¬=================================¬
 *
 */
public interface IUserInput {
    public abstract String getPassword(String mensager);
    public abstract String getText(String mensager);
    public abstract String getText(String mensager, String content);
    public abstract int[] getCPF(String mensager);
    public abstract void showMessage(String title, String header, String content);
    public boolean confirmationMessage(String title, String content);
}
