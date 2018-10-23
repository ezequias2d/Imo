import elfo.calendar.Schedule;
import elfo.console.Menu;
public class ElfoConsoleTest {
    static public void main(String[] argv){
        ElfoConsoleTest elfoConsoleTest = new ElfoConsoleTest();
        Schedule schedule = Schedule.getSchedule();
        schedule.seeCalendar();
        schedule.changeYear(1);
        System.out.println("oii");
        schedule.seeCalendar();
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
