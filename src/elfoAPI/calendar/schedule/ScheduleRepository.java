package elfoAPI.calendar.schedule;

import elfoAPI.calendar.CalendarTools;
import elfoAPI.data.Serializer;
import elfoAPI.exception.data.DataCannotBeAccessedException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.1.27
 */
public class ScheduleRepository implements IScheduleRepository{
    public static final String URI = "src/resources/depot/calendars.dat";
    private static ScheduleRepository scheduleRepository;

    private ArrayList<Schedule> schedules;
    private HashMap<Integer, ArrayList<Schedule>> schedulePerYears;
    private Serializer serializer;


    /**
     * Constructor of ScheduleRepository
     */
    private ScheduleRepository() throws DataCannotBeAccessedException {

        this.serializer = Serializer.getInstance();
        try {
            this.schedulePerYears = (HashMap<Integer, ArrayList<Schedule>>) serializer.open(URI);
            updateSchedulesForTheYear(CalendarTools.getCurrentYear());
        } catch (DataCannotBeAccessedException e) {
            this.schedulePerYears = new HashMap<Integer, ArrayList<Schedule>>();
            update();
        }
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
            try {
                scheduleRepository = new ScheduleRepository();
            } catch (DataCannotBeAccessedException e) {
                //nao faz sentido continuar
                System.exit(125);
            }
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
    public Schedule get(String identify, int year) throws DataCannotBeAccessedException {
        updateSchedulesForTheYear(year);
        int index = -1;
        for(int i = 0; i < schedules.size(); i++){
            if(schedules.get(i).getIdentity().equals(identify)){
                index = i;
                break;
            }
        }
        if(index != -1){
            return schedules.get(index);
        }else{
            Schedule schedule = new Schedule(year,identify);
            schedules.add(schedule);
            update();
            return schedule;
        }
    }

    @Override
    public boolean remove(Schedule object) throws DataCannotBeAccessedException {
        boolean out = schedules.remove(object);
        update();
        return out;
    }
    @Override
    public Schedule get(String identity) throws DataCannotBeAccessedException {
        return get(identity,CalendarTools.getCurrentYear());
    }
    @Override
    public int get(Schedule object) {
        return schedules.indexOf(object);
    }

    @Override
    public void update() throws DataCannotBeAccessedException {
        serializer.save(URI, schedulePerYears);
    }
}
