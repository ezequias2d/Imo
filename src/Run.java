import elfo.console.Menu;
import elfo.users.User;
import elfo.users.UserScreen;
import elfo.users.UserTools;
import imo.exception.ParameterOutOfTypeException;
import imo.menu.Client;
import imo.menu.RealEstateManager;
import imo.menu.Realtor;
import imo.property.*;


/**
 * @author Ezequias Silva
 * @version 0.0.13
 */
public class Run {
    static public void main(String[] argv){
        PropertyRepository propertyRepository = PropertyRepository.getInstance();
        UserScreen userScreen = UserScreen.getInstace();
        Menu menu = Menu.getInstance();
        Boolean loop = true;

        //Cria propriedades para demonstracao
        for(int u = 1; u < 10; u++){
            try {
                PropertyType propertyType = PropertyTypeRepository.getInstace().get("House"); //tipo generico de casa
                Property property = propertyRepository.createNewProperty(u * 10, u, 0, propertyType);
                property.setName("Nome da propriedade" + u);
                for (int i = 0; i < 2 * (u + 1); i++) {//floor
                    property.setBuyPrice((i + 1) * 1000 + property.getBuyPrice());
                    for (int j = 0; j < Room.values().length; j++) {//type room
                        for (int k = 0; k < (j * u / (i + 1 + u)); k++) {//rooms
                            Room room = Room.values()[j - 1];
                            room.setArea(u * 10.0 / 11.0 * ((j + 1.0) / (i + 1.0)));
                            property.addRoom(room);
                        }
                    }
                }
                propertyRepository.addProperty(property);
            }catch (ParameterOutOfTypeException parameterOutOfTypeException) {
                System.out.println(parameterOutOfTypeException.getMessage());
            }
        }

        //seta telas para cada tipo de login
        userScreen.setNextScreen(Client.getInstace().getMenuListIndex(), User.LEVEL_NORMAL);
        userScreen.setNextScreen(Realtor.getInstace().getMenuListIndex(), User.LEVEL_ADM2);
        userScreen.setNextScreen(RealEstateManager.getInstace().getMenuListIndex(), User.LEVEL_ADM1);

        menu.setMenuIndex(userScreen.getIndexMenu());


        while(loop){
            //inicia loop de entrada
            menu.run(UserTools.getScanner());
        }

    }
}
