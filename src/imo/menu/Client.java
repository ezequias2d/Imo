package imo.menu;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserController;
import elfo.users.UserScreen;
import elfo.users.UserTools;
import imo.menu.screens.AccountScreen;
import imo.menu.screens.SaleScreen;
import imo.menu.screens.SearchScreen;
import imo.menu.screens.CalendarScreen;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.4
 */
public class Client extends MenuList {
    private UserController userController;
    private UserScreen userScreen;
    private int menuListIndex;
    private String identifier;
    static private Client client;

    /**
     * Constructor
     */
    private Client(){
        super(Menu.getInstance());

        SearchScreen searchScreen = new SearchScreen();
        CalendarScreen calendarScreen = new CalendarScreen();
        SaleScreen saleScreen = new SaleScreen();
        AccountScreen accountScreen = new AccountScreen();

        userController = UserController.getInstance();
        userScreen = UserScreen.getInstace();
        accountScreen.genericChangeInfoScreen();
        saleScreen.saleScreenClient();
        menuListIndex = menuHome.addMenu(this);

        this.addOption("Search", searchScreen.getMenuListIndex());
        this.addOption("Calendar", calendarScreen.getMenuListIndex());
        this.addOption("Sales", saleScreen.getMenuListIndex());
        this.addOption("Account", accountScreen.getMenuListIndex());
        this.addOption("Logout",this::logout);

        identifier = UserTools.convertCpfToString(userController.getCpfCurrent());
    }

    /**
     * @return Instace of Client
     */
    static public Client getInstace(){
        if(client == null || client.identifier != UserTools.convertCpfToString(client.userController.getCpfCurrent())){
            client = new Client();
        }
        return client;
    }

    /**
     * @return Index of Client MenuList in Menu
     */
    public int getMenuListIndex(){
        return menuListIndex;
    }

    /**
     * Logout
     * @param menu MenuHome
     */
    private void logout(Menu menu){
        userScreen.logout(menu);
        menu.setMenuIndex(userScreen.getIndexMenu());
    }
}
