package elfoAPI.exception.calendar;

import elfoAPI.exception.ElfoException;

/**
 * Representa execeçao de hora invalida em um ScheduleEvent
 * Exemplo: tentar criar um ScheduleEvent que começa as 25:00(horario), como nao existe 25:00
 * no padrao de medida de tempo atual, hora e invalida.
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class HourNotExistException extends ElfoException {
    public HourNotExistException(int hour, int minutes) {
        super("The hour:" + hour + ":" + minutes + " is invalid");
    }
}
