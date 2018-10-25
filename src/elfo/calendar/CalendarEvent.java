package elfo.calendar;

public class CalendarEvent {
    private int hour;
    private int minutes;
    private DeltaTime time;
    private int endHour;
    private int endMinutes;
    private boolean shown;
    private String text;

    public CalendarEvent(String text,int hour, int minutes,DeltaTime time){
        this.text = text;
        this.hour = hour;
        this.minutes = minutes;
        this.time = time;
        DeltaTime dtEnd = new DeltaTime(hour + time.getDeltaHours(),minutes+time.getDeltaMinutes());
        endHour = dtEnd.getDeltaHours();
        endMinutes = dtEnd.getDeltaMinutes();
    }
    public boolean equals(CalendarEvent ce){
        return ((ce.getHour() == hour) &&
                (ce.getMinutes() == minutes)&&
                (ce.getTime().getDeltaHours() == time.getDeltaHours()) &&
                (ce.getTime().getDeltaMinutes() == time.getDeltaMinutes()));
    }

    public boolean isInEvent(double absoluteTime){
        return (getAbsoluteTime() < absoluteTime &&
                getFinalAbsoluteTime() > absoluteTime);
    }

    public double getAbsoluteTime(){
        double out = hour;
        out += minutes / 60;
        return out;
    }
    public double getFinalAbsoluteTime(){
        double out = endHour;
        out += endMinutes / 60;
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

    public boolean isShown(){
        return shown;
    }

    public void setShown(boolean b){
        shown = b;
    }
}
