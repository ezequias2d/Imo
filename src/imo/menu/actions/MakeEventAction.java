package imo.menu.actions;

import elfo.calendar.CalendarTools;
import elfo.calendar.schedule.DeltaTime;
import elfo.calendar.schedule.Schedule;
import elfo.calendar.schedule.ScheduleRepository;
import elfo.console.Menu;
import elfo.exception.calendar.EventInvalidException;
import elfo.exception.calendar.HourNotExistException;
import elfo.users.User;
import elfo.users.UserController;
import elfo.users.UserTools;


import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.4
 */
public class MakeEventAction {
    private UserController userController;
    private Scanner sc;
    private Schedule schedule;
    private ScheduleRepository scheduleRepository;
    private DeltaTime deltaTime;

    /**
     * Constructor
     */
    public MakeEventAction(){
        userController = UserController.getInstance();
        sc = UserTools.getScanner();
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
     * MenuCommand
     * MakeEvent
     * @param menu MenuHome
     */
    public void makeEvent(Menu menu){
        String textEvent;
        String userFormalName = userController.getFormalNameCurrent();
        scheduleUpdate();
        System.out.printf("Enter Propriety code>");
        int code = sc.nextInt();

        textEvent = String.format("Visit of property of code %d, ",
                code);

        System.out.printf("Enter date in format (Example:'04/11')\n>");
        int[] date = CalendarTools.convertToDate(sc.next());

        date[0]--;
        date[1]--;

        System.out.printf("The visit lasts 30 minutes\n");

        System.out.printf("Enter the time of the event\n>");
        int hour = sc.nextInt();

        System.out.printf("Enter the minutes of the event\n>");
        int minutes = sc.nextInt();

        User[] users = userController.getUsers(User.LEVEL_ADM2);

        try {
            if(this.schedule.isDisponible(date[1],date[0],hour,minutes,deltaTime)){
                for(User user : CalendarTools.lessUserLoadedWithEvents(users,date[1],date[0])){
                    Schedule schedule = scheduleRepository.getScheleduleOfCpf(user.getCpf());
                    schedule.createNewEvent(textEvent  + userFormalName,date[1],date[0],hour,minutes,deltaTime);
                    String ADM2FormalName = user.getFormalName();
                    this.schedule.createNewEvent(textEvent + ADM2FormalName,date[1],date[0],hour,minutes,deltaTime);
                    System.out.printf("Scheduled event!\nRealtor:%s\n",user.getFormalName());
                    System.out.printf(this.schedule.getDayOfDate(date[1],date[0]).getVisualEvents());
                    System.out.printf("Code1:%s\nCode2:%s\n",schedule.getIdentifier(),this.schedule.getIdentifier());
                    scheduleRepository.save();
                    break;
                    }
                } else {
                    System.out.printf("\nunmarked event!\n");
                }
            } catch (EventInvalidException | HourNotExistException e1) {
                e1.printStackTrace();
            }
    }
}
