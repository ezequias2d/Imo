package elfoAPI.exception.calendar;

import elfoAPI.exception.ElfoException;

/**
 * Representa um erro de data invalida
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class InvalidCalendarDateException extends ElfoException {
    public InvalidCalendarDateException() {
        super("The date is invalid!");
    }
}
