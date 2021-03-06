package elfoAPI.calendar.schedule;

import elfoAPI.calendar.CalendarTools;
import elfoAPI.calendar.Day;
import elfoAPI.data.IIdentificable;
import elfoAPI.exception.calendar.HourNotExistException;

import java.io.Serializable;

/**
 * Representa um evento do Schedule
 * @author Ezequias Moises dos Santos Silva
 * @version 0.1.18
 */
public class ScheduleEvent implements IIdentificable, Serializable {
    private int hour;
    private int minutes;
    private DeltaTime time;
    private int endHour;
    private int endMinutes;
    private boolean undefined;
    private String text;
    private String identity;

    private ScheduleDay day;

    /**
     * Event of Calendar
     * @param text desciption of event
     * @param hour Hour
     * @param minutes Minutes
     * @param time Time
     * @param day Day
     */
    public ScheduleEvent(String text, int hour, int minutes, DeltaTime time, ScheduleDay day) throws HourNotExistException {
        checkIfTimeExists(hour, minutes);
        this.text = text;
        this.hour = hour;
        this.minutes = minutes;
        this.time = time;
        this.undefined = false;
        this.day = day;
        this.identity = "" + (day.getEvents().size() + 1);
        DeltaTime dtEnd = new DeltaTime(hour + time.getDeltaHours(),minutes+time.getDeltaMinutes());
        endHour = dtEnd.getDeltaHours();
        endMinutes = dtEnd.getDeltaMinutes();
    }
    /**
     * Event of Calendar
     * @param text desciption of event
     * @param identity Identity
     * @param day Day
     */
    public ScheduleEvent(String text,String identity, ScheduleDay day) {
        this.text = text;
        this.undefined = true;
        this.day = day;
        this.identity = identity;
    }

    public boolean isUndefined(){
        return undefined;
    }

    /**
     * Verifica se horario existe
     * @param hour Hora
     * @param minutes Minutos
     * @throws HourNotExistException Se nao existir lança exceçao
     */
    private void checkIfTimeExists(int hour, int minutes) throws HourNotExistException{
        if((hour == 24 && minutes == 0) || (hour < 24 && minutes < 60)){
            return;
        }
        throw new HourNotExistException(hour,minutes);
    }

    /**
     * Verifica equivalencia
     * @param obj Objeto
     * @return Se equivalente
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof ScheduleEvent) {
            ScheduleEvent ce = (ScheduleEvent)obj;
            return ((ce.isUndefined() &&
                    ce.getText().equals(text))
                    || ((ce.getHour() == hour) &&
                    (ce.getMinutes() == minutes) &&
                    (ce.getTime().getDeltaHours() == time.getDeltaHours()) &&
                    (ce.getTime().getDeltaMinutes() == time.getDeltaMinutes())));
        }
        return false;
    }

    /**
     * Retorna o dia mascado com esse evento
     * @return Day of event
     */
    public Day getDay(){
        return day;
    }

    /**
     * Verifica se uma hora espeficifica na forma 'absoluta' esta dentro desse evento
     * @param absoluteTime Absolute Time
     * @return returns true if the given time is between the event
     */
    public boolean isInEvent(double absoluteTime){
        return (getAbsoluteTime() <= absoluteTime &&
                getFinalAbsoluteTime() >= absoluteTime);
    }

    /**
     * Pega tempo de inicio abosluto
     * @return returns the absolute time of the start of the event in hours
     */
    public double getAbsoluteTime(){
        double out = hour;
        out += minutes / 60.0;
        return out;
    }

    /**
     * Pega tempo do final absoluto
     * (final no sentido do horario que termina o evento)
     * @return returns the absolute time of the end of the event in hours
     */
    public double getFinalAbsoluteTime(){
        double out = endHour;
        out += endMinutes / 60.0;
        return out;
    }

    /**
     * Pega parte de "horas" do começo do horario do evento
     * @return Hour of Event
     */
    public int getHour(){
        return hour;
    }

    /**
     * Pega DeltaTime(variaçao do tempo/duraçao) do evento
     * @return Time
     */
    public DeltaTime getTime(){
        return time;
    }

    /**
     * Pega horario de começo formatado em String
     * @return Start Horary
     */
    public String getHoraryString(){
        return CalendarTools.formatOfTime(hour,minutes);
    }

    /**
     * Pega horario de termino formatado em String
     * @return End Horary
     */
    public String getEndHoraryString(){
        return CalendarTools.formatOfTime(endHour,endMinutes);
    }

    /**
     * Pega parte de "minutos" do começo do horario do evento
     * @return Minutes
     */
    public int getMinutes(){
        return minutes;
    }


    /**
     * Pega texto do evento com suas informaçoes detalhadas do evento
     * @return Text of event
     */
    public String getText(){
        return text;
    }

    @Override
    public String getIdentity() {
        return identity;
    }
}
