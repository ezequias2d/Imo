package elfoAPI.calendar.schedule;

import elfoAPI.calendar.CalendarTools;
import elfoAPI.calendar.ElfoCalendar;
import elfoAPI.exception.calendar.EventInvalidException;
import elfoAPI.exception.calendar.HourNotExistException;
import elfoAPI.exception.data.DataCannotBeAccessedException;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Representa uma agenda de eventos
 * Esta agenda so pode ser criada pelo repositorio de Schedule
 * @author Ezequias Moises dos Santos Silva
 * @version 0.1.18
 */
public class Schedule extends ElfoCalendar<ScheduleDay>{

    private final String identifier;
    /**
     * Constroi um Schedule de um ano especifico
     * @param identifier Identificador unico
     * @param year Ano do schedule
     */
    Schedule(int year, String identifier){
        super(year,1,1, new ScheduleDay(0,0,0));
        updateCalendar();
        this.identifier = identifier;
    }

    /**
     * Muda para dia atual
     */
    public void upgradeToCurrentDay(){
        GregorianCalendar gCalendar;
        gCalendar = new GregorianCalendar();
        month = gCalendar.get(Calendar.MONTH) + 1;
        day = gCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getIdentifier(){
        return identifier;
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
    public void createNewEvent(String text,int monthNumber, int dayNumber,int hour, int minutes, DeltaTime deltaTime) throws EventInvalidException, HourNotExistException, DataCannotBeAccessedException {
        ScheduleDay day = this.getDayOfDate(monthNumber,dayNumber);
        day.addEvents(text,hour,minutes,deltaTime);
    }

    /**
     * Pega numero de eventos no dia
     * @param month Month
     * @param day ScheduleDay
     * @return ElfoNumber of events in day
     */
    public int getNumberOfEventInDay(int month, int day){
        return this.getDayOfDate(month,day).getEvents().size();
    }

    /**
     * verifica se esta disponivel uma parte de um dia especifico
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
