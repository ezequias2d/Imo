package imo.menu;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserControl;
import elfo.users.UserScreen;
import elfo.users.UserTools;
import imo.menu.actions.*;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class RealEstateManager {
    UserControl userControl;
    UserScreen userScreen;
    SearchScreen searchScreen;
    CalendarScreen calendarScreen;
    AccountScreen accountScreen;
    SaleScreen saleScreen;
    RegisterPropertyScreen registerPropertyScreen;
    Menu menu;
    MenuList menuList;
    int menuListIndex;
    String identifier;
    static private RealEstateManager realEstateManager;
    private RealEstateManager(){
        userControl = UserControl.getUserControl();
        userScreen = UserScreen.getInstace();
        menu = Menu.getMenu();
        searchScreen = new SearchScreen();
        calendarScreen = new CalendarScreen();
        saleScreen = new SaleScreen();
        accountScreen = new AccountScreen();
        registerPropertyScreen = new RegisterPropertyScreen();
        accountScreen.ADM1ChangePasswordScreen();
        saleScreen.saleScreenRealEstateManager();
        menuListIndex = menu.creatMenu();
        menuList = menu.getMenuList(menuListIndex);

        menuList.addOption("Search", searchScreen.getMenuListIndex());
        menuList.addOption("Calendar", calendarScreen.getMenuListIndex());
        menuList.addOption("Register", UserScreen.getInstace()::create);
        menuList.addOption("Sales", saleScreen.getMenuListIndex());
        menuList.addOption("Register Property",registerPropertyScreen.getMenuListIndex());
        menuList.addOption("Account", accountScreen.getMenuListIndex());
        menuList.addOption("Logout",this::logout);

        identifier = UserTools.convertCpfToString(userControl.getCpfCurrent());
    }
    static public RealEstateManager getInstace(){
        if(realEstateManager == null || realEstateManager.identifier != UserTools.convertCpfToString(realEstateManager.userControl.getCpfCurrent())){
            realEstateManager = new RealEstateManager();
        }
        return realEstateManager;
    }
    public int getMenuListIndex(){
        return menuListIndex;
    }
    private void logout(Menu menu){
        userControl.logout();
        menu.setMenuIndex(userScreen.getIndexMenu());
    }
}
