package imo.property;

import elfo.data.IRepositorio;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class PropertyTypeRepository implements IRepositorio<PropertyType>, Serializable {
    private static PropertyTypeRepository instance;
    private ArrayList<PropertyType> propertyTypes;

    private PropertyTypeRepository(){
        propertyTypes = new ArrayList<PropertyType>();
        loadDefault();
    }
    public static PropertyTypeRepository getInstace(){
        if(instance == null){
            instance = new PropertyTypeRepository();
        }
        return instance;
    }
    private void loadDefault(){
        PropertyType house = new PropertyType("House","Simply a house.");
        PropertyType rowHouse = new PropertyType("Row House","Any of a row of houses joined by common sidewalls.");
        PropertyType apartment = new PropertyType("Apartment","A room or set of rooms fitted especially with housekeeping facilities and usually leased as a dwelling.");
        PropertyType emptyGround = new PropertyType("Empty Ground", "An empty lot.");
        PropertyType building = new PropertyType("Building", "A structure with a roof and walls, such as a house, school, store, or factory.");

        this.add(house);
        this.add(rowHouse);
        this.add(apartment);
        this.add(emptyGround);
        this.add(building);
    }

    @Override
    public boolean add(PropertyType object){
        return propertyTypes.add(object);
    }

    @Override
    public boolean remove(PropertyType object){
        return propertyTypes.remove(object);
    }

    @Override
    public PropertyType get(int index) {
        return propertyTypes.get(index);
    }

    @Override
    public PropertyType get(String indent) {
        for(PropertyType propertyType : propertyTypes){
            if(propertyType.getName().equals(indent)){
                return propertyType;
            }
        }
        return null;
    }

    @Override
    public int get(PropertyType object) {
        return propertyTypes.indexOf(object);
    }

    @Override
    public PropertyType[] toArray() {
        PropertyType[] out = new PropertyType[propertyTypes.size()];
        for(int i = 0; i < propertyTypes.size(); i++){
            out[i] = propertyTypes.get(i);
        }
        return out;
    }

    @Override
    public void update() throws IOException {

    }
}
