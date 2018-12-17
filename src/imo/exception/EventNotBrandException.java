package imo.exception;

import elfoAPI.calendar.schedule.Schedule;
import elfoAPI.calendar.schedule.ScheduleEvent;
import elfoAPI.exception.ElfoException;

/**
 * Representa a exceçao lançada quando nao consegue marca uma visita no ImoCalendarFunctions
 *      - Para marca um evento, precisa esta disponivel em 3 Schedules(O do usuario que quer
 *      visitar, do imovel e de ao menos um corretor)
 * @author Ezequias Moises dos Santos Silva
 */
public class EventNotBrandException extends ElfoException {
    public EventNotBrandException(ScheduleEvent scheduleEvent, Schedule... schedules) {
        super("");
        String menseger = "Unmaked Event! "
                + scheduleEvent.getHoraryString() + " | " + scheduleEvent.getDay().toString();
        for(Schedule schedule : schedules){
            menseger += "\n" + "Schedule: " + schedule.getIdentity();
        }
        setMessage(menseger);
    }
}
