package elfo.calendar;

import elfo.calendar.schedule.Schedule;
import elfo.calendar.schedule.ScheduleRepository;
import elfo.users.User;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class CalendarTools {
    private static GregorianCalendar gregorianCalendar;
    private static DecimalFormat decimalFormat;

    static private void gregorianCalendarCreat(){
        if(gregorianCalendar == null){
            gregorianCalendar = new GregorianCalendar();
        }
    }
    static private void decimalFormatCreat(){
        if(decimalFormat == null){
            decimalFormat = new DecimalFormat();
        }
    }

    /**
     * @param day ScheduleDay
     * @param month Month
     * @param year Year
     * @return take the day of the day provided
     */
    static public int weekDay(int day,int month,int year){
        gregorianCalendarCreat();
        gregorianCalendar.set(Calendar.DAY_OF_MONTH,day);
        gregorianCalendar.set(Calendar.MONTH,month - 1);
        gregorianCalendar.set(Calendar.YEAR,year);
        return gregorianCalendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @param month month number
     * @param year Year
     * @return  returns size of month
     */
    static public int monthSize(int month,int year){
        gregorianCalendarCreat();
        gregorianCalendar.set(Calendar.YEAR,year);
        gregorianCalendar.set(Calendar.MONTH, month - 1);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.get(Calendar.DAY_OF_MONTH) + 1;
    }

    /**
     * @param month month number
     * @return returns size of month
     */
    static public int monthSize(int month){
        gregorianCalendarCreat();
        gregorianCalendar.set(Calendar.MONTH, month - 1);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.get(Calendar.DAY_OF_MONTH) + 1;
    }


    /**
     * @param year Year
     * @return days of year
     */
    static public int dayYearSize(int year){
        gregorianCalendarCreat();
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
        decimalFormatCreat();
        decimalFormat.applyPattern("##00");
        return "("+ decimalFormat.format(hour) + ":" + decimalFormat.format(minute)+")";
    }

    /**
     * @param number ElfoNumber
     * @return month name
     */
    static public String monthName(int number){
        String [] weekNames = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        return weekNames[number - 1];
    }

    /**
     * @param s String date "DD/MM/YYYY"
     * @return Convert string DD/MM/YYYY in int[] = {DD,MM,YYYY}
     */
    static public int[] convertToDate(String s, String regex){
        String sp[] = s.split(regex);
        int[] date = new int[sp.length];
        for(int i = 0; i < sp.length; i++){
            date[i] = Integer.valueOf(sp[i]);
        }
        return date;
    }
    static public int[] convertToDate(String s){
        return convertToDate(s,"/");
    }
    static public int[] convertToDate(LocalDate date){
        int[] dateOut = new int[3];
        dateOut[0] = date.getDayOfMonth();
        dateOut[1] = date.getMonthValue();
        dateOut[2] = date.getYear();
        return dateOut;
    }

    static public int[] convertToHour(String s){
        String sp[] = s.split(":");
        int[] hour = new int[sp.length];
        for(int i = 0; i < sp.length; i++){
            hour[i] = Integer.valueOf(sp[i]);
        }
        return hour;
    }
    /**
     * orna list of users by less loaded users on top
     * @param users user list
     * @param month event month
     * @param day event day
     * @return ordered list of users
     */
    static public User[] lessUserLoadedWithEvents(User[] users, int month, int day){
        User[] user = new User[users.length];
        int tam = users.length;
        ScheduleRepository scheduleRepository = ScheduleRepository.getInstance();
        for (int i = 0; i < tam; i++){
            int k = tam-1;
            Schedule schedule1 = scheduleRepository.get(users[i].getIdentity());
            for (int j = 0; j < tam; j++){
                Schedule schedule2 = scheduleRepository.get(users[j].getIdentity());
                if((schedule1.getNumberOfEventInDay(month,day) < schedule2.getNumberOfEventInDay(month,day)) ||
                        (schedule1.getNumberOfEventInDay(month,day) == schedule2.getNumberOfEventInDay(month,day) && i > j)){
                    k-=1;
                }
            }
            user[k] = users[i];
        }
        return user;
    }


    static public int[] dateChanger(int daysToChange, int day, int month, int year){
        int[] newDate = {day + daysToChange, month, year};
        while(newDate[1] > 12 || newDate[1] < 1 ||
                newDate[0] < 1 || newDate[0] > monthSize(newDate[1],newDate[2])){
            if(newDate[1] > 12){
                newDate[1] -= 11;
                newDate[2] += 1;
            }else if(newDate[1] < 1){
                newDate[1] += 12;
                newDate[2] -= 1;
            }else if(newDate[0] < 1){
                newDate[1] -= 1;
                newDate[0] += monthSize(newDate[1],newDate[2]);
            }else if(newDate[0] > monthSize(newDate[1],newDate[2])){
                newDate[1] += 1;
                newDate[0] -= monthSize(newDate[1] - 1, newDate[2]);
            }
        }
        return newDate;
    }

    static public int[] dateChanger(int daysToChange, int[] date){
        return dateChanger(daysToChange,date[0],date[1],date[2]);
    }

    static public int[] getCurrentDate(){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        int[] date = new int[3];
        date[0] = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        date[1] = gregorianCalendar.get(Calendar.MONTH);
        date[2] = gregorianCalendar.get(Calendar.YEAR);
        return date;
    }
    public static int getCurrentYear(){
        return (new GregorianCalendar()).get(Calendar.YEAR);
    }
}
