package elfo.console;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class MenuList {
    protected Menu menuHome;
    private ArrayList<Consumer<Menu>> actions;
    private ArrayList<String> labels;
    private ArrayList<Integer> menus;
    private ArrayList<Integer> back;

    /**
     * @param menuHome Menu Class
     */
    public MenuList(Menu menuHome){
        actions = new ArrayList<Consumer<Menu>>();
        labels = new ArrayList<String>();
        menus = new ArrayList<Integer>();
        back = new ArrayList<Integer>();
        this.menuHome = menuHome;
    }

    /**
     * Remove item of index
     * @param index Index
     */
    public void remove(int index){
        actions.remove(index);
        labels.remove(index);
        menus.remove(index);
    }

    /**
     * Remove last item
     */
    public void remove(){
        remove(actions.size() - 1);
    }

    /**
     * Clear MenuList
     */
    public void removeAll(){
        actions.removeAll(actions);
        labels.removeAll(labels);
        menus.removeAll(menus);
    }

    /**
     * @param label Label Text
     * @param action Action Consumer(Menu)
     */
    public void addOption(String label, Consumer<Menu> action){
        actions.add(action);
        labels.add(label);
        menus.add(-2);
    }

    /**
     * @param label Label Text
     * @param menu Index of MenuList
     */
    public void addOption(String label, int menu){
        actions.add(null);
        labels.add(label);
        menus.add(menu);
    }

    /**
     * @param key Key menu
     */
    public void enter(int key){
        if(key == -1){
            back();
        }else if(key < actions.size() && actions.get(key) != null) {
            actions.get(key).accept((menuHome));
        }else{
            if(key < menus.size() && (key < actions.size() && actions.get(key) == null) && menuHome.getClass() == Menu.class){
                int current = menuHome.current;
                menuHome.current = menus.get(key);
                MenuList currentList = menuHome.getMenuList(menuHome.current);
                currentList.back.add(current);
            }
        }
    }

    /**
     * Back option
     */
    private void back(){
        menuHome.current = back.get(back.size() - 1);
        back.remove((Integer) menuHome.current);
    }

    /**
     * Print of MenuList
     */
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
