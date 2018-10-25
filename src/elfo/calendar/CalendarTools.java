package elfo.calendar;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    static public int weekDay(int day,int month,int year){
        cgCreat();
        cg.set(Calendar.DAY_OF_MONTH,day);
        cg.set(Calendar.MONTH,month);
        cg.set(Calendar.YEAR,year);
        return cg.get(Calendar.DAY_OF_WEEK);
    }

    static public int daysOfWeek(int week,int month,int year){
        cgCreat();
        cg.set(Calendar.MONTH,month);
        cg.set(Calendar.YEAR,year);
        cg.set(Calendar.WEEK_OF_MONTH,week+1);
        cg.set(Calendar.DAY_OF_WEEK, -1);
        return cg.get(Calendar.DAY_OF_WEEK);
    }
    static public int monthSize(int number,int year){
        cgCreat();
        cg.set(Calendar.YEAR,year);
        cg.set(Calendar.MONTH, number + 1);
        cg.set(Calendar.DAY_OF_MONTH, -1);
        return cg.get(Calendar.DAY_OF_MONTH) + 1;
    }
    static public int monthSize(int number){
        cgCreat();
        cg.set(Calendar.MONTH, number + 1);
        cg.set(Calendar.DAY_OF_MONTH, -1);
        return cg.get(Calendar.DAY_OF_MONTH) + 1;
    }
    static public int weekMonthSize(int number, int year){
        cgCreat();
        monthSize(number,year);
        return cg.get(Calendar.WEEK_OF_MONTH);
    }
    static public int dayYearSize(int year){
        cgCreat();
        int days = 0;
        for(int i = 0; i < 12; i++){
            days += monthSize(i,year);
        }
        return days;
    }
    static public String formatOfTime(int hour, int minute){
        dcCreat();
        dc.applyPattern("##00");
        return "("+hour + ":" + dc.format(minute)+")";
    }
    static public String monthName(int number){
        String [] weekNames = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        return weekNames[number];
    }
}
