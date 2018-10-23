package elfo.console;

import java.util.ArrayList;
import java.util.function.Consumer;

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
    public void addOption(String label, Consumer<Menu> action){
        actions.add(action);
        labels.add(label);
        menus.add(-1);
    }

    public void addOption(String label, int menu){
        actions.add(null);
        labels.add(label);
        menus.add(menu);
    }
    public void enter(int key){
        if(key == -1){
            back(menuHome);
        }else if(actions.get(key) != null) {
            actions.get(key).accept((menuHome));
        }else{
            if(menuHome.getClass() == Menu.class){
                int current = menuHome.current;
                menuHome.current = menus.get(key);
                MenuList currentList = menuHome.menuLists.get(menuHome.current);
                currentList.back.add(current);
            }
        }
    }

    public void back(Menu m){
        m.current = back.get(back.size() - 1);
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
