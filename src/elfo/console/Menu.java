package elfo.console;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public int current = 0;
    public ArrayList<MenuList> menuLists;
    public Menu(){
        menuLists = new ArrayList<MenuList>();
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
