package elfo.console;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class Menu {
    private static Menu menu;
    int current = 0;
    private ArrayList<MenuList> menuLists;
    
    private Menu(){
        menuLists = new ArrayList<MenuList>();
    }

    /**
     * @return Menu Instance
     */
    static public Menu getInstance(){
        if(menu == null){
            menu = new Menu();
        }
        return menu;
    }

    /**
     * @return current screen
     */
    public int getCurrentMenuIndex(){
        return current;
    }

    /**
     * @param index Index of MenuList
     * @return if there is
     */
    public boolean setMenuIndex(int index){
        if(index > menuLists.size() - 1){
            return false;
        }else{
            current = index;
            return true;
        }
    }

    /**
     * @param index Index
     * @return MenuList of Index
     */
    public MenuList getMenuList(int index){
        return menuLists.get(index);
    }

    /**
     * Create a new MenuList
     * @return Index
     */
    public int createMenu(){
        MenuList m = new MenuList(this);
        menuLists.add(m);
        return menuLists.size() - 1;
    }

    /**
     * Add a MenuList
     * @param ml MenuList to add
     * @return Index
     */
    public int addMenu(MenuList ml){
        menuLists.add(ml);
        return menuLists.size() - 1;
    }

    /**
     * Run Menu;
     * @param sc Scanner
     */
    public void run(Scanner sc){
        for(int i = 0; i < menuLists.size(); i++){
            MenuList m = menuLists.get(current);
            m.seeMenu();
            int f = sc.nextInt();
            m.enter(f);
        }
    }
}
