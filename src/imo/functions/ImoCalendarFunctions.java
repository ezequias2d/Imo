package imo.functions;

import elfo.calendar.CalendarTools;
import elfo.calendar.Day;
import elfo.calendar.schedule.*;
import elfo.data.IRepositorio;
import elfo.exception.calendar.EventInvalidException;
import elfo.exception.calendar.HourNotExistException;
import elfo.users.User;
import elfo.users.UserController;
import imo.exception.EventNotBrandException;
import imo.property.Property;
import imo.property.PropertyController;

public class ImoCalendarFunctions {

    private UserController userController;
    private Schedule schedule;
    private ScheduleRepository scheduleRepository;
    private DeltaTime deltaTime;


    public ImoCalendarFunctions(){

        userController = UserController.getInstance();
        scheduleRepository = ScheduleRepository.getInstance();
        deltaTime = new DeltaTime(0,30);
        scheduleUpdate(CalendarTools.getCurrentYear());
    }

    /**
     * Get schedule of current cpf
     */
    private void scheduleUpdate(int year){
        schedule = scheduleRepository.get(userController.getCurrentIdentity(), year);
    }

    /**
     */
    public void eventBrand(Property property, int[] date, int[] time) throws HourNotExistException, EventInvalidException, EventNotBrandException {
        String userFormalName = userController.getFormalNameCurrent();
        Schedule propertySchedule = property.getSchedule();
        String code = property.getIdentity();
        int minutes = time[1];
        int hour = time[0];
        String textEvent = String.format("Visit of property of code %s, ", code);
        scheduleUpdate(date[2]);


        if (this.schedule.isDisponible(date[1], date[0], hour, minutes, deltaTime) &&               //verifica se o usuario possui tempo disponivel
                propertySchedule.isDisponible(date[1], date[0], hour, minutes, deltaTime)) {        //verifica se o imovel esta disponivel

            User[] users = userController.getUsers(User.LEVEL_ADM2);                                //pega todos usuarios do tipo ADM2(Corretores de imoveis)
            users = CalendarTools.lessUserLoadedWithEvents(users, date[1], date[0]);                //ordena por corretores menos carregados(com eventos) no dia

            for (User adm2 : users) {
                Schedule schedule = scheduleRepository.get(adm2.getIdentity());
                schedule.createNewEvent(textEvent + userFormalName, date[1], date[0], hour, minutes, deltaTime);

                String ADM2FormalName = adm2.getFormalName();

                this.schedule.createNewEvent(textEvent + ADM2FormalName, date[1], date[0], hour, minutes, deltaTime);
                propertySchedule.createNewEvent(textEvent + "ADM2:" + ADM2FormalName + ", NORMAL:" + userFormalName, date[1], date[0], hour, minutes, deltaTime);
                return;
            }
        }
        throw new EventNotBrandException(new ScheduleEvent("",hour,minutes,deltaTime,new Day(date)),schedule,propertySchedule);
    }

    public ScheduleDay getDay(int[] date){
        scheduleUpdate(date[2]);
        schedule.setDate(date[2], date[1], date[0]);
        ScheduleDay out = schedule.getDay();
        schedule.upgradeToCurrentDay();
        return out;
    }
    public Schedule getSchedule(){
        return schedule;
    }
}
