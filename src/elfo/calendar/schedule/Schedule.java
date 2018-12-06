package elfo.calendar.schedule;

import elfo.calendar.CalendarTools;
import elfo.calendar.Day;
import elfo.calendar.ElfoCalendar;
import elfo.exception.calendar.EventInvalidException;
import elfo.exception.calendar.HourNotExistException;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class Schedule extends ElfoCalendar<ScheduleDay>{
    private GregorianCalendar gCalendar;
    private String identifier;

    /**
     * Constructor Private of schedule
     */
    public Schedule(){
        super(0,0,0, new ScheduleDay(0,0,0));
        upgradeToCurrentDay();
    }

    public void upgradeToCurrentDay(){
        gCalendar = new GregorianCalendar();
        year = gCalendar.get(Calendar.YEAR);
        month = gCalendar.get(Calendar.MONTH) + 1;
        day = gCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Create a new GregorianCalendar and configurate
     * @param year Year
     * @return GregorianCalendar
     */
    private GregorianCalendar createGCalendar(int year) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.YEAR, year);
        return cal;
    }

    public void setIdentifier(String identifier){
        this.identifier = identifier;
    }

    public String getIdentifier(){
        return identifier;
    }


    /**
     * Update the ElfoCalendar
     */
    private void updateCalendar(){
        gCalendar = createGCalendar(year);
        day = gCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Increse calendar in "m" months
     * @param m Months to increse
     */
    public void changeMonth(int m){
        this.month += month;
        gCalendar.add(Calendar.MONTH,m);
        updateCalendar();
    }

    /**
     * Increse year in "y" years
     * @param y Years to increse
     */
    public void changeYear(int y){
        this.year += y;
        gCalendar.add(Calendar.YEAR,y);
        updateCalendar();
    }

    /**
     * Create a new Event
     * @param text Description of the event
     * @param monthNumber Month of the event
     * @param dayNumber ScheduleDay in month of the event
     * @param hour Hour of event initialization time
     * @param minutes Minutes of event initialization time
     * @param deltaTime Time variation in deltatime
     */
    public void createNewEvent(String text,int monthNumber, int dayNumber,int hour, int minutes, DeltaTime deltaTime) throws EventInvalidException, HourNotExistException {
        ScheduleDay day = this.getDayOfDate(monthNumber,dayNumber);
        day.addEvents(text,hour,minutes,deltaTime);
    }

    /**
     * Prints the entire calendar for the current year
     */
    public void seeThisCalendar(){
        System.out.print(this.getVisualYear());
    }

    /**
     *Prints the current month
     */
    public void seeThisMonth(){
        System.out.print(this.getVisualMonth(month));
    }


    /**
     * @param month Month
     * @param day ScheduleDay
     * @return ElfoNumber of events in day
     */
    public int getNumberOfEventInDay(int month, int day){
        return this.getDayOfDate(month,day).getEvents().size();
    }

    /**
     * @param month Month
     * @param day ScheduleDay
     * @param hour Hour
     * @param minutes Minutes
     * @param deltaTime DeltaTime
     * @return true if disponible
     */
    public boolean isDisponible(int month, int day,int hour, int minutes, DeltaTime deltaTime) throws HourNotExistException {
        return this.getDayOfDate(month,day).isDisponible(hour,minutes,deltaTime);
    }
}
