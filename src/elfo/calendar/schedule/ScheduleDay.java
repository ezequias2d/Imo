package elfo.calendar.schedule;

import elfo.calendar.CalendarTools;
import elfo.calendar.Day;
import elfo.exception.calendar.EventInvalidException;
import elfo.exception.calendar.HourNotExistException;

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
    ScheduleDay(int day, int month, int year){
        super(day,month,year);
        this.events = new ArrayList<ScheduleEvent>();
    }

    public ScheduleDay newDay(int day, int month, int year){
        return new ScheduleDay(day,month,year);
    }



    /**
     * @return Events of the ScheduleDay
     */
    public ArrayList<ScheduleEvent> getEvents(){
        ArrayList<ScheduleEvent> newEvents = new ArrayList<ScheduleEvent>();
        newEvents.addAll(events);
        return newEvents;
    }

    /**
     * Get events of the day in string
     * @return Event String
     */
    public String getVisualEvents(){
        String ret = String.format("(%d/%d/%d) | ", getDay(),getMonth(),getYear());
        int sizeDate = ret.length();
        boolean isNone = true;
        for(int i = 0; i < events.size(); i++){
            ScheduleEvent event = events.get(i);
            if(i > 0){
                for(int j = 0; j < sizeDate - 2; j++){
                    ret += " ";
                }
                ret += "| ";
            }
            ret += CalendarTools.formatOfTime(event.getHour(),event.getMinutes()) +
                    " ---" +
                    CalendarTools.formatOfTime(event.getTime().getDeltaHours(),event.getTime().getDeltaMinutes()) +
                    "---> " +
                    CalendarTools.formatOfTime(event.getEndHour(),event.getEndMinutes()) +
                    " | " + event.getText() +
                    String.format("\n");
            isNone = false;
        }
        if(isNone){
            ret += String.format("None\n");
        }
        return ret;
    }

    /**
     * Add Event
     * @param text Descpition of event
     * @param hour Hour
     * @param minutes Minutes
     * @param time variation of time
     */
    public void addEvents(String text,int hour, int minutes, DeltaTime time) throws EventInvalidException, HourNotExistException {
        ScheduleEvent scheduleEvent = new ScheduleEvent(text,hour,minutes,time);
        addEvents(scheduleEvent);
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
        ScheduleEvent scheduleEvent = new ScheduleEvent("",hour,minutes,time);
        return isDisponible(scheduleEvent);
    }
    /**
     * Check if the time is available
     * @param scheduleEvent ScheduleEvent
     * @return True if disponible
     */
    public boolean isDisponible(ScheduleEvent scheduleEvent){
        for(ScheduleEvent ce : events){
            if((ce.isInEvent(scheduleEvent.getAbsoluteTime())               ||
                    ce.isInEvent(scheduleEvent.getFinalAbsoluteTime())      ||
                    scheduleEvent.isInEvent(ce.getAbsoluteTime())           ||
                    scheduleEvent.isInEvent(ce.getFinalAbsoluteTime()))){
                return false;
            }
        }
        return true;
    }

}
