package elfo.calendar;

public class DeltaTime {
    private int[] time;

    public DeltaTime(int dHours, int dMinutes){
        while(dMinutes >= 60){
            dHours += 1;
            dMinutes -= 60;
        }
        time = new int[]{dHours, dMinutes};
    }
    public int getDeltaHours(){
        return time[0];
    }
    public int getDeltaMinutes(){
        return time[1];
    }
    public int[] getTime(){
        return time;
    }
    public double getDeltaTime(){
        double dt = time[0];
        dt += time[0] / 60;
        return dt;
    }
}
