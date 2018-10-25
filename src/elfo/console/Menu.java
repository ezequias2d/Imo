package elfo.console;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public static Menu menu;
    public int current = 0;
    public ArrayList<MenuList> menuLists;
    private Menu(){
        menuLists = new ArrayList<MenuList>();
    }

    static public Menu getMenu(){
        if(menu == null){
            menu = new Menu();
        }
        return menu;
    }
    public int getCurrentMenuIndex(){
        return current;
    }
    public boolean setMenuIndex(int index){
        if(index > menuLists.size() - 1){
            return false;
        }else{
            current = index;
            return true;
        }
    }
    public MenuList getMenuList(int index){
        return menuLists.get(index);
    }
    public int creatMenu(){
        MenuList m = new MenuList(this);
        menuLists.add(m);
        return menuLists.size() - 1;
    }
    public void run(Scanner sc){
        for(int i = 0; i < menuLists.size(); i++){
            MenuList m = menuLists.get(current);
            m.seeMenu();
            int f = sc.nextInt();
            m.enter(f);
        }
    }
}
