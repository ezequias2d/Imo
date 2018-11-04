import elfo.console.Menu;
import elfo.users.UserControl;
import elfo.users.UserScreen;
import elfo.users.UserTools;
import imo.menu.Client;
import imo.menu.RealEstateManager;
import imo.menu.Realtor;
import imo.property.Floor;
import imo.property.Property;
import imo.property.PropertyControl;
import imo.property.Room;


/**
 * @author Ezequias Silva
 * @version 0.0.13
 */
public class Run {
    static public void main(String[] argv){
        PropertyControl propertyControl = PropertyControl.getInstance();
        UserScreen userScreen = UserScreen.getInstace();
        Menu menu = Menu.getInstance();
        Boolean loop = true;

        //Cria propriedades para demonstracao
        for(int u = 1; u < 10; u++){
            Property property = propertyControl.createNewProperty(u*10, 136000);
            property.setName("Nome da propriedade" + u);
            for(int i = 0; i < 2*(u+1); i++){//floor
                Floor floor = property.createFloor();
                for(int j = 0; j < 13; j++){//type room
                    for(int k = 0; k < (j*u/(i+1+u)); k++){//rooms
                        Room room = new Room(j-1,u*10.0/11.0*((j+1.0)/(i+1.0)));
                        floor.add(room);
                    }
                }
            }
            propertyControl.addProperty(property);
        }

        //seta telas para cada tipo de login
        userScreen.setNextScreen(Client.getInstace().getMenuListIndex(), UserControl.LEVEL_NORMAL);
        userScreen.setNextScreen(Realtor.getInstace().getMenuListIndex(), UserControl.LEVEL_ADM2);
        userScreen.setNextScreen(RealEstateManager.getInstace().getMenuListIndex(), UserControl.LEVEL_ADM1);
        menu.setMenuIndex(userScreen.getIndexMenu());


        while(loop){
            //inicia loop de entrada
            menu.run(UserTools.getScanner());
        }

    }
}
