package elfo.calendar;

import elfo.users.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class CalendarTools {
    private static GregorianCalendar cg;
    private static DecimalFormat dc;

    static private void cgCreat(){
        if(cg == null){
            cg = new GregorianCalendar();
        }
    }
    static private void dcCreat(){
        if(dc == null){
            dc = new DecimalFormat();
        }
    }

    /**
     * @param day Day
     * @param month Month
     * @param year Year
     * @return take the day of the day provided
     */
    static public int weekDay(int day,int month,int year){
        cgCreat();
        cg.set(Calendar.DAY_OF_MONTH,day);
        cg.set(Calendar.MONTH,month);
        cg.set(Calendar.YEAR,year);
        return cg.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @param number month number
     * @param year Year
     * @return  returns size of month
     */
    static public int monthSize(int number,int year){
        cgCreat();
        cg.set(Calendar.YEAR,year);
        cg.set(Calendar.MONTH, number + 1);
        cg.set(Calendar.DAY_OF_MONTH, -1);
        return cg.get(Calendar.DAY_OF_MONTH) + 1;
    }

    /**
     * @param number month number
     * @return returns size of month
     */
    static public int monthSize(int number){
        cgCreat();
        cg.set(Calendar.MONTH, number + 1);
        cg.set(Calendar.DAY_OF_MONTH, -1);
        return cg.get(Calendar.DAY_OF_MONTH) + 1;
    }


    /**
     * @param year Year
     * @return days of year
     */
    static public int dayYearSize(int year){
        cgCreat();
        int days = 0;
        for(int i = 0; i < 12; i++){
            days += monthSize(i,year);
        }
        return days;
    }

    /**
     *
     * @param hour Hour
     * @param minute Minute
     * @return formated time
     */
    static public String formatOfTime(int hour, int minute){
        dcCreat();
        dc.applyPattern("##00");
        return "("+dc.format(hour) + ":" + dc.format(minute)+")";
    }

    /**
     * @param number Number
     * @return month name
     */
    static public String monthName(int number){
        String [] weekNames = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        return weekNames[number];
    }

    /**
     * @param s String date "DD/MM/YYYY"
     * @return Convert string DD/MM/YYYY in int[] = {DD,MM,YYYY}
     */
    static public int[] convertDate(String s){
        String sp[] = s.split("/");
        int[] date = new int[sp.length];
        for(int i = 0; i < sp.length; i++){
            date[i] = Integer.valueOf(sp[i]);
        }
        return date;
    }

    /**
     * orna list of users by less loaded users on top
     * @param users user list
     * @param month event month
     * @param day event day
     * @return ordered list of users
     */
    static public User[] lessUserLoadedWithEvents(ArrayList<User> users, int month, int day){
        User[] user = new User[users.size()];
        int tam = users.size();
        ScheduleDepot scheduleDepot = ScheduleDepot.getInstance();
        for (int i = 0; i < tam; i++){
            int k = tam-1;
            Schedule schedule1 = scheduleDepot.getScheleduleOfCpf(users.get(i).getCpf());
            for (int j = 0; j < tam; j++){
                Schedule schedule2 = scheduleDepot.getScheleduleOfCpf(users.get(j).getCpf());
                if((schedule1.getNumberOfEventInDay(month,day) < schedule2.getNumberOfEventInDay(month,day)) ||
                        (schedule1.getNumberOfEventInDay(month,day) == schedule2.getNumberOfEventInDay(month,day) && i > j)){
                    k-=1;
                }
            }
            user[k] = users.get(i);
        }
        return user;
    }
}
