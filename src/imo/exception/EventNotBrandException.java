package imo.exception;

import elfo.calendar.schedule.Schedule;
import elfo.calendar.schedule.ScheduleEvent;
import elfo.exception.ElfoException;

public class EventNotBrandException extends ElfoException {
    public EventNotBrandException(ScheduleEvent scheduleEvent, Schedule... schedules) {
        super("");
        String menseger = "Unmaked Event! "
                + scheduleEvent.getHoraryString() + " | " + scheduleEvent.getDay().toString();
        for(Schedule schedule : schedules){
            menseger += "\n" + "Schedule: " + schedule.getIdentifier();
        }
        setMessage(menseger);
    }
}
