package imo.functions;

import elfo.exception.data.DataCannotBeAccessedException;
import elfo.number.DeltaNumber;
import imo.exception.ParameterOutOfTypeException;
import imo.property.*;

import java.util.ArrayList;

public class ImoPropertyFunctions {
    private PropertyController propertyController;

    private DeltaNumber moneyLimit;
    private DeltaNumber areaLimit;
    private DeltaNumber floorsLimit;
    private DeltaNumber roomsLimit;


    /**
     * Constructor
     */
    public ImoPropertyFunctions(){
        propertyController = PropertyController.getInstance();
        moneyLimit = new DeltaNumber();
        areaLimit = new DeltaNumber();
        floorsLimit = new DeltaNumber();
        roomsLimit = new DeltaNumber();
    }

    public void setMoneyLimit(double min, double max){
        moneyLimit.setDelta(min, max);
    }

    public void setAreaLimit(double min, double max){
        areaLimit.setDelta(min, max);
    }

    public void setFloorsLimit(double min, double max){
        floorsLimit.setDelta(min, max);
    }

    public void setRoomsLimit(double min, double max){
        floorsLimit.setDelta(min, max);
    }

    /**
     */
    public void deleteProperty(Property property) throws DataCannotBeAccessedException {
        propertyController.delete(property);
    }
    /**
     * MenuCommand
     * Register Property
     */
    public void registerProperty(String name, double value, double area, int floors, ArrayList<Room> rooms) throws ParameterOutOfTypeException, DataCannotBeAccessedException {
        PropertyType propertyType = PropertyTypeRepository.getInstace().get("House"); //tipo generico de casa
        Property property = propertyController.createNewProperty(area,value,0,propertyType);
        property.setName(name);
        property.setFloors(floors);
        property.addRooms(rooms);
    }
    /**
     * @return Search of property
     */
    public ArrayList<Property> getSearch(PropertyType... propertyTypes){
        return propertyController.filterProperties(moneyLimit,areaLimit,floorsLimit,roomsLimit,propertyTypes);
    }

    public ArrayList<Property> getSearch(double min, double max, ArrayList<Property> listToFilter, String typeRoom){
        return propertyController.filterProperties(typeRoom, listToFilter,new DeltaNumber(min,max));
    }

    public PropertyType[] getPropertyTypes(){
        return propertyController.getPropertiesTypes();
    }
}
