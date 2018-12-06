package elfo.calendar.schedule;

import elfo.exception.calendar.HourNotExistException;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class ScheduleEvent {
    private int hour;
    private int minutes;
    private DeltaTime time;
    private int endHour;
    private int endMinutes;
    private String text;

    /**
     * Event of Calendar
     * @param text desciption of event
     * @param hour Hour
     * @param minutes Minutes
     * @param time Time
     */
    public ScheduleEvent(String text, int hour, int minutes, DeltaTime time) throws HourNotExistException {
        checkIfTimeExists(hour, minutes);
        this.text = text;
        this.hour = hour;
        this.minutes = minutes;
        this.time = time;
        DeltaTime dtEnd = new DeltaTime(hour + time.getDeltaHours(),minutes+time.getDeltaMinutes());
        endHour = dtEnd.getDeltaHours();
        endMinutes = dtEnd.getDeltaMinutes();
    }

    private void checkIfTimeExists(int hour, int minutes) throws HourNotExistException{
        if((hour == 24 && minutes == 0) || (hour < 24 && minutes < 60)){
            return;
        }
        throw new HourNotExistException(hour,minutes);
    }

    public boolean equals(Object obj){
        if(obj instanceof ScheduleEvent) {
            ScheduleEvent ce = (ScheduleEvent)obj;
            return ((ce.getHour() == hour) &&
                    (ce.getMinutes() == minutes) &&
                    (ce.getTime().getDeltaHours() == time.getDeltaHours()) &&
                    (ce.getTime().getDeltaMinutes() == time.getDeltaMinutes()));
        }
        return false;
    }

    /**
     * @param absoluteTime Absolute Time
     * @return returns true if the given time is between the event
     */
    public boolean isInEvent(double absoluteTime){
        return (getAbsoluteTime() < absoluteTime &&
                getFinalAbsoluteTime() > absoluteTime);
    }

    /**
     * @return returns the absolute time of the start of the event in hours
     */
    public double getAbsoluteTime(){
        double out = hour;
        out += minutes / 60.0;
        return out;
    }

    /**
     * @return returns the absolute time of the end of the event in hours
     */
    public double getFinalAbsoluteTime(){
        double out = endHour;
        out += endMinutes / 60.0;
        return out;
    }

    public int getHour(){
        return hour;
    }

    public DeltaTime getTime(){
        return time;
    }

    public int getMinutes(){
        return minutes;
    }

    public String getText(){
        return text;
    }

    public int getEndHour(){
        return endHour;
    }

    public int getEndMinutes(){
        return endMinutes;
    }

}
