package elfoAPI.exception.calendar;

import elfoAPI.exception.ElfoException;

public class InvalidCalendarDateException extends ElfoException {
    public InvalidCalendarDateException() {
        super("The date is invalid!");
    }
}
