package elfo.calendar;
/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class CalendarEvent {
    private int hour;
    private int minutes;
    private DeltaTime time;
    private int endHour;
    private int endMinutes;
    private boolean shown;
    private String text;

    /**
     * Event of Calendar
     * @param text desciption of event
     * @param hour
     * @param minutes
     * @param time
     */
    public CalendarEvent(String text,int hour, int minutes,DeltaTime time){
        this.text = text;
        this.hour = hour;
        this.minutes = minutes;
        this.time = time;
        DeltaTime dtEnd = new DeltaTime(hour + time.getDeltaHours(),minutes+time.getDeltaMinutes());
        endHour = dtEnd.getDeltaHours();
        endMinutes = dtEnd.getDeltaMinutes();
    }
    public boolean equals(Object obj){
        if(obj instanceof CalendarEvent) {
            CalendarEvent ce = (CalendarEvent)obj;
            return ((ce.getHour() == hour) &&
                    (ce.getMinutes() == minutes) &&
                    (ce.getTime().getDeltaHours() == time.getDeltaHours()) &&
                    (ce.getTime().getDeltaMinutes() == time.getDeltaMinutes()));
        }
        return false;
    }

    /**
     * @param absoluteTime
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
