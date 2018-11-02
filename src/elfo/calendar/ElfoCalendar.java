package elfo.calendar;

import java.util.ArrayList;


/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
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
     * @return
     */
    private boolean add(ArrayList<Day> obj){
        return array.add(obj);
    }

    /**
     * @param index
     * @return
     */
    public ArrayList<Day> get(int index){
        return array.get(index);
    }
    private void updateCalendar(){
        for(int i = 0; i < 12; i++){
            ArrayList<Day> days = new ArrayList<Day>();
            for(int j = 1; j <= CalendarTools.monthSize(i,year);j++){
                days.add(new Day(j,i,year));
            }
            add(days);
        }
    }
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
    public Day getDay(){
        return this.get(month).get(day-1);
    }
    public Day[] getNextWeekDays(int month, int day){
        Day[] ret = new Day[7];
        for(int i = day ; i < day + 7; i++){
            ret[i - day] = this.getDayOfDate(month,i);
        }
        return ret;
    }
    public Day[] getBeforeDays(int days){
        Day[] ret = new Day[days];
        for(int i = 0; i < 30; i++){
            ret[i] = this.getDayOfDate(month - 1,day + i);
        }
        return ret;
    }
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
    public String getVisualYear(){
        String ret = "";
        for (int i = 0; i < 12; i++){
            ret += getVisualMonth(i);
        }
        ret += String.format("\n");
        return ret;
    }
}
