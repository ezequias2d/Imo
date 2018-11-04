package imo.menu;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserControl;
import elfo.users.UserScreen;
import elfo.users.UserTools;
import imo.menu.screens.CalendarScreen;
import imo.menu.screens.AccountScreen;
import imo.menu.screens.RegisterPropertyScreen;
import imo.menu.screens.SearchScreen;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class Realtor extends MenuList{
    private UserControl userControl;
    private UserScreen userScreen;
    private int menuListIndex;
    private String identifier;
    static private Realtor realtor;

    /**
     * Constructor
     */
    private Realtor(){
        super(Menu.getInstance());
        userControl = UserControl.getInstance();
        userScreen = UserScreen.getInstace();

        SearchScreen searchScreen = new SearchScreen();
        CalendarScreen calendarScreen = new CalendarScreen();
        AccountScreen accountScreen = new AccountScreen();
        RegisterPropertyScreen registerPropertyScreen = new RegisterPropertyScreen();

        menuListIndex = menuHome.addMenu(this);

        this.addOption("Search", searchScreen.getMenuListIndex());
        this.addOption("Calendar", calendarScreen.getMenuListIndex());
        this.addOption("Register Property",registerPropertyScreen.getMenuListIndex());
        this.addOption("Account", accountScreen.getMenuListIndex());
        this.addOption("Logout",this::logout);

        identifier = UserTools.convertCpfToString(userControl.getCpfCurrent());
    }

    /**
     * @return Instace of Realtor
     */
    static public Realtor getInstace(){
        if(realtor == null || realtor.identifier != UserTools.convertCpfToString(realtor.userControl.getCpfCurrent())){
            realtor = new Realtor();
        }
        return realtor;
    }

    /**
     * @return Index of MenuList in Menu
     */
    public int getMenuListIndex(){
        return menuListIndex;
    }

    /**
     * MenuCommand
     * Logout
     * @param menu MenuHome
     */
    private void logout(Menu menu){
        userControl.logout();
        menu.setMenuIndex(userScreen.getIndexMenu());
    }
}
