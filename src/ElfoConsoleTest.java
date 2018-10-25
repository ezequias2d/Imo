import elfo.calendar.Day;
import elfo.calendar.DeltaTime;
import elfo.calendar.Schedule;
import elfo.console.Menu;
public class ElfoConsoleTest {
    static public void main(String[] argv){
        ElfoConsoleTest elfoConsoleTest = new ElfoConsoleTest();
        Schedule schedule = Schedule.getSchedule();
        schedule.createNewEvent("Evento teste",9,25,5,20,new DeltaTime(1,1));
        schedule.createNewEvent("Evento teste2",9,25,5,50,new DeltaTime(1,1));
        schedule.createNewEvent("Evento teste2",9,25,5,50,new DeltaTime(1,1));
        Day[] days = schedule.getElfoCalendar().getNextWeekDays(9,24);
        for(int i = 0; i < days.length; i++){
            System.out.printf("%s",days[i].getVisualEvents());
        }
        schedule.seeThisMonth();
       /* Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        int m1 = menu.creatMenu();
        int m2 = menu.creatMenu();
        menu.menuLists.get(m1).addOption("M2", m2);
        menu.menuLists.get(m1).addOption("Exit", ElfoConsoleTest::exit);
        menu.menuLists.get(m2).addOption("M1", m1);
        menu.menuLists.get(m2).addOption("Exit", ElfoConsoleTest::exit);
        while (true){
            menu.run(sc);
        }*/

    }
    static void exit(Menu m){
        System.exit(0);
    }
}
