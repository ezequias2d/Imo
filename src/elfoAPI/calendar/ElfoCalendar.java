package elfoAPI.calendar;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Representa um calendario, podendo ser construido com qualquer
 * classe compativel com Day, como uma herança de Day e o proprio
 * Day.
 *
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.22
 * @param <T> T extends Day or T is Day
 */
public class ElfoCalendar<T extends Day> implements Serializable {
    private ArrayList<ArrayList<T>> array;
    protected int year;
    protected int month;
    protected int day;
    private T auxiliaryDay;
    /**
     * Contrutor de ElfoCalendar
     * Dia auxiliar usado para criar os outros dias usando a funçao 'newDay'.
     * @param year Year
     * @param month Month
     * @param day Day
     * @param auxiliaryDay Auxiliary Day
     */
    public ElfoCalendar(int year,int month, int day, T auxiliaryDay){
        this.array = new ArrayList<ArrayList<T>>();
        setDate(year,month,day);
        this.auxiliaryDay = auxiliaryDay;
        updateCalendar();
    }

    /**
     * Seta data
     * @param year Year
     * @param month Month
     * @param day ScheduleDay
     */
    public void setDate(int year, int month, int day){
        if(year > 1536 &&
                month > 0 && month <= 12 &&
                day > 0 && day <= CalendarTools.monthSize(month)){
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }

    /**
     * @param obj ScheduleDay
     */
    private void addMonth(ArrayList<T> obj){
        array.add(obj);
    }

    /**
     * Pega ArrayList que representa o Mes
     * @param index Month
     * @return ArraList of 'ScheduleDay's
     */
    private ArrayList<T> getMonth(int index){
        return array.get(index - 1);
    }

    /**
        Reconstroi o calendario
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
     * Pega dia de um mes e dia
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
     * Pega dia atual do calendario
     * @return Current day
     */
    public T getDay(){
        return this.getMonth(month).get(day - 1);
    }
}
