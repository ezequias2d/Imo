package imo.menu.actions;
import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.number.DeltaNumber;
import elfo.users.UserTools;
import imo.property.PropertyControl;
import imo.property.Property;
import imo.property.PropertyView;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchScreen {

    private PropertyControl propertyControl;
    private PropertyView propertyView;
    private Menu menu;
    private Scanner sc;

    private int menuListIndex;
    private MenuList menuList;

    private int menuListIndexFilter;
    private MenuList menuListFilter;

    private DeltaNumber moneyLimit;
    private DeltaNumber areaLimit;
    private DeltaNumber floorsLimit;
    private DeltaNumber roomsLimit;

    private MakeEventScreen makeEventScreen;

    public SearchScreen(){
        moneyLimit = new DeltaNumber();
        areaLimit = new DeltaNumber();
        floorsLimit = new DeltaNumber();
        roomsLimit = new DeltaNumber();
        makeEventScreen = new MakeEventScreen();

        sc = UserTools.getScanner();
        propertyControl = PropertyControl.getPropertyControl();
        propertyView = PropertyView.getInstance();
        menu = Menu.getMenu();

        menuListIndex = menu.creatMenu();
        menuList = menu.getMenuList(menuListIndex);

        menuListIndexFilter = menu.creatMenu();
        menuListFilter = menu.getMenuList(menuListIndexFilter);

        menuListFilter.addOption("Money",this::OptionMoneyFilter);
        menuListFilter.addOption("Area",this::OptionAreaFilter);
        menuListFilter.addOption("Floors",this::OptionFloorsFilter);
        menuListFilter.addOption("Rooms",this::OptionRoomsFilter);

        menuList.addOption("Filter",menuListIndexFilter);
        menuList.addOption("Show Search",this::ShowSearch);
        menuList.addOption("Make event", makeEventScreen::makeEvent);

        menu.setMenuIndex(menuListIndex);
    }

    public int getMenuListIndex(){
        return menuListIndex;
    }

    private void ShowSearch(Menu menu){
        ArrayList<Property> filterList = propertyControl.filterProperties(moneyLimit,areaLimit,floorsLimit,roomsLimit);
        String out = "";
        for(int i = 0; i < filterList.size(); i++){
            Property property = filterList.get(i);
            out += propertyView.getViewOfProperty(property) + String.format("\nCode:%d\n\n\n",i);
        }
        System.out.printf(out);
    }

    private void OptionMoneyFilter(Menu menu){
        loadLimit(moneyLimit);
    }
    private void OptionAreaFilter(Menu menu){
        loadLimit(areaLimit);
    }
    private void OptionFloorsFilter(Menu menu){
        loadLimit(floorsLimit);
    }
    private void OptionRoomsFilter(Menu menu){
        loadLimit(roomsLimit);
    }
    private void loadLimit(DeltaNumber dn){
        System.out.printf("\nMin:");
        dn.setMin(sc.nextDouble());
        System.out.printf("\nMax:");
        dn.setMax(sc.nextDouble());
    }
}
