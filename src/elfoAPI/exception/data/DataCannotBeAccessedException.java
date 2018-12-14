package elfoAPI.exception.data;

import elfoAPI.exception.ElfoException;


/**
 * Representa exceçao lansada quando 'Serializer' nao consegue acessar um arquivo requisitado
 * por Serializer.save ou Serializer.open.
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class DataCannotBeAccessedException extends ElfoException {
    public DataCannotBeAccessedException(String file, String ioMenseger) {
        super("The file " + file + " cannot be accessed" + "\n" + ioMenseger);
    }
}
