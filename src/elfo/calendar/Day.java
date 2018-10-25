package elfo.calendar;

import java.util.ArrayList;

public class Day {
    private int day;
    private int weekDay;
    private int month;
    private int year;
    private ArrayList<CalendarEvent> events;

    Day(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
        this.weekDay = CalendarTools.weekDay(day,month,year);
        this.events = new ArrayList<CalendarEvent>();
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
    public ArrayList<CalendarEvent> getEvents(){
        return events;
    }

    public void addEvents(CalendarEvent calendarEvent){
        events.add(calendarEvent);
    }

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

    public void addEvents(String text,int hour, int minutes, DeltaTime time){
        CalendarEvent calendarEvent = new CalendarEvent(text,hour,minutes,time);
        events.add(calendarEvent);
    }
}
