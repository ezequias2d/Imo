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
    private ArrayList<CalendarEvent> getEvents(){
        return events;
    }

    private void addEvents(CalendarEvent calendarEvent){
        events.add(calendarEvent);
    }

    private void addEvents(int hour, int minutes, int time[]){
        CalendarEvent calendarEvent = new CalendarEvent(hour,minutes,time);
        events.add(calendarEvent);
    }
}
