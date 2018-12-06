package elfo.exception.calendar;

import elfo.exception.ElfoException;

public class HourNotExistException extends ElfoException {
    public HourNotExistException(int hour, int minutes) {
        super("The hour:" + hour + ":" + minutes + " is invalid");
    }
}
