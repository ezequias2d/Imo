package imo.property;

import elfo.number.DeltaNumber;
import imo.cashier.Money;

import java.util.ArrayList;

public class PropertyControl {
    private static PropertyControl propertyControl;
    private ArrayList<Property> properties;

    private PropertyControl() {
        this.properties = new ArrayList<Property>();
    }
    static public PropertyControl getPropertyControl(){
        if(propertyControl == null){
            propertyControl = new PropertyControl();
        }
        return propertyControl;
    }
    
    public void addProperty(Property property){
        properties.add(property);
    }
    public Property createNewProperty(float area, Money price){
        Property property = new Property(area,price);
        properties.add(property);
        return property;
    }
    
    public boolean removeProperty(Property property){
        return properties.remove(property);
    }

    public Property getProperty(int index) {
        return properties.get(index);
    }
    ArrayList<Property> getProperties(){
        return properties;
    }

    public ArrayList<Property> filterProperties(DeltaNumber moneyLimit, DeltaNumber areaLimit, DeltaNumber floorsLimit, DeltaNumber roomsLimit){
        ArrayList<Property> propertiesOut = new ArrayList<Property>();
        for (Property property: properties) {
            if(property.isInPrice(moneyLimit) &&
                    property.isInArea(areaLimit) &&
                    property.isInNumberFloor(floorsLimit) &&
                    property.isInNumberRooms(roomsLimit)){
                propertiesOut.add(property);
            }
        }
        return propertiesOut;
    }
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