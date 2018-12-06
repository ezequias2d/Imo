package elfo.calendar.schedule;

import elfo.data.BasicTypes.DataArrayList;
import elfo.data.FileReg;
import elfo.data.IRepositorio;
import elfo.users.UserTools;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class ScheduleRepository implements IRepositorio<Schedule> {
    public static final String URI_DEPOT = "src/resources/depot/calendars.edd";
    private static ScheduleRepository scheduleRepository;
    private ArrayList<Schedule> schedules;


    private ScheduleRepository(){

        System.out.printf("\nCarregamdo elementos..\n");
        this.schedules = new ArrayList<Schedule>();
        //this.schedules.addAll(dal.getElements());
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
     * @param cpf CPF in int[11]
     * @return schedule of CPF
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

    public void save(){

    }

    /**
     * @return ArrayList of schedule
     */
    public ArrayList<Schedule> getSchedules(){
        return schedules;
    }

    @Override
    public boolean add(Schedule object) {
        return schedules.add(object);
    }

    @Override
    public boolean remove(Schedule object) {
        return schedules.remove(object);
    }

    @Override
    public Schedule get(int index) {
        return schedules.get(index);
    }

    @Override
    public Schedule get(String indent) {
        int index = -1;
        for(int i = 0; i < schedules.size(); i++){
            if(schedules.get(i).getIdentifier().equals(indent)){
                index = i;
                break;
            }
        }
        if(index != -1){
            return schedules.get(index);
        }else{
            Schedule schedule = new Schedule();
            schedule.setIdentifier(indent);
            schedules.add(schedule);

            return schedule;
        }
    }

    @Override
    public int get(Schedule object) {
        return schedules.indexOf(object);
    }

    @Override
    public Schedule[] toArray() {
        return (Schedule[]) schedules.toArray();
    }

    @Override
    public void update() throws IOException {

    }
}
