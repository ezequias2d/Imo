package elfoAPI.exception.calendar;

import elfoAPI.exception.ElfoException;

/**
 * Representa exceçao quando alguma informaçao de um evento e' invalida
 * Como a tentativa de registrar dois eventos no mesmo Schedule que dividem o mesmo
 * espaço de tempo na mesma data.
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class EventInvalidException extends ElfoException {
    public EventInvalidException(String message) {
        super(message);
    }
}
