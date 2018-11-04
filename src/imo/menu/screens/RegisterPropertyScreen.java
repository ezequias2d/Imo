package imo.menu.screens;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserTools;
import imo.property.*;

import java.util.Scanner;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class RegisterPropertyScreen extends MenuList {
    private int menuListIndex;
    private Scanner sc;
    private PropertyControl propertyControl;

    /**
     * Constructor
     */
    public RegisterPropertyScreen(){
        super(Menu.getInstance());
        menuListIndex = menuHome.addMenu(this);
        sc = UserTools.getScanner();
        propertyControl = PropertyControl.getInstance();
        this.addOption("Register Property", this::registerProperty);
    }

    /**
     * MenuCommand
     * Register Property
     * @param menu MenuHome
     */
    private void registerProperty(Menu menu){
        String name;
        double value;
        double area = 0;
        int floors;
        sc.nextLine();
        System.out.printf("Name>");
        name = sc.nextLine();
        System.out.printf("Enter Number of Floors>");
        floors = sc.nextInt();
        System.out.printf("Enter Price>");
        value = sc.nextDouble();

        Property property = propertyControl.createNewProperty(area,value);
        property.setName(name);

        for(int i = 0; i < floors; i++){//floor
            Floor floor = createFloor(property);
            area += floor.getArea();
            property.getArea().increase(floor.getArea());
        }
    }

    /**
     * Create a Floor
     * @param property Property that will own the floor
     * @return A new Floor
     */
    private Floor createFloor(Property property){
        Floor floor = property.createFloor();
        int roomToAdd = 0;
        for(int i = 0; i < Room.TYPE_NAME.length; i++){
            System.out.printf("%d - %s\n",i,Room.TYPE_NAME[i]);
        }
        while (roomToAdd != -1){
            System.out.printf("Enter room type(-1 to exist)>");
            roomToAdd = sc.nextInt();
            if(roomToAdd == -1){
                continue;
            }
            Room room = new Room(roomToAdd,0);
            System.out.printf("Area!\n0 - Set area\n1 - Set dimensions");
            int areaType = sc.nextInt();
            if(areaType == 0){
                System.out.printf("Area>");
                room.setArea(sc.nextDouble());
            }else if(areaType == 1){
                System.out.printf("x>");
                double x = sc.nextDouble();
                System.out.printf("y>");
                double y = sc.nextDouble();
                room.setArea(x,y);
            }else{
                continue;
            }
            floor.add(room);
            System.out.printf(room.getArea() + "\n");
        }
        return floor;
    }

    /**
     * @return Index of MenuList
     */
    public int getMenuListIndex(){
        return menuListIndex;
    }
}
