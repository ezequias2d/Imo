package elfo.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.3
 * @
 */
public class Schedule {
    private GregorianCalendar gCalendar;
    static ElfoCalendar elfoCalendar;
    static Schedule schedule;
    private int year;
    private int month;
    private int monthDay;
    private int monthSize;
    private int weekDay;
    private int weekMonth;

    /**
     * Constructor Private of Schedule
     */
    private Schedule(){
        gCalendar = new GregorianCalendar();
        year = gCalendar.get(Calendar.YEAR);
        month = gCalendar.get(Calendar.MONTH);
        updateCalendar();
        elfoCalendar = new ElfoCalendar(year,month,monthDay);
    }

    /**
     * Create a single Schedule and return
     * @return Single Schedule
     */
    public static Schedule getSchedule(){
        if(schedule == null){
            schedule = new Schedule();
        }
        return schedule;
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

    /**
     * Update the ElfoCalendar
     */
    private void updateCalendar(){
        monthDay = gCalendar.get(Calendar.DAY_OF_MONTH);
        weekDay = gCalendar.get(Calendar.DAY_OF_WEEK);
        monthSize = CalendarTools.monthSize(month,year);
        weekMonth = CalendarTools.weekMonthSize(month,year);
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
    public void createNewEvent(String text,int monthNumber, int dayNumber,int hour, int minutes, DeltaTime deltaTime){
        Day day = elfoCalendar.getDayOfDate(monthNumber,dayNumber);
        CalendarEvent ce = new CalendarEvent(text,hour,minutes,deltaTime);
        day.addEvents(ce);
    }
    public void seeThisCalendar(){
        System.out.print(elfoCalendar.getVisualYear());
    }
    public void seeThisMonth(){
        System.out.print(elfoCalendar.getVisualMonth(month));
    }
    public ElfoCalendar getElfoCalendar(){
        return elfoCalendar;
    }
}
