package elfo.calendar;

import java.util.ArrayList;

public class ElfoCalendar extends ArrayList<ArrayList<Day>> {

    public ElfoCalendar(int year){
        for(int i = 0; i < 12; i++){
            ArrayList<Day> days = new ArrayList<Day>();
            for(int j = 1; j <= CalendarTools.monthSize(i,year);j++){
                days.add(new Day(j,i,year));
            }
            add(days);
        }
    }
    public void seeCalendar(){
        int con =  -1;
        for (int i = 0; i < 12; i++){
            int initialDay = this.get(i).get(0).getWeekDay() + con;
            System.out.printf("\n%s\n",CalendarTools.monthName(i));
            ArrayList<Day> weeks = this.get(i);
            for(int w = 0; w < initialDay; w++){
                System.out.printf("   ");
            }
            for(int j = 0; j < weeks.size(); j++){
                Day day = weeks.get(j);
                System.out.printf("%s ",day.getDay());
                if(day.getDay() < 10){
                    System.out.printf(" ");
                }
                int weekDay = day.getWeekDay() + con;
                while(weekDay < 0){
                    weekDay += 7;
                }
                if(weekDay == 6){
                    System.out.printf("\n");
                }
            }
        }
        System.out.printf("\n");
    }
}
