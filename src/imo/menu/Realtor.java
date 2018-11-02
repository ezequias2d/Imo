package imo.menu;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserControl;
import elfo.users.UserScreen;
import elfo.users.UserTools;
import imo.menu.actions.CalendarScreen;
import imo.menu.actions.AccountScreen;
import imo.menu.actions.RegisterPropertyScreen;
import imo.menu.actions.SearchScreen;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class Realtor{
    UserControl userControl;
    UserScreen userScreen;
    SearchScreen searchScreen;
    CalendarScreen calendarScreen;
    AccountScreen accountScreen;
    RegisterPropertyScreen registerPropertyScreen;
    Menu menu;
    MenuList menuList;
    int menuListIndex;
    String identifier;
    static private Realtor realtor;
    Realtor(){
        userControl = UserControl.getUserControl();
        userScreen = UserScreen.getInstace();
        menu = Menu.getMenu();
        searchScreen = new SearchScreen();
        calendarScreen = new CalendarScreen();
        accountScreen = new AccountScreen();
        registerPropertyScreen = new RegisterPropertyScreen();
        accountScreen.genericChangePasswordScreen();
        menuListIndex = menu.creatMenu();
        menuList = menu.getMenuList(menuListIndex);

        menuList.addOption("Search", searchScreen.getMenuListIndex());
        menuList.addOption("Calendar", calendarScreen.getMenuListIndex());
        menuList.addOption("Register Property",registerPropertyScreen.getMenuListIndex());
        menuList.addOption("Account", accountScreen.getMenuListIndex());
        menuList.addOption("Logout",this::logout);

        identifier = UserTools.convertCpfToString(userControl.getCpfCurrent());
    }
    static public Realtor getInstace(){
        if(realtor == null || realtor.identifier != UserTools.convertCpfToString(realtor.userControl.getCpfCurrent())){
            realtor = new Realtor();
        }
        return realtor;
    }
    public int getMenuListIndex(){
        return menuListIndex;
    }
    private void logout(Menu menu){
        userControl.logout();
        menu.setMenuIndex(userScreen.getIndexMenu());
    }
}
