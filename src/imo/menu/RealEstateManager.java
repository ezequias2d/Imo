package imo.menu;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserControl;
import elfo.users.UserScreen;
import elfo.users.UserTools;
import imo.menu.screens.*;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class RealEstateManager extends MenuList{
    private UserControl userControl;
    private UserScreen userScreen;
    private int menuListIndex;
    private String identifier;
    static private RealEstateManager realEstateManager;

    /**
     * Constructor
     */
    private RealEstateManager(){
        super(Menu.getInstance());
        userControl = UserControl.getInstance();
        userScreen = UserScreen.getInstace();

        SearchScreen searchScreen = new SearchScreen();
        CalendarScreen calendarScreen = new CalendarScreen();
        AccountScreen accountScreen = new AccountScreen();
        SaleScreen saleScreen = new SaleScreen();
        RegisterPropertyScreen registerPropertyScreen = new RegisterPropertyScreen();

        accountScreen.ADM1ChangePasswordScreen();

        saleScreen.saleScreenRealEstateManager();

        menuListIndex = menuHome.addMenu(this);

        this.addOption("Search", searchScreen.getMenuListIndex());
        this.addOption("Calendar", calendarScreen.getMenuListIndex());
        this.addOption("Register", UserScreen.getInstace()::create);
        this.addOption("Sales", saleScreen.getMenuListIndex());
        this.addOption("Register Property",registerPropertyScreen.getMenuListIndex());
        this.addOption("Account", accountScreen.getMenuListIndex());
        this.addOption("Logout",this::logout);

        identifier = UserTools.convertCpfToString(userControl.getCpfCurrent());
    }

    /**
     * @return Instace of RealEstateManager
     */
    static public RealEstateManager getInstace(){
        if(realEstateManager == null || realEstateManager.identifier != UserTools.convertCpfToString(realEstateManager.userControl.getCpfCurrent())){
            realEstateManager = new RealEstateManager();
        }
        return realEstateManager;
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
        userScreen.logout(menu);
        menu.setMenuIndex(userScreen.getIndexMenu());
    }
}
