package elfo.calendar;

import elfo.number.DeltaNumber;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class Schedule {
    private GregorianCalendar gCalendar;
    private ElfoCalendar elfoCalendar;
    private String identifier;
    private int year;
    private int month;
    private int monthDay;

    /**
     * Constructor Private of Schedule
     */
    public Schedule(){
        gCalendar = new GregorianCalendar();
        year = gCalendar.get(Calendar.YEAR);
        month = gCalendar.get(Calendar.MONTH);
        updateCalendar();
        elfoCalendar = new ElfoCalendar(year,month,monthDay);
    }

    /**
     * Create a new GregorianCalendar and configurate
     * @param year
     * @return
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
        monthDay = gCalendar.get(Calendar.DAY_OF_MONTH);
        gCalendar = createGCalendar(year);
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
     * @param dayNumber Day in month of the event
     * @param hour Hour of event initialization time
     * @param minutes Minutes of event initialization time
     * @param deltaTime Time variation in deltatime
     * @return
     */
    public boolean createNewEvent(String text,int monthNumber, int dayNumber,int hour, int minutes, DeltaTime deltaTime){
        Day day = elfoCalendar.getDayOfDate(monthNumber,dayNumber);
        return day.addEvents(text,hour,minutes,deltaTime);
    }

    /**
     * Prints the entire calendar for the current year
     */
    public void seeThisCalendar(){
        System.out.print(elfoCalendar.getVisualYear());
    }

    /**
     *Prints the current month
     */
    public void seeThisMonth(){
        System.out.print(elfoCalendar.getVisualMonth(month));
    }

    /**
     * @return ElfoCalendar of Schedule
     */
    public ElfoCalendar getElfoCalendar(){
        return elfoCalendar;
    }

    public int getNumberOfEventInDay(int month, int day){
        return elfoCalendar.getDayOfDate(month,day).getEvents().size();
    }

    public boolean isDisponible(int month, int day,int hour, int minutes, DeltaTime deltaTime){
        return elfoCalendar.getDayOfDate(month,day).isDisponible(hour,minutes,deltaTime);
    }
}
