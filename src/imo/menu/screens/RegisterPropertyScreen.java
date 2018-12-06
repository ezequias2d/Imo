package imo.menu.screens;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserTools;
import imo.exception.ParameterOutOfTypeException;
import imo.property.*;

import java.util.Scanner;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class RegisterPropertyScreen extends MenuList {
    private int menuListIndex;
    private Scanner sc;
    private PropertyRepository propertyRepository;

    /**
     * Constructor
     */
    public RegisterPropertyScreen(){
        super(Menu.getInstance());
        menuListIndex = menuHome.addMenu(this);
        sc = UserTools.getScanner();
        propertyRepository = PropertyRepository.getInstance();
        this.addOption("Register Property", this::registerProperty);
        this.addOption("Delete Property", this::deleteProperty);
    }

    /**
     * @param menu MenuHome
     */
    private void deleteProperty(Menu menu){
        System.out.printf("Property Code>");
        int code = sc.nextInt();
        Property property = propertyRepository.getProperty(code);
        propertyRepository.removeProperty(property);
        System.out.printf("Removed Property!");
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
        System.out.printf("Enter number of Floors>");
        floors = sc.nextInt();
        System.out.printf("Enter Price>");
        value = sc.nextDouble();
        try {
            PropertyType propertyType = PropertyTypeRepository.getInstace().get("House"); //tipo generico de casa
            Property property = propertyRepository.createNewProperty(area,value,0,propertyType);
            property.setName(name);
            property.setFloors(floors);
            createRoom(property);
        }catch (ParameterOutOfTypeException parameterOutOfTypeException) {
            System.out.println(parameterOutOfTypeException.getMessage());
        }

    }

    /**
     * Create a Floor
     * @param property Property that will own the floor
     * @return A new Floor
     */
    private void createRoom(Property property){
        int roomToAdd = 0;
        for(int i = 0; i < Room.values().length; i ++){
            System.out.printf("%d - %s\n",i,Room.values()[i].getType());
        }
        while (roomToAdd != -1){
            System.out.print("Enter room type(-1 to exist)>");
            roomToAdd = sc.nextInt();
            if(roomToAdd == -1){
                break;
            }
            Room room = Room.values()[roomToAdd];
            System.out.print("Area!\n0 - Set area\n1 - Set dimensions");
            int areaType = sc.nextInt();
            if(areaType == 0){
                System.out.print("Area>");
                room.setArea(sc.nextDouble());
            }else if(areaType == 1){
                System.out.print("x>");
                double x = sc.nextDouble();
                System.out.print("y>");
                double y = sc.nextDouble();
                room.setArea(x,y);
            }else{
                continue;
            }
            property.addRoom(room);
            System.out.print(room.getArea() + "\n");
        }
    }

    /**
     * @return Index of MenuList
     */
    public int getMenuListIndex(){
        return menuListIndex;
    }
}
