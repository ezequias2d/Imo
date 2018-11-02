package imo.menu;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserControl;
import elfo.users.UserScreen;
import elfo.users.UserTools;
import imo.menu.actions.AccountScreen;
import imo.menu.actions.SaleScreen;
import imo.menu.actions.SearchScreen;
import imo.menu.actions.CalendarScreen;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class Client {
    UserControl userControl;
    UserScreen userScreen;
    SearchScreen searchScreen;
    CalendarScreen calendarScreen;
    SaleScreen saleScreen;
    AccountScreen accountScreen;
    Menu menu;
    MenuList menuList;
    int menuListIndex;
    String identifier;
    static private Client client;
    Client(){
        userControl = UserControl.getUserControl();
        userScreen = UserScreen.getInstace();
        menu = Menu.getMenu();
        searchScreen = new SearchScreen();
        calendarScreen = new CalendarScreen();
        saleScreen = new SaleScreen();
        accountScreen = new AccountScreen();
        accountScreen.genericChangePasswordScreen();
        saleScreen.saleScreenClient();
        menuListIndex = menu.creatMenu();
        menuList = menu.getMenuList(menuListIndex);

        menuList.addOption("Search", searchScreen.getMenuListIndex());
        menuList.addOption("Calendar", calendarScreen.getMenuListIndex());
        menuList.addOption("Sales", saleScreen.getMenuListIndex());
        menuList.addOption("Account", accountScreen.getMenuListIndex());
        menuList.addOption("Logout",this::logout);

        identifier = UserTools.convertCpfToString(userControl.getCpfCurrent());
    }
    static public Client getInstace(){
        if(client == null || client.identifier != UserTools.convertCpfToString(client.userControl.getCpfCurrent())){
            client = new Client();
        }
        return client;
    }
    public int getMenuListIndex(){
        return menuListIndex;
    }
    private void logout(Menu menu){
        userControl.logout();
        menu.setMenuIndex(userScreen.getIndexMenu());
    }
}
