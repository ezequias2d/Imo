package imo.menu.actions;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserControl;
import elfo.users.UserTools;
import elfo.number.Number;
import imo.property.*;

import java.util.Scanner;

public class RegisterPropertyScreen {
    private Menu menu;
    private int menuListIndex;
    private MenuList menuList;
    private Scanner sc;
    private UserControl userControl;
    private PropertyControl propertyControl;
    public RegisterPropertyScreen(){
        menu = Menu.getMenu();
        menuListIndex = menu.creatMenu();
        menuList = menu.getMenuList(menuListIndex);
        sc = UserTools.getScanner();
        userControl = UserControl.getUserControl();
        propertyControl = PropertyControl.getPropertyControl();

        menuList.addOption("Register Property", this::registerProperty);
    }
    private void registerProperty(Menu menu){
        String name;
        Number value;
        double area = 0;
        int floors;
        sc.nextLine();
        System.out.printf("Name>");
        name = sc.nextLine();
        System.out.printf("Enter Number of Floors>");
        floors = sc.nextInt();
        System.out.printf("Enter Price>");
        value = new Number(sc.nextDouble());

        Property property = propertyControl.createNewProperty(area,value);
        property.setName(name);

        for(int i = 0; i < floors; i++){//floor
            Floor floor = createFloor(property);
            area += floor.getArea();
            property.getArea().increase(floor.getArea());
        }
    }
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
    public int getMenuListIndex(){
        return menuListIndex;
    }
}
