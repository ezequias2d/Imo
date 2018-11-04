package elfo.calendar;

import java.util.ArrayList;


/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class ElfoCalendar {
    private ArrayList<ArrayList<Day>> array;
    private int year;
    private int month;
    private int day;

    /**
     * @param year Year
     * @param month Month
     * @param day Day
     */
    public ElfoCalendar(int year,int month, int day){
        this.array = new ArrayList<ArrayList<Day>>();
        setDate(year,month,day);
        updateCalendar();
    }

    /**
     * @param year Year
     * @param month Month
     * @param day Day
     * @return Success
     */
    public boolean setDate(int year, int month, int day){
        if(year > 1536 &&
                month > 0 && month <= 12 &&
                day > 0 && day <= CalendarTools.monthSize(month)){
            this.year = year;
            this.month = month;
            this.day = day;
            return true;
        }
        return false;
    }

    /**
     * @param obj Day
     * @return if added
     */
    private boolean add(ArrayList<Day> obj){
        return array.add(obj);
    }

    /**
     * @param index Month
     * @return ArraList of 'Day's
     */
    public ArrayList<Day> get(int index){
        return array.get(index);
    }
    /**
        Update Calendar
     */
    private void updateCalendar(){
        for(int i = 0; i < 12; i++){
            ArrayList<Day> days = new ArrayList<Day>();
            for(int j = 1; j <= CalendarTools.monthSize(i,year);j++){
                days.add(new Day(j,i,year));
            }
            add(days);
        }
    }

    /**
     * @param month Month
     * @param day Day
     * @return Day of date
     */
    public Day getDayOfDate(int month, int day){
        while (day > CalendarTools.monthSize(month) - 1 || month > 11 || day < 0 || month < 0){
            if(day > CalendarTools.monthSize(month) - 1){
                day -= CalendarTools.monthSize(month);
                month += 1;
            }else if(day <= 0){
                day = CalendarTools.monthSize(month) - 1;
                month -= 1;
            }
            if(month > 11){
                year += 1;
                month -= 11;
            }else if(month < 0){
                year -= 1;
                month = 11;
            }
        }
        return this.get(month).get(day);
    }

    /**
     * @return Current day
     */
    public Day getDay(){
        return this.get(month).get(day-1);
    }

    /**
     * @param month Month
     * @param day Day
     * @return Returns Day[7] with next seven days
     */
    public Day[] getNextWeekDays(int month, int day){
        Day[] ret = new Day[7];
        for(int i = day ; i < day + 7; i++){
            ret[i - day] = this.getDayOfDate(month,i);
        }
        return ret;
    }

    /**
     * @param n n Days
     * @return Returns Day[n] with before 'n' days
     */
    public Day[] getBeforeDays(int n){
        Day[] ret = new Day[n];
        for(int i = 0; i < n; i++){
            ret[i] = this.getDayOfDate(month - 1,day + i);
        }
        return ret;
    }

    /**
     * @param month Month
     * @return Returns calendar mounted in standard form
     */
    public String getVisualMonth(int month){
        String ret = "";
        int con = -1;
        if(month >= 0 && month < 12){
            int initialDay = this.get(month).get(0).getWeekDay() + con;
            ret += String.format("\n%s\n",CalendarTools.monthName(month));
            ArrayList<Day> days = this.get(month);
            for(int w = 0; w < initialDay; w++){
                ret += "    ";
            }
            for(int j = 0; j < days.size(); j++){
                Day day = days.get(j);
                ret += String.format("%s",day.getDay());
                if(day.getDay() == this.day){
                    ret += "| ";
                }else if(day.getDay() == this.day - 1) {
                    ret += " |";
                }else if(day.getEvents().size() > 0){
                    ret += "<-";
                }else{
                    ret += "  ";
                }
                if(day.getDay() < 10){
                    ret += " ";
                }
                int weekDay = day.getWeekDay() + con;
                while(weekDay < 0){
                    weekDay += 7;
                }
                if(weekDay == 6){
                    ret += String.format("\n");
                }
            }
        }
        return ret;
    }

    /**
     * @return Returns year mounted in standard form
     */
    public String getVisualYear(){
        String ret = "";
        for (int i = 0; i < 12; i++){
            ret += getVisualMonth(i);
        }
        ret += String.format("\n");
        return ret;
    }
}
