package imo.menu.actions;

import elfo.calendar.CalendarTools;
import elfo.calendar.DeltaTime;
import elfo.calendar.Schedule;
import elfo.calendar.ScheduleControl;
import elfo.console.Menu;
import elfo.users.User;
import elfo.users.UserControl;
import elfo.users.UserTools;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.4
 */
public class MakeEventAction {
    private UserControl  userControl;
    private Scanner sc;
    private Schedule schedule;
    private ScheduleControl scheduleControl;
    private DeltaTime deltaTime;

    /**
     * Constructor
     */
    public MakeEventAction(){
        userControl = UserControl.getInstance();
        sc = UserTools.getScanner();
        scheduleControl = ScheduleControl.getInstance();
        deltaTime = new DeltaTime(0,30);
        scheduleUpdate();
    }

    /**
     * Get Schedule of current cpf
     */
    private void scheduleUpdate(){
        schedule = scheduleControl.getScheleduleOfCpf(userControl.getCpfCurrent());
    }

    /**
     * MenuCommand
     * MakeEvent
     * @param menu MenuHome
     */
    public void makeEvent(Menu menu){
        String textEvent;
        String userFormalName = userControl.getFormalNameCurrent();
        scheduleUpdate();
        System.out.printf("Enter Propriety code>");
        int code = sc.nextInt();

        textEvent = String.format("Visit of property of code %d, ",
                code);

        System.out.printf("Enter date in format (Example:'04/11')\n>");
        int[] date = CalendarTools.convertDate(sc.next());

        date[0]--;
        date[1]--;

        System.out.printf("The visit lasts 30 minutes\n");

        System.out.printf("Enter the time of the event\n>");
        int hour = sc.nextInt();

        System.out.printf("Enter the minutes of the event\n>");
        int minutes = sc.nextInt();

        ArrayList<User> users = userControl.getUsers(UserControl.LEVEL_ADM2);

        if(this.schedule.isDisponible(date[1],date[0],hour,minutes,deltaTime)){
            for(User user : CalendarTools.lessUserLoadedWithEvents(users,date[1],date[0])){
                Schedule schedule = scheduleControl.getScheleduleOfCpf(user.getCpf());
                if(schedule.createNewEvent(textEvent  + userFormalName,date[1],date[0],hour,minutes,deltaTime)){
                    String ADM2FormalName = user.getFormalName();
                    this.schedule.createNewEvent(textEvent + ADM2FormalName,date[1],date[0],hour,minutes,deltaTime);
                    System.out.printf("Scheduled event!\nRealtor:%s\n",user.getFormalName());
                    System.out.printf(this.schedule.getElfoCalendar().getDayOfDate(date[1],date[0]).getVisualEvents());
                    System.out.printf("Code1:%s\nCode2:%s\n",schedule.getIdentifier(),this.schedule.getIdentifier());
                    break;
                }
            }
        }
        System.out.printf("\nunmarked event!\n");
    }
}
