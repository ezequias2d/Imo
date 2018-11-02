package imo.menu.actions;

import elfo.calendar.CalendarTools;
import elfo.calendar.Day;
import elfo.calendar.Schedule;
import elfo.calendar.ScheduleControl;
import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserControl;
import elfo.users.UserTools;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class CalendarScreen {
    private Schedule schedule;
    private Menu menu;
    private int menuListIndex;
    private MenuList menuList;
    private Scanner sc;
    private UserControl userControl;
    public CalendarScreen(){
        this.userControl = UserControl.getUserControl();
        this.menu = Menu.getMenu();
        this.menuListIndex = menu.creatMenu();
        this.menuList = menu.getMenuList(menuListIndex);
        this.sc = UserTools.getScanner();

        this.menuList.addOption("Show Events this week", this::showWeekEvents);
        this.menuList.addOption("Show Month", this::showMonth);
        this.menuList.addOption("Show Events of day", this::showEventsDay);
    }
    public int getMenuListIndex(){
        return menuListIndex;
    }
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
            schedule = ScheduleControl.getInstance().getScheleduleOfCpf(UserControl.getUserControl().getCpfCurrent());
            return true;
        }
    }
    private void showMonth(Menu menu){
        if(scheduleUpdate()){
            System.out.printf("Month>");
            int month = sc.nextInt();
            System.out.printf("%s\n",schedule.getElfoCalendar().getVisualMonth(month - 1));
        }else{

        }

    }
    private void showEventsDay(Menu menu){
        if(scheduleUpdate()) {
            System.out.printf("Enter date in format (Example:'04/11')\n>");
            int[] date = CalendarTools.convertDate(sc.next());
            Day day = schedule.getElfoCalendar().getDayOfDate(date[1] - 1, date[0] - 1);
            System.out.printf("%s\n", day.getVisualEvents());
        }
    }
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
