import elfo.console.Menu;
import elfo.users.User;
import elfo.users.UserScreen;
import elfo.users.UserTools;
import javafx.scene.input.Mnemonic;

public class UserTest {
    static public void main(String[] argv){
        UserScreen us = new UserScreen();
        Menu menu = Menu.getMenu();
        menu.setMenuIndex(us.getIndexMenu());
        menu.run(UserTools.getScanner());
    }
}
