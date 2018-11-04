package imo.property;

import elfo.number.DeltaNumber;

import java.util.ArrayList;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class PropertyControl {
    private static PropertyControl propertyControl;
    private ArrayList<Property> properties;

    /**
     * Constructor
     */
    private PropertyControl() {
        this.properties = new ArrayList<Property>();
    }

    /**
     * @return Instance of PropertyControl
     */
    static public PropertyControl getInstance(){
        if(propertyControl == null){
            propertyControl = new PropertyControl();
        }
        return propertyControl;
    }

    /**
     * Add Property
     * @param property Property
     * @return if added
     */
    public boolean addProperty(Property property){
        return properties.add(property);
    }

    /**
     * Create a new Property
     * @param area Area
     * @param price Price
     * @return New Property
     */
    public Property createNewProperty(double area, double price){
        Property property = new Property(area,price);
        properties.add(property);
        return property;
    }

    /**
     * @param property Property
     * @return Code of Property
     */
    public int getPropertyCode(Property property){
        return properties.indexOf(property);
    }

    /**
     * @param property Property
     * @return if removed
     */
    public boolean removeProperty(Property property){
        return properties.remove(property);
    }

    /**
     * @param index Index/Code
     * @return Property of index
     */
    public Property getProperty(int index) {
        return properties.get(index);
    }

    /**
     * @return Properties ArrayList
     */
    ArrayList<Property> getProperties(){
        return properties;
    }

    /**
     * @param moneyLimit MoneyLimit
     * @param areaLimit AreaLimit
     * @param floorsLimit FloorsLimit
     * @param roomsLimit RoomsLimit
     * @return Filtered Properties
     */
    public ArrayList<Property> filterProperties(DeltaNumber moneyLimit, DeltaNumber areaLimit, DeltaNumber floorsLimit, DeltaNumber roomsLimit){
        ArrayList<Property> propertiesOut = new ArrayList<Property>();
        for (Property property: properties) {
            if(property.isInPrice(moneyLimit) &&
                    property.isInArea(areaLimit) &&
                    property.isInNumberFloor(floorsLimit) &&
                    property.isInNumberRooms(roomsLimit) &&
                    property.isAvailability()){
                propertiesOut.add(property);
            }
        }
        return propertiesOut;
    }

    /**
     *
     * @param roomType Room Type
     * @param limitRoomType LimitRoom of Type
     * @return Filtered Properties
     */
    public ArrayList<Property> filterProperties(int roomType, DeltaNumber limitRoomType){
        ArrayList<Property> propertiesOut = new ArrayList<Property>();
        for(Property property: properties){
            if(property.isInNumberRooms(limitRoomType,roomType)){
                propertiesOut.add(property);
            }
        }
        return propertiesOut;
    }
    
}