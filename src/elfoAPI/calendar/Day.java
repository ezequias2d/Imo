package elfoAPI.calendar;

import java.io.Serializable;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.15
 */
public class Day implements Serializable {
    private int day;
    private int weekDay;
    private int month;
    private int year;

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
    }

    public Day(int[] date){
        this(date[0],date[1],date[2]);
    }

    public Day newDay(int day, int month, int year){
        return new Day(day,month,year);
    }

    public String toString(){
        return String.format("%d/%d/%d",day,month,year);
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

    public int[] getDate(){
        return new int[]{day,month,year};
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
}
