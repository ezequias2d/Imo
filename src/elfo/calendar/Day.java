package elfo.calendar;

import elfo.calendar.schedule.DeltaTime;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class Day {
    private int day;
    private int weekDay;
    private int month;
    private int year;
    //private ArrayList<ScheduleEvent> events;

    /**
     * @param day ScheduleDay
     * @param month Month
     * @param year Year
     */
    protected Day(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
        this.weekDay = CalendarTools.weekDay(day,month,year);
        //this.events = new ArrayList<ScheduleEvent>();
    }

    public Day newDay(int day, int month, int year){
        return new Day(day,month,year);
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

    public boolean isBefore(int day, int month, int year){
        return (this.year < year ||
                (this.year == year && this.month < month) ||
                (this.year == year && this.month == month && this.day < day));
    }
    public boolean isAfter(int day, int month, int year){
        return (this.year > year ||
                (this.year == year && this.month > month) ||
                (this.year == year && this.month == month && this.day > day));
    }

//    /**
//     * @return Events of the ScheduleDay
//     */
//    public ArrayList<ScheduleEvent> getEvents(){
//        ArrayList<ScheduleEvent> newEvents = new ArrayList<ScheduleEvent>();
//        newEvents.addAll(events);
//        return newEvents;
//    }

//    /**
//     * Get events of the day in string
//     * @return Event String
//     */
//    public String getVisualEvents(){
//        String ret = String.format("(%d/%d/%d) | ", day,month,year);
//        int sizeDate = ret.length();
//        boolean isNone = true;
//        for(int i = 0; i < events.size(); i++){
//            ScheduleEvent event = events.get(i);
//            if(i > 0){
//                for(int j = 0; j < sizeDate - 2; j++){
//                    ret += " ";
//                }
//                ret += "| ";
//            }
//            ret += CalendarTools.formatOfTime(event.getHour(),event.getMinutes()) +
//                    " ---" +
//                    CalendarTools.formatOfTime(event.getTime().getDeltaHours(),event.getTime().getDeltaMinutes()) +
//                    "---> " +
//                    CalendarTools.formatOfTime(event.getEndHour(),event.getEndMinutes()) +
//                    " | " + event.getText() +
//                    String.format("\n");
//            isNone = false;
//        }
//        if(isNone == true){
//            ret += String.format("None\n");
//        }
//        return ret;
//    }

//    /**
//     * Add Event
//     * @param text Descpition of event
//     * @param hour Hour
//     * @param minutes Minutes
//     * @param time variation of time
//     * @return true, can be added
//     */
//    public boolean addEvents(String text,int hour, int minutes, DeltaTime time){
//        ScheduleEvent calendarEvent = new ScheduleEvent(text,hour,minutes,time);
//        if(isDisponible(calendarEvent)){
//            events.add(calendarEvent);
//            return true;
//        }
//        return false;
//    }
//    /**
//     * Add event
//     * @param calendarEvent Event
//     * @return true, can be added
//     */
//    public boolean addEvents(ScheduleEvent calendarEvent){
//        if(isDisponible(calendarEvent)){
//            events.add(calendarEvent);
//            return true;
//        }
//        return false;
//    }

//    /**
//     * Check if the time is available
//     * @param hour Hour
//     * @param minutes Minutes
//     * @param time DeltaTime
//     * @return True if disponible
//     */
//    public boolean isDisponible(int hour, int minutes, DeltaTime time){
//        ScheduleEvent calendarEvent = new ScheduleEvent("",hour,minutes,time);
//        return isDisponible(calendarEvent);
//    }
//    /**
//     * Check if the time is available
//     * @param calendarEvent ScheduleEvent
//     * @return True if disponible
//     */
//    public boolean isDisponible(ScheduleEvent calendarEvent){
//        for(ScheduleEvent ce : events){
//            if((ce.isInEvent(calendarEvent.getAbsoluteTime())               ||
//                    ce.isInEvent(calendarEvent.getFinalAbsoluteTime())      ||
//                    calendarEvent.isInEvent(ce.getAbsoluteTime())           ||
//                    calendarEvent.isInEvent(ce.getFinalAbsoluteTime()))){
//                return false;
//            }
//        }
//        return true;
//    }

}
