package elfo.calendar;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class DeltaTime {
    private int[] time;

    /**
     * @param dHours Delta hours
     * @param dMinutes Delta minutes
     */
    public DeltaTime(int dHours, int dMinutes){
        while(dMinutes >= 60){
            dHours += 1;
            dMinutes -= 60;
        }
        time = new int[]{dHours, dMinutes};
    }

    /**
     * @return Delta hours
     */
    public int getDeltaHours(){
        return time[0];
    }

    /**
     * @return Delta minutes
     */
    public int getDeltaMinutes(){
        return time[1];
    }

    /**
     * @return int[] = {Delta hours, Delta Minutes}
     */
    public int[] getTime(){
        return time;
    }

    /**
     * @return Absolute Delta Time in Double
     */
    public double getDeltaTime(){
        double dt = time[0];
        dt += time[0] / 60;
        return dt;
    }
}
