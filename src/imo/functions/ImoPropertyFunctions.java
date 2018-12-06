package imo.functions;

import elfo.number.DeltaNumber;
import imo.exception.ParameterOutOfTypeException;
import imo.property.*;

import java.util.ArrayList;

public class ImoPropertyFunctions {
    private PropertyRepository propertyRepository;

    private DeltaNumber moneyLimit;
    private DeltaNumber areaLimit;
    private DeltaNumber floorsLimit;
    private DeltaNumber roomsLimit;


    /**
     * Constructor
     */
    public ImoPropertyFunctions(){
        propertyRepository = PropertyRepository.getInstance();
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
    private void deleteProperty(int code){
        Property property = propertyRepository.getProperty(code);
        propertyRepository.removeProperty(property);
        //System.out.printf("Removed Property!");
    }
    /**
     * MenuCommand
     * Register Property
     */
    private void registerProperty(String name, double value, double area, int floors, ArrayList<Room> rooms){
        try {
            PropertyType propertyType = PropertyTypeRepository.getInstace().get("House"); //tipo generico de casa
            Property property = propertyRepository.createNewProperty(area,value,0,propertyType);
            property.setName(name);
            property.setFloors(floors);
            property.addRooms(rooms);
            //createRoom(property);
        }catch (ParameterOutOfTypeException parameterOutOfTypeException) {
            System.out.println(parameterOutOfTypeException.getMessage());
        }
    }
    /**
     * @return Search of property
     */
    public ArrayList<Property> getSearch(PropertyType ... propertyTypes){
        return propertyRepository.filterProperties(moneyLimit,areaLimit,floorsLimit,roomsLimit,propertyTypes);
    }

    public ArrayList<Property> getSearch(double min, double max, ArrayList<Property> listToFilter, String typeRoom){
        return propertyRepository.filterProperties(typeRoom, listToFilter,new DeltaNumber(min,max));
    }

}
