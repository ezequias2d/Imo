package elfo.exception.calendar;

import elfo.exception.ElfoException;

public class EventInvalidException extends ElfoException {
    public EventInvalidException(String message) {
        super(message);
    }
}
