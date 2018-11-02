import elfo.console.Menu;
import elfo.users.User;
import elfo.users.UserScreen;
import elfo.users.UserTools;


public class UserTest {
    static public void main(String[] argv){
        UserScreen us = UserScreen.getInstace();
        Menu menu = Menu.getMenu();
        menu.setMenuIndex(us.getIndexMenu());
        menu.run(UserTools.getScanner());
    }
}
