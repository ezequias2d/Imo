import elfo.console.Menu;
import elfo.users.UserControl;
import elfo.users.UserScreen;
import elfo.users.UserTools;
import elfo.number.Number;
import imo.menu.Client;
import imo.menu.RealEstateManager;
import imo.menu.Realtor;
import imo.property.*;


public class PropertyTest {
    static public void main(String[] argv){
        int area = 1000;
        PropertyControl propertyControl = PropertyControl.getPropertyControl();
        UserScreen userScreen = UserScreen.getInstace();
        Menu menu = Menu.getMenu();
        Boolean loop = true;
        for(int u = 1; u < 10; u++){
            Property property = propertyControl.createNewProperty(area, new Number(136000));
            property.setName("Nome da propriedade" + u);
            PropertyView propertyView = PropertyView.getInstance();
            for(int i = 0; i < 2*(u+1); i++){//floor
                Floor floor = property.createFloor();
                for(int j = 0; j < 13; j++){//type room
                    for(int k = 0; k < (j*u/(i+1+u)); k++){//rooms
                        Room room = new Room(j-1,area/11*((j+1)/(i+1)));
                        floor.add(room);
                    }
                }
            }
            propertyControl.addProperty(property);
        }
        userScreen.setNextScreen(Client.getInstace().getMenuListIndex(), UserControl.LEVEL_NORMAL);
        userScreen.setNextScreen(Realtor.getInstace().getMenuListIndex(), UserControl.LEVEL_ADM2);
        userScreen.setNextScreen(RealEstateManager.getInstace().getMenuListIndex(), UserControl.LEVEL_ADM1);
        menu.setMenuIndex(userScreen.getIndexMenu());
        while(loop){
            menu.run(UserTools.getScanner());
        }

    }
}
