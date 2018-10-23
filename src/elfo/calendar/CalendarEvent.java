package elfo.calendar;

public class CalendarEvent {
    private int hour;
    private int minutes;
    private int[] time;
    private boolean shown;

    public CalendarEvent(int hour, int minutes,int[] time){
        this.hour = hour;
        this.minutes = minutes;
        this.time = time;
    }
    public boolean equals(CalendarEvent ce){
        return ((ce.getHour() == hour) &&
                (ce.getMinutes() == minutes)&&
                (ce.getTime()[0] == time[0]) &&
                (ce.getTime()[1] == time[1]));
    }

    public boolean isInEvent(int[] time){
        return (time[0] > hour &&
                time[1] > minutes &&
                time[0] < hour + this.time[0] &&
                time[1] < minutes + this.time[1]);
    }
    public boolean isInEvent(int hour, int minutes){
        int[] time = {hour,minutes};
        return isInEvent(time);
    }

    public int getHour(){
        return hour;
    }

    public int[] getTime(){
        return time;
    }

    public int getMinutes(){
        return minutes;
    }

    public boolean isShown(){
        return shown;
    }

    public void setShown(boolean b){
        shown = b;
    }
}
