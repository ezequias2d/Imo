package elfo.calendar;

import java.util.ArrayList;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class Day {
    private int day;
    private int weekDay;
    private int month;
    private int year;
    private ArrayList<Object> save;
    private ArrayList<CalendarEvent> events;

    /**
     * @param day Day
     * @param month Month
     * @param year Year
     */
    Day(int day, int month, int year){
        this.save = new ArrayList<Object>();
        this.day = day;
        this.month = month;
        this.year = year;
        this.weekDay = CalendarTools.weekDay(day,month,year);
        this.events = new ArrayList<CalendarEvent>();
    }

    public String toString(){
        return String.format("%d/%d/%d",day,month+1,year);
    }
    public int getDay(){
        return day;
    }
    public int getMonth(){
        return month;
    }
    public int getYear(){
        return year;
    }
    public int getWeekDay(){
        return weekDay;
    }

    /**
     * @return Events of the Day
     */
    public ArrayList<CalendarEvent> getEvents(){
        return events;
    }

    /**
     * Add object of 'save list'
     * @param obj Object
     */
    public void addSave(Object obj){
        save.add(obj);
    }

    /**
     * @return Return save list
     */
    public ArrayList<Object> getSave(){
        return save;
    }

    /**
     * Get events of the day in string
     * @return Event String
     */
    public String getVisualEvents(){
        String ret = String.format("(%d/%d/%d) | ", day,month + 1,year);
        int sizeDate = ret.length();
        boolean isNone = true;
        for(int i = 0; i < events.size(); i++){
            CalendarEvent event = events.get(i);
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
        if(isNone == true){
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
     * @return true, can be added
     */
    public boolean addEvents(String text,int hour, int minutes, DeltaTime time){
        CalendarEvent calendarEvent = new CalendarEvent(text,hour,minutes,time);
        if(isDisponible(calendarEvent)){
            events.add(calendarEvent);
            return true;
        }
        return false;
    }
    /**
     * Add event
     * @param calendarEvent Event
     * @return true, can be added
     */
    public boolean addEvents(CalendarEvent calendarEvent){
        if(isDisponible(calendarEvent)){
            events.add(calendarEvent);
            return true;
        }
        return false;
    }

    /**
     * Check if the time is available
     * @param hour Hour
     * @param minutes Minutes
     * @param time DeltaTime
     * @return True if disponible
     */
    public boolean isDisponible(int hour, int minutes, DeltaTime time){
        CalendarEvent calendarEvent = new CalendarEvent("",hour,minutes,time);
        return isDisponible(calendarEvent);
    }
    /**
     * Check if the time is available
     * @param calendarEvent CalendarEvent
     * @return True if disponible
     */
    public boolean isDisponible(CalendarEvent calendarEvent){
        for(CalendarEvent ce : events){
            if((ce.isInEvent(calendarEvent.getAbsoluteTime())               ||
                    ce.isInEvent(calendarEvent.getFinalAbsoluteTime())      ||
                    calendarEvent.isInEvent(ce.getAbsoluteTime())           ||
                    calendarEvent.isInEvent(ce.getFinalAbsoluteTime()))){
                return false;
            }
        }
        return true;
    }

}
