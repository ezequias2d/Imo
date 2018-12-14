package elfoAPI.calendar;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.15
 */
public class ElfoCalendar<T extends Day> implements Serializable {
    private ArrayList<ArrayList<T>> array;
    protected int year;
    protected int month;
    protected int day;
    private T auxiliaryDay;
    /**
     * @param year Year
     * @param month Month
     * @param day ScheduleDay
     */
    public ElfoCalendar(int year,int month, int day, T auxiliaryDay){
        this.array = new ArrayList<ArrayList<T>>();
        setDate(year,month,day);
        this.auxiliaryDay = auxiliaryDay;
        updateCalendar();
    }

    /**
     * @param year Year
     * @param month Month
     * @param day ScheduleDay
     * @return Success
     */
    public boolean setDate(int year, int month, int day){
        if(year > 1536 &&
                month > 0 && month <= 12 &&
                day > 0 && day <= CalendarTools.monthSize(month)){
            this.year = year;
            this.month = month;
            this.day = day;
            return true;
        }
        return false;
    }

    /**
     * @param obj ScheduleDay
     * @return if added
     */
    private boolean addMonth(ArrayList<T> obj){
        return array.add(obj);
    }

    /**
     * @param index Month
     * @return ArraList of 'ScheduleDay's
     */
    public ArrayList<T> getMonth(int index){
        return array.get(index - 1);
    }

    /**
        Update Calendar
     */
    @SuppressWarnings ( "unchecked" )
    protected void updateCalendar(){
        for(int i = 1; i <= 12; i++){
            ArrayList<T> days = new ArrayList<T>();
            for(int j = 1; j <= CalendarTools.monthSize(i,year);j++){
                days.add((T)auxiliaryDay.newDay(j,i,year));
            }
            addMonth(days);
        }
    }

    /**
     * @param month Month
     * @param day ScheduleDay
     * @return ScheduleDay of date
     */
    public T getDayOfDate(int month, int day){
        while (day > CalendarTools.monthSize(month) - 1 || month > 12 || day < 1 || month < 1){
            if(day > CalendarTools.monthSize(month) - 1){
                day -= CalendarTools.monthSize(month);
                month += 1;
            }else if(day <= 0){
                day = CalendarTools.monthSize(month) - 1;
                month -= 1;
            }
            if(month > 12){
                year += 1;
                month -= 12;
            }else if(month < 1){
                year -= 1;
                month = 12;
            }
        }
        return this.getMonth(month).get(day - 1);
    }

    /**
     * @return Current day
     */
    public T getDay(){
        return this.getMonth(month).get(day - 1);
    }

    /**
     *
     */

    /**
     * @param month Month
     * @param day ScheduleDay
     * @return Returns ScheduleDay[7] with next seven days
     */
    @SuppressWarnings ( "unchecked" )
    public T[] getNextWeekDays(int month, int day){
        T[] ret = (T[])Array.newInstance(auxiliaryDay.getClass(),7);
        for(int i = day ; i < day + 7; i++){
            ret[i - day] = this.getDayOfDate(month,i);
        }
        return ret;
    }

    /**
     * @param n n Days
     * @return Returns ScheduleDay[n] with before 'n' days
     */
    @SuppressWarnings ( "unchecked" )
    public T[] getBeforeDays(int n){
        T[] ret = (T[])Array.newInstance(auxiliaryDay.getClass(),n);
        for(int i = 0; i < n; i++){
            ret[i] = this.getDayOfDate(month,day + i);
        }
        return ret;
    }

    /**
     * @param month Month
     * @return Returns calendar mounted in standard form
     */
    public String getVisualMonth(int month){
        String ret = "";
        int con = -1;
        if(month >= 1 && month <= 12){
            int initialDay = this.getMonth(month).get(0).getWeekDay() + con;
            ret += String.format("\n%s\n",CalendarTools.monthName(month ));
            ArrayList<T> days = this.getMonth(month);
            for(int w = 0; w < initialDay; w++){
                ret += "    ";
            }
            for(int j = 0; j < days.size(); j++){
                T day = days.get(j);
                ret += String.format("%s",day.getDay());
                if(day.getDay() == this.day){
                    ret += "| ";
                }else if(day.getDay() == this.day - 1) {
                    ret += " |";
               // }else if(day.getEvents().size() > 0){
               //     ret += "<-";
                }else{
                    ret += "  ";
                }
                if(day.getDay() < 10){
                    ret += " ";
                }
                int weekDay = day.getWeekDay() + con;
                while(weekDay < 0){
                    weekDay += 7;
                }
                if(weekDay == 6){
                    ret += String.format("\n");
                }
            }
        }
        return ret;
    }

    public int getCurrentDay(){
        return day;
    }
    public int getCurrentMonth(){
        return month;
    }
    public int getCurrentYear(){
        return year;
    }

    /**
     * @return Returns year mounted in standard form
     */
    public String getVisualYear(){
        String ret = "";
        for (int i = 1; i <= 12; i++){
            ret += getVisualMonth(i);
        }
        ret += String.format("\n");
        return ret;
    }
}
