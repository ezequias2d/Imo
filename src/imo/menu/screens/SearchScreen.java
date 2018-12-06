package imo.menu.screens;
import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.number.DeltaNumber;
import elfo.users.UserTools;
import imo.menu.actions.MakeEventAction;
import imo.property.PropertyRepository;
import imo.property.Property;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class SearchScreen extends MenuList {

    private PropertyRepository propertyRepository;
    private Scanner sc;

    private int menuListIndex;



    private DeltaNumber moneyLimit;
    private DeltaNumber areaLimit;
    private DeltaNumber floorsLimit;
    private DeltaNumber roomsLimit;

    /**
     * Constructor
     */
    public SearchScreen(){
        super(Menu.getInstance());
        int menuListIndexFilter;
        MenuList menuListFilter;

        moneyLimit = new DeltaNumber();
        areaLimit = new DeltaNumber();
        floorsLimit = new DeltaNumber();
        roomsLimit = new DeltaNumber();
        MakeEventAction makeEventAction = new MakeEventAction();

        sc = UserTools.getScanner();
        propertyRepository = PropertyRepository.getInstance();

        menuListIndex = menuHome.addMenu(this);

        menuListIndexFilter = menuHome.createMenu();
        menuListFilter = menuHome.getMenuList(menuListIndexFilter);

        menuListFilter.addOption("Money",this::OptionMoneyFilter);
        menuListFilter.addOption("Area",this::OptionAreaFilter);
        menuListFilter.addOption("Floors",this::OptionFloorsFilter);
        menuListFilter.addOption("Rooms",this::OptionRoomsFilter);

        this.addOption("Filter",menuListIndexFilter);
        this.addOption("Show Search",this::ShowSearch);
        this.addOption("Make event", makeEventAction::makeEvent);

        menuHome.setMenuIndex(menuListIndex);
    }

    /**
     *@return Index of MenuList in Menu
     */
    public int getMenuListIndex(){
        return menuListIndex;
    }

    /**
     * MenuCommand
     * Show Search
     * @param menu MenuHome
     */
    private void ShowSearch(Menu menu){
        ArrayList<Property> filterList = propertyRepository.filterProperties(moneyLimit,areaLimit,floorsLimit,roomsLimit);
        String out = "";
        for(int i = 0; i < filterList.size(); i++){
            Property property = filterList.get(i);
            out += property + String.format("\nCode:%d\n\n\n",i);
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

    /**
     * Loads limit on a delta number
     * @param dn DeltaNumber to load
     */
    private void loadLimit(DeltaNumber dn){
        System.out.printf("\nMin:");
        dn.setMin(sc.nextDouble());
        System.out.printf("\nMax:");
        dn.setMax(sc.nextDouble());
    }
}
