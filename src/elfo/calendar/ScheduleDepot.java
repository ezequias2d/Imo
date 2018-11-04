package elfo.calendar;

import elfo.users.UserTools;

import java.util.ArrayList;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class ScheduleDepot {
    private static ScheduleDepot scheduleDepot;
    private ArrayList<Schedule> schedules;

    private ScheduleDepot(){
        schedules = new ArrayList<Schedule>();
    }

    /**
     * @return ScheduleDepot
     */
    public static ScheduleDepot getInstance(){
        if(scheduleDepot == null){
            scheduleDepot = new ScheduleDepot();
        }
        return scheduleDepot;
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
