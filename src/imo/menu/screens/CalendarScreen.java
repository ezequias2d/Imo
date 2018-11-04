package imo.menu.screens;

import elfo.calendar.CalendarTools;
import elfo.calendar.Day;
import elfo.calendar.Schedule;
import elfo.calendar.ScheduleControl;
import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserControl;
import elfo.users.UserTools;

import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class CalendarScreen extends MenuList {
    private Schedule schedule;
    private int menuListIndex;
    private Scanner sc;
    private UserControl userControl;

    /**
     * Constructor
     */
    public CalendarScreen(){
        super(Menu.getInstance());
        this.userControl = UserControl.getInstance();
        this.menuListIndex = menuHome.addMenu(this);
        this.sc = UserTools.getScanner();

        this.addOption("Show Events this week", this::showWeekEvents);
        this.addOption("Show Month", this::showMonth);
        this.addOption("Show Events of day", this::showEventsDay);
    }

    /**
     * @return Index of MenuList in Menu
     */
    public int getMenuListIndex(){
        return menuListIndex;
    }

    /**
     * takes the current user's calendar, but if it's an adm1, ask for a cpf to get the cpf calendar
     * @return true if caught a 'valid' calendar
     */
    private boolean scheduleUpdate(){
        if(userControl.isADM1()){
            System.out.printf("Cpf>");
            int[] cpf = UserTools.stringToCpf(sc.next());
            if(UserTools.authenticateCpf(cpf)){
                schedule = ScheduleControl.getInstance().getScheleduleOfCpf(cpf);
                return true;
            }
            schedule = ScheduleControl.getInstance().getScheleduleOfCpf(UserTools.getCpfNull());
            return false;
        }else{
            schedule = ScheduleControl.getInstance().getScheleduleOfCpf(UserControl.getInstance().getCpfCurrent());
            return true;
        }
    }

    /**
     * MenuCommand
     * shows inserted month
     * @param menu MenuHome
     */
    private void showMonth(Menu menu){
        if(scheduleUpdate()){
            System.out.printf("Month>");
            int month = sc.nextInt();
            System.out.printf("%s\n",schedule.getElfoCalendar().getVisualMonth(month - 1));
        }else{

        }

    }

    /**
     * MenuCommand
     * Shows the day's events
     * @param menu MenuHome
     */
    private void showEventsDay(Menu menu){
        if(scheduleUpdate()) {
            System.out.printf("Enter date in format (Example:'04/11')\n>");
            int[] date = CalendarTools.convertDate(sc.next());
            Day day = schedule.getElfoCalendar().getDayOfDate(date[1] - 1, date[0] - 1);
            System.out.printf("%s\n", day.getVisualEvents());
        }
    }

    /**
     * MenuCommand
     * Show events of the week
     * @param menu MenuHome
     */
    private void showWeekEvents(Menu menu){
        if(scheduleUpdate()){
            Day day = schedule.getElfoCalendar().getDay();
            int dayN = day.getDay();
            int month = day.getMonth();
            for(Day d : schedule.getElfoCalendar().getNextWeekDays(month,dayN)){
                System.out.printf("%s\n",d.getVisualEvents());
            }
        }else{
            String[] out = new String[7];
            Day day = schedule.getElfoCalendar().getDay();
            int dayN = day.getDay();
            int month = day.getMonth();
            for(Schedule s : ScheduleControl.getInstance().getSchedules()){
                Day[] days = s.getElfoCalendar().getNextWeekDays(month,dayN);
                for(int i = 0; i < days.length; i++){
                    if(out[i] == null){
                        out[i] = "";
                    }
                    Day d = days[i];
                    String userName = userControl.getUser(UserTools.stringToCpf(s.getIdentifier())).getFormalName();
                    out[i] += d.getVisualEvents() + " - " + userName +"\n";
                }
            }
            for(String s: out){
                System.out.printf("%s\n\n",s);
            }
        }
    }
}
