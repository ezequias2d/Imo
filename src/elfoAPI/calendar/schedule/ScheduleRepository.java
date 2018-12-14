package elfoAPI.calendar.schedule;

import elfoAPI.calendar.CalendarTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.1.27
 */
public class ScheduleRepository implements IScheduleRepository{
    public static final String URI_DEPOT = "src/resources/depot/calendars.dat";
    private static ScheduleRepository scheduleRepository;

    private ArrayList<Schedule> schedules;
    private Map<Integer, ArrayList<Schedule>> schedulePerYears;

    /**
     * Constructor of ScheduleRepository
     */
    private ScheduleRepository(){
        this.schedules = new ArrayList<Schedule>();
        this.schedulePerYears = new HashMap<Integer, ArrayList<Schedule>>();
    }

    /**
     * Atualiza schedules para um ano especifico
     * @param year Year
     */
    private void updateSchedulesForTheYear(int year){
        if(!schedulePerYears.containsKey(year)){
            schedulePerYears.put(year,new ArrayList<Schedule>());
        }
        schedules = schedulePerYears.get(year);
    }

    /**
     * @return ScheduleRepository
     */
    public static ScheduleRepository getInstance(){
        if(scheduleRepository == null){
            scheduleRepository = new ScheduleRepository();
        }
        return scheduleRepository;
    }

    /**
     * Pega um calendario por identificador e ano
     * Criando um automaticamente se nao existir
     *
     * @param identify Identify
     * @param year Year
     * @return Schedule
     */
    @Override
    public Schedule get(String identify, int year){
        updateSchedulesForTheYear(year);
        int index = -1;
        for(int i = 0; i < schedules.size(); i++){
            if(schedules.get(i).getIdentifier().equals(identify)){
                index = i;
                break;
            }
        }
        if(index != -1){
            return schedules.get(index);
        }else{
            Schedule schedule = new Schedule(year,identify);
            schedules.add(schedule);
            return schedule;
        }
    }

    @Override
    public boolean remove(Schedule object) {
        return schedules.remove(object);
    }
    @Override
    public Schedule get(String indent) {
        return get(indent,CalendarTools.getCurrentYear());
    }
    @Override
    public int get(Schedule object) {
        return schedules.indexOf(object);
    }


}
