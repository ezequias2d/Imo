package imo.functions;

import elfo.calendar.CalendarTools;
import elfo.calendar.Day;
import elfo.calendar.schedule.DeltaTime;
import elfo.calendar.schedule.Schedule;
import elfo.calendar.schedule.ScheduleDay;
import elfo.calendar.schedule.ScheduleRepository;
import elfo.data.IRepositorio;
import elfo.exception.calendar.EventInvalidException;
import elfo.exception.calendar.HourNotExistException;
import elfo.users.User;
import elfo.users.UserController;
import imo.property.Property;
import imo.property.PropertyRepository;

public class ImoCalendarFunctions {

    private UserController userController;
    private Schedule schedule;
    private ScheduleRepository scheduleRepository;
    private DeltaTime deltaTime;


    public ImoCalendarFunctions(){

        userController = UserController.getInstance();
        scheduleRepository = ScheduleRepository.getInstance();
        deltaTime = new DeltaTime(0,30);
        scheduleUpdate();
    }

    /**
     * Get schedule of current cpf
     */
    private void scheduleUpdate(){
        schedule = scheduleRepository.getScheleduleOfCpf(userController.getCpfCurrent());
    }

    /**
     */
    public void eventBrand(Property property, int[] date, int[] time) throws HourNotExistException, EventInvalidException {
        IRepositorio<Property> repositorioPropriedades = PropertyRepository.getInstance();
        String userFormalName = userController.getFormalNameCurrent();
        Schedule propertySchedule = property.getSchedule();
        int code = repositorioPropriedades.get(property);
        int minutes = time[1];
        int hour = time[0];
        String textEvent = String.format("Visit of property of code %d, ", code);
        scheduleUpdate();


        if (this.schedule.isDisponible(date[1], date[0], hour, minutes, deltaTime) &&               //verifica se o usuario possui tempo disponivel
                propertySchedule.isDisponible(date[1], date[0], hour, minutes, deltaTime)) {        //verifica se o imovel esta disponivel

            User[] users = userController.getUsers(User.LEVEL_ADM2);                                //pega todos usuarios do tipo ADM2(Corretores de imoveis)
            users = CalendarTools.lessUserLoadedWithEvents(users, date[1], date[0]);                //ordena por corretores menos carregados(com eventos) no dia

            for (User adm2 : users) {
                Schedule schedule = scheduleRepository.getScheleduleOfCpf(adm2.getCpf());
                schedule.createNewEvent(textEvent + userFormalName, date[1], date[0], hour, minutes, deltaTime);

                String ADM2FormalName = adm2.getFormalName();

                this.schedule.createNewEvent(textEvent + ADM2FormalName, date[1], date[0], hour, minutes, deltaTime);
                propertySchedule.createNewEvent(textEvent + "ADM2:" + ADM2FormalName + ", NORMAL:" + userFormalName, date[1], date[0], hour, minutes, deltaTime);
                scheduleRepository.save();
                break;
            }
        }
    }

    public ScheduleDay getDay(int[] date){
        scheduleUpdate();
        schedule.setDate(date[2], date[1], date[0]);
        ScheduleDay out = schedule.getDay();
        schedule.upgradeToCurrentDay();
        return out;
    }
}
