package elfoAPI.calendar;

import java.io.Serializable;

/**
 * Classe que representa um Dia no ElfoCalendar
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.22
 */
public class Day implements Serializable, Cloneable {
    private int day;
    private int weekDay;
    private int month;
    private int year;

    /**
     * Contrutor de Day
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

    /**
     * Contrutor de Day com vetor de data{day, month, year}
     * @param date Vetor de data
     */
    public Day(int[] date){
        this(date[0],date[1],date[2]);
    }

    /**
     * Cria um novo dia
     * @param day Day
     * @param month Month
     * @param year Year
     * @return New Day
     */
    public Day newDay(int day, int month, int year){
        return new Day(day,month,year);
    }

    /**
     * Formata em String a data
     * @return
     */
    public String toString(){
        return String.format("%d/%d/%d",day,month,year);
    }

    /**
     * Retorna dia
     * @return Day
     */
    public int getDay(){
        return day;
    }

    /**
     * Retorna mes
     * @return Month
     */
    public int getMonth(){
        return month;
    }

    /**
     * Retorna ano
     * @return Year
     */
    public int getYear(){
        return year;
    }

    /**
     * Retorna dia na semana(0 a 6)
     * @return
     */
    public int getWeekDay(){
        return weekDay;
    }

    /**
     * Pega data em vetor de inteiro {day, month, year}
     * @return Date in array of int
     */
    public int[] getDate(){
        return new int[]{day,month,year};
    }

    /**
     * Retorna verdadediro se a data fornecida for anterior a do dia
     * @param day Day
     * @param month Month
     * @param year Year
     * @return if is before
     */
    public boolean isBefore(int day, int month, int year){
        return (this.year < year ||
                (this.year == year && this.month < month) ||
                (this.year == year && this.month == month && this.day < day));
    }
    /**
     * Retorna verdadediro se a data fornecida for posterior a do dia
     * @param day Day
     * @param month Month
     * @param year Year
     * @return if is before
     */
    public boolean isAfter(int day, int month, int year){
        return (this.year > year ||
                (this.year == year && this.month > month) ||
                (this.year == year && this.month == month && this.day > day));
    }
}
