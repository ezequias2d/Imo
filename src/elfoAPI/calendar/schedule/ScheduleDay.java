package elfoAPI.calendar.schedule;

import elfoAPI.calendar.CalendarTools;
import elfoAPI.calendar.Day;
import elfoAPI.data.IIdentifiable;
import elfoAPI.exception.calendar.EventInvalidException;
import elfoAPI.exception.calendar.HourNotExistException;

import java.util.ArrayList;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class ScheduleDay extends Day {
    private ArrayList<ScheduleEvent> events;

    /**
     * @param day ScheduleDay
     * @param month Month
     * @param year Year
     */
    public ScheduleDay(int day, int month, int year){
        super(day,month,year);
        this.events = new ArrayList<ScheduleEvent>();
    }

    /**
     * Cria a si proprio
     * @param day Dia
     * @param month Mes
     * @param year Ano
     * @return
     */
    @Override
    public ScheduleDay newDay(int day, int month, int year){
        return new ScheduleDay(day,month,year);
    }



    /**
     * Pega eventos do dia
     * @return Events of the ScheduleDay
     */
    public ArrayList<ScheduleEvent> getEvents(){
        ArrayList<ScheduleEvent> newEvents = new ArrayList<ScheduleEvent>();
        newEvents.addAll(events);
        return newEvents;
    }


    /**
     * Add and create Event
     * @param text Descpition of event
     * @param hour Hour
     * @param minutes Minutes
     * @param time variation of time
     */
    public void addEvents(String text,int hour, int minutes, DeltaTime time) throws EventInvalidException, HourNotExistException {
        ScheduleEvent scheduleEvent = new ScheduleEvent(text,hour,minutes,time, this);
        addEvents(scheduleEvent);
    }

    public void deleteEvent(String identity){
        ArrayList<ScheduleEvent> eventsToRemove = new ArrayList<>();
        for(ScheduleEvent scheduleEvent : events){
            if(scheduleEvent.getIdentity().equals(identity)){
                eventsToRemove.add(scheduleEvent);
            }
        }
        events.removeAll(eventsToRemove);
    }

    /**
     * Add event
     * @param scheduleEvent Event
     */
    public void addEvents(ScheduleEvent scheduleEvent) throws EventInvalidException{
        if(isDisponible(scheduleEvent)){
            events.add(scheduleEvent);
            return;
        }
        throw new EventInvalidException("Event '" + scheduleEvent.getText() + "' not possible");
    }

    /**
     * Check if the time is available
     * @param hour Hour
     * @param minutes Minutes
     * @param time DeltaTime
     * @return True if disponible
     */
    public boolean isDisponible(int hour, int minutes, DeltaTime time) throws HourNotExistException {
        ScheduleEvent scheduleEvent = new ScheduleEvent("",hour,minutes,time,this);
        return isDisponible(scheduleEvent);
    }
    /**
     * Check if the time is available
     * @param scheduleEvent ScheduleEvent
     * @return True if disponible
     */
    public boolean isDisponible(ScheduleEvent scheduleEvent){
        for(ScheduleEvent se : events){
            if(((se.isInEvent(scheduleEvent.getAbsoluteTime())               ||
                    se.isInEvent(scheduleEvent.getFinalAbsoluteTime())      ||
                    scheduleEvent.isInEvent(se.getAbsoluteTime())           ||
                    scheduleEvent.isInEvent(se.getFinalAbsoluteTime()))) && !scheduleEvent.isUndefined()){
                return false;
            }
        }
        return true;
    }
}
