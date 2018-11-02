package elfo.console;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class MenuList {
    Menu menuHome;
    ArrayList<Consumer<Menu>> actions;
    ArrayList<String> labels;
    ArrayList<Integer> menus;
    public ArrayList<Integer> back;

    public MenuList(Menu menuHome){
        actions = new ArrayList<Consumer<Menu>>();
        labels = new ArrayList<String>();
        menus = new ArrayList<Integer>();
        back = new ArrayList<Integer>();
        this.menuHome = menuHome;
    }
    public void remove(int index){
        actions.remove(index);
        labels.remove(index);
        menus.remove(index);
    }
    public void remove(){
        remove(actions.size() - 1);
    }

    public void removeAll(){
        actions.removeAll(actions);
        labels.removeAll(labels);
        menus.removeAll(menus);
    }
    public void addOption(String label, Consumer<Menu> action){
        actions.add(action);
        labels.add(label);
        menus.add(-2);
    }

    public void addOption(String label, int menu){
        actions.add(null);
        labels.add(label);
        menus.add(menu);
    }
    public void enter(int key){
        if(key == -1){
            back(menuHome);
        }else if(key < actions.size() && actions.get(key) != null) {
            actions.get(key).accept((menuHome));
        }else{
            if(key < menus.size() && (key < actions.size() && actions.get(key) == null) && menuHome.getClass() == Menu.class){
                int current = menuHome.current;
                menuHome.current = menus.get(key);
                MenuList currentList = menuHome.menuLists.get(menuHome.current);
                currentList.back.add(current);
            }
        }
    }

    public void back(Menu m){
        m.current = back.get(back.size() - 1);
        back.remove((Integer) m.current);
    }
    public void seeMenu(){
        if(back.size() > 0){
            System.out.printf("-1 - <<Back<<\n");
        }
        for(int i = 0; i < labels.size(); i++){
            System.out.printf("%d  - %s\n",i,labels.get(i));
        }

        System.out.printf("\n");
    }

}
