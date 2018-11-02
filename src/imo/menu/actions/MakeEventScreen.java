package imo.menu.actions;

import elfo.calendar.CalendarTools;
import elfo.calendar.DeltaTime;
import elfo.calendar.Schedule;
import elfo.calendar.ScheduleControl;
import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.User;
import elfo.users.UserControl;
import elfo.users.UserTools;
import imo.property.Property;
import imo.property.PropertyControl;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class MakeEventScreen {
    private PropertyControl propertyControl;
    private UserControl  userControl;
    private Menu menu;
    private Scanner sc;
    private Schedule schedule;
    private ScheduleControl scheduleControl;
    private DeltaTime deltaTime;
    String textEvent;
    public MakeEventScreen(){
        propertyControl = PropertyControl.getPropertyControl();
        userControl = UserControl.getUserControl();
        menu = Menu.getMenu();
        sc = UserTools.getScanner();
        scheduleControl = ScheduleControl.getInstance();
        deltaTime = new DeltaTime(0,30);
        scheduleUpdate();
    }
    private void scheduleUpdate(){
        schedule = scheduleControl.getScheleduleOfCpf(userControl.getCpfCurrent());
    }

    public void makeEvent(Menu menu){
        scheduleUpdate();
        boolean loop = true;
        String userFormalName = userControl.getFormalNameCurrent();
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
            for(User u : CalendarTools.lessUserLoadedWithEvents(users,date[1],date[0])){
                Schedule schedule = scheduleControl.getScheleduleOfCpf(u.getCpf());
                if(schedule.createNewEvent(textEvent  + userFormalName,date[1],date[0],hour,minutes,deltaTime)){
                    String ADM2FormalName = u.getFormalName();
                    this.schedule.createNewEvent(textEvent + ADM2FormalName,date[1],date[0],hour,minutes,deltaTime);
                    System.out.printf("Scheduled event!\nRealtor:%s\n",u.getFormalName());
                    System.out.printf(this.schedule.getElfoCalendar().getDayOfDate(date[1],date[0]).getVisualEvents());
                    System.out.printf("Code1:%s\nCode2:%s\n",schedule.getIdentifier(),this.schedule.getIdentifier());
                    break;
                }
            }
        }
        System.out.printf("\nunmarked event!\n");
    }
}
