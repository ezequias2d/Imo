package elfo.calendar;

import elfo.users.UserTools;

import java.util.ArrayList;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class ScheduleControl {
    private static ScheduleControl scheduleControl;
    private ArrayList<Schedule> schedules;

    private ScheduleControl(){
        schedules = new ArrayList<Schedule>();
    }

    /**
     * @return ScheduleControl
     */
    public static ScheduleControl getInstance(){
        if(scheduleControl == null){
            scheduleControl = new ScheduleControl();
        }
        return scheduleControl;
    }

    /**
     * @param cpf CPF in int[11]
     * @return Schedule of CPF
     */
    public Schedule getScheleduleOfCpf(int[] cpf){
        String cpfString = UserTools.convertCpfToString(cpf);
        int index = -1;
        for(int i = 0; i < schedules.size(); i++){
            if(schedules.get(i).getIdentifier().equals(cpfString)){
                index = i;
                break;
            }
        }
        if(index != -1){
            return schedules.get(index);
        }else{
            Schedule schedule = new Schedule();
            schedule.setIdentifier(cpfString);
            schedules.add(schedule);
            return schedule;
        }
    }

    /**
     * @return ArrayList of Schedule
     */
    public ArrayList<Schedule> getSchedules(){
        return schedules;
    }
}
