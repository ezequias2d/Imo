package imo.property;

import elfo.data.IRepositorio;
import elfo.data.Serializer;
import elfo.exception.data.DataCannotBeAccessedException;
import elfo.number.DeltaNumber;
import sun.plugin.perf.PluginRollup;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class PropertyController {

    private static PropertyController propertyController;

    private int identityCount;
    private PropertyRepository propertyRepository;
    private PropertyTypeRepository propertyTypeRepository;

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
        this.propertyTypeRepository = PropertyTypeRepository.getInstace();
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
                e.printStackTrace();
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
     * @param area Area
     * @param buyPrice Buy Price
     * @param rentPrice Rent Price
     * @return New Property
     */
    public Property createNewProperty(double area, double buyPrice,double rentPrice, PropertyType propertyType) throws DataCannotBeAccessedException {
        Property property = new Property(area,buyPrice, rentPrice, propertyType, "#" + newIdentity());
        propertyRepository.add(property);
        return property;
    }

    /**
     * Create a new Property
     * @param buyPrice Buy Price
     * @param rentPrice Rent Price
     * @return New Property
     */
    public Property createNewProperty(ArrayList<Room> rooms, double buyPrice,double rentPrice, PropertyType propertyType) throws DataCannotBeAccessedException {
        Property property = new Property(rooms,buyPrice, rentPrice, propertyType, "#" + newIdentity());
        propertyRepository.add(property);
        return property;
    }

    public PropertyType[] getPropertiesTypes(){
        return propertyTypeRepository.toArray();
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
                    isInArrayPropertyType(property,propertyTypes)){
                propertiesOut.add(property);
            }
        }
        return propertiesOut;
    }

    public void delete(Property property) throws DataCannotBeAccessedException {
        propertyRepository.remove(property);
    }

    private boolean isInArrayPropertyType(Property property, PropertyType[] propertyTypes){
        if(propertyTypes.length == 0){
            return true;
        }
        for(PropertyType propertyType : propertyTypes){
            if(propertyType.getName().equals(property.getPropertyTypeName())){
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