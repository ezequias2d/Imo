package imo.property;

import elfo.data.IRepositorio;
import elfo.number.DeltaNumber;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class PropertyRepository implements IRepositorio<Property>,Serializable{
    private static PropertyRepository propertyRepository;

    private PropertyTypeRepository propertyTypeRepository;
    private ArrayList<Property> properties;

    /**
     * Constructor
     */
    private PropertyRepository() {
        this.properties = new ArrayList<Property>();
        this.propertyTypeRepository = PropertyTypeRepository.getInstace();
    }

    /**
     * @return Instance of PropertyRepository
     */
    static public PropertyRepository getInstance(){
        if(propertyRepository == null){
            propertyRepository = new PropertyRepository();
        }
        return propertyRepository;
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
     * @param buyPrice Buy Price
     * @param rentPrice Rent Price
     * @return New Property
     */
    public Property createNewProperty(double area, double buyPrice,double rentPrice, PropertyType propertyType){
        Property property = new Property(area,buyPrice, rentPrice, propertyType);
        properties.add(property);
        return property;
    }

    /**
     * @param property Property
     * @return Index of Property
     */
    @Override
    public int get(Property property){
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
    public ArrayList<Property> getProperties(){
        ArrayList<Property> out = new ArrayList<Property>();
        out.addAll(properties);
        return out;
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
        for (Property property: properties) {
            if(property.isInPrice(moneyLimit) &&
                    property.isInArea(areaLimit) &&
                    property.isInNumberRooms(roomsLimit) &&
                    property.isAvailability() &&
                    property.isInFloors(floorsLimit) &&
                    isInArrayPropertyType(property,propertyTypes)){
                propertiesOut.add(property);
            }
        }
        return propertiesOut;
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

    @Override
    public boolean add(Property object) throws IOException {
        return false;
    }

    @Override
    public boolean remove(Property object) throws IOException {
        return false;
    }

    @Override
    public Property get(int index) {
        return null;
    }

    @Override
    public Property get(String indent) {
        return null;
    }

    @Override
    public Property[] toArray() {
        return new Property[0];
    }

    @Override
    public void update() throws IOException {

    }
}