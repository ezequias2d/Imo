package imo.property;

import elfoAPI.data.IRepositorio;
import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.number.DeltaNumber;
import elfoAPI.sale.ISellableRepository;
import elfoAPI.sale.SaleController;
import imo.exception.ParameterOutOfTypeException;

import java.util.ArrayList;

/**
 * Representa a classe que controla o repositorio e propriedades
 * aplicando as regras de cadrasto alem de fazer o retorno de um ArrayList
 * filtrado.
 *
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class PropertyController {

    private static PropertyController propertyController;

    private int identityCount;
    private IRepositorio<Property> propertyRepository;
    private PropertyTypeRepository propertyTypeRepository;

    private PropertyType propertyTypeGeneric;

    /**
     * Constructor
     */
    private PropertyController() throws DataCannotBeAccessedException {
        identityCount = -1;
        this.propertyRepository = new PropertyRepository();
        for(Property property: propertyRepository.toArray()){
            int idNum = Integer.valueOf(property.getIdentity().substring(1));
            if(idNum > identityCount){
                identityCount = idNum;
            }
        }
        this.propertyTypeRepository = new PropertyTypeRepository();
        this.propertyTypeGeneric = new PropertyType("All","All");

        SaleController.getInstace().setSellableRepository((ISellableRepository) propertyRepository);
    }

    /**
     * @return Instance of PropertyController
     */
    static public PropertyController getInstance(){
        if(propertyController == null){
            try {
                propertyController = new PropertyController();
            } catch (DataCannotBeAccessedException e) {
                //Sistema nao pode pode acessar dados ao ser iniciado
                //nao faz sentido o programa continuar executando.
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return propertyController;
    }


    private int newIdentity(){
        identityCount++;
        return identityCount;
    }

    /**
     * Create a new Property
     * @param buyPrice Buy Price
     * @param rentPrice Rent Price
     * @return New Property
     */
    public Property createNewProperty(ArrayList<Room> rooms, int floors, double area, double buyPrice,double rentPrice, PropertyType propertyType, String andress) throws DataCannotBeAccessedException, ParameterOutOfTypeException {
        Property property = new Property(rooms, floors, area,buyPrice, rentPrice, propertyType, andress, "#" + newIdentity());
        propertyRepository.add(property);
        return property;
    }

    public PropertyType[] getPropertiesTypes(){
        ArrayList<PropertyType> propertiesTypes = new ArrayList<PropertyType>();
        propertiesTypes.add(propertyTypeGeneric);
        for(PropertyType propertyType : propertyTypeRepository.toArray()){
            propertiesTypes.add(propertyType);
        }
        return propertiesTypes.toArray(new PropertyType[propertiesTypes.size()]);
    }

    /**
     * @param moneyLimit MoneyLimit
     * @param areaLimit AreaLimit
     * @param floorsLimit FloorsLimit
     * @param roomsLimit RoomsLimit
     * @return Filtered Properties
     */
    public ArrayList<Property> filterProperties(DeltaNumber moneyLimit, DeltaNumber areaLimit, DeltaNumber floorsLimit, DeltaNumber roomsLimit, PropertyType... propertyTypes){
        ArrayList<Property> propertiesOut = new ArrayList<Property>();
        for (Property property: propertyRepository.toArray()) {
            if(property.isInPrice(moneyLimit) &&
                    property.isInArea(areaLimit) &&
                    property.isInNumberRooms(roomsLimit) &&
                    property.isAvaliable() &&
                    property.isInFloors(floorsLimit) &&
                    property.isAvaliable() &&
                    isInArrayPropertyType(property,propertyTypes)){
                propertiesOut.add(property);
            }
        }
        return propertiesOut;
    }

    public void delete(Property property) throws DataCannotBeAccessedException {
        propertyRepository.remove(property);
    }

    public void update() throws DataCannotBeAccessedException {
        propertyRepository.update();
    }

    private boolean isInArrayPropertyType(Property property, PropertyType[] propertyTypes){
        if(propertyTypes.length == 0){
            return true;
        }
        for(PropertyType propertyType : propertyTypes){
            if(propertyType.getName().equals(property.getPropertyTypeName()) || propertyType == propertyTypeGeneric){
                return true;
            }
        }
        return false;
    }

    /**
     * @param roomType Room Type
     * @param limitRoomType LimitRoom of Type
     * @return Filtered Properties
     */
    public ArrayList<Property> filterProperties(String roomType, ArrayList<Property> properties, DeltaNumber limitRoomType){
        ArrayList<Property> propertiesOut = new ArrayList<Property>();
        for(Property property: properties){
            if(property.isInNumberRooms(limitRoomType,roomType)){
                propertiesOut.add(property);
            }
        }
        return propertiesOut;
    }

}