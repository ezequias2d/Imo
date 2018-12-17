package elfoAPI.calendar;

import elfoAPI.calendar.schedule.Schedule;
import elfoAPI.calendar.schedule.ScheduleRepository;
import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.users.User;

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

    /*
     * Cria objeto estatico "Gregorian Calendar"
     */
    static private void gregorianCalendarCreat(){
        if(gregorianCalendar == null){
            gregorianCalendar = new GregorianCalendar();
        }
    }
    /*
        Cria objeto estatico "Decimal Format"
     */
    static private void decimalFormatCreat(){
        if(decimalFormat == null){
            decimalFormat = new DecimalFormat();
        }
    }

    /**
     * Pega 'dia da semana'(de 0 a 6) de uma data.
     * Exemplo:
     *      15/12/2018 e um sabado
     *      Retorna 6(ultimo dia da semana)
     *
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
     * Pega tamanho de um mes em dias
     * Exemplo:
     *      12/18 - Retorna 31
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
     * Pega tamanho de um mes em dias do ano atual
     * Exemplo:
     *      12(ano 2018) - Retorna 31
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
     * Formata array de inteiros(data) em String.
     * Exemplo:
     *      int[] date = {15, 12, 2018};
     *      formatDate(date) - RETORNA - "15/12/2018"
     * @param date
     * @return
     */
    static public String formatDate(int[] date){
        return date[0] + "/" + date[1] + "/" + date[2];
    }

    /**
     * Formata hora em String
     * Exemplo:
     *      formatOfTime(7, 30) - RETORNA - "7:30"
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
     * Pega abreviaçao do nome de um mes
     * Exemplo:
     *      monthName(3) - RETORNA - "Mar"(Março)
     * @param number ElfoNumber
     * @return month name
     */
    static public String monthName(int number){
        String [] weekNames = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        return weekNames[number - 1];
    }

    /**
     * Convete um LocalDate em um array com a data.
     * Exemplo:
     *      LocalDate localDate = 15-12-2018
     *      convertToDate(localDate) - RETORNA - new int[]{15, 12, 2018}
     * @param date
     * @return
     */
    static public int[] convertToDate(LocalDate date){
        int[] dateOut = new int[3];
        dateOut[0] = date.getDayOfMonth();
        dateOut[1] = date.getMonthValue();
        dateOut[2] = date.getYear();
        return dateOut;
    }

    /**
     * Pega um String no formato "0:00" e retorna um vetor com a hora
     * Exemplo:
     *      convertToHour("7:30") - RETORNA - new int[]{7, 30};
     * @param s
     * @return
     */
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
    static public User[] lessUserLoadedWithEvents(User[] users, int month, int day) throws DataCannotBeAccessedException {
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

    /**
     * Muda uma data em 'n' dias
     * Exemplo:
     *      n = 15
     *      dateChanger(n, 28, 12, 2018) - RETORNA - new int[]{12, 1, 2019}
     * @param daysToChange Days to Change
     * @param day Date day
     * @param month Date month
     * @param year Date year
     * @return new Date
     */
    static public int[] dateChanger(int daysToChange, int day, int month, int year){
        int[] newDate = {day + daysToChange, month, year};
        while(newDate[1] > 12 || newDate[1] < 1 ||
                newDate[0] < 1 || newDate[0] > monthSize(newDate[1],newDate[2])){
            if(newDate[1] > 12){
                newDate[1] -= 12;
                newDate[2] += 1;
            }else if(newDate[1] < 1){
                newDate[1] += 12;
                newDate[2] -= 1;
            }else if(newDate[0] < 1){
                newDate[1] -= 1;
                newDate[0] += monthSize(newDate[1],newDate[2]);
            }else if(newDate[0] > monthSize(newDate[1],newDate[2])){
                newDate[0] -= monthSize(newDate[1], newDate[2]);
                newDate[1] += 1;
            }
        }
        return newDate;
    }

    /**
     * Muda uma data em 'n' dias
     * Exemplo:
     *      n = 15
     *      dateChanger(n, int[]{28, 12, 2018}) - RETORNA - new int[]{12, 1, 2019}
     *
     *
     * @param daysToChange Days to Change
     * @param date  Date in array
     * @return new date
     */
    static public int[] dateChanger(int daysToChange, int[] date){
        return dateChanger(daysToChange,date[0],date[1],date[2]);
    }

    /**
     * Pega data atual em array
     * @return int{day, month, year}
     */
    static public int[] getCurrentDate(){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        int[] date = new int[3];
        date[0] = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        date[1] = gregorianCalendar.get(Calendar.MONTH) + 1;
        date[2] = gregorianCalendar.get(Calendar.YEAR);
        return date;
    }

    /**
     * Pega ano atual
     * @return Year
     */
    public static int getCurrentYear(){
        return (new GregorianCalendar()).get(Calendar.YEAR);
    }
}
