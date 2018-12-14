package imo.property;

import elfoAPI.data.IRepositorio;
import elfoAPI.data.Serializer;
import elfoAPI.exception.data.DataCannotBeAccessedException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Implementa um repositorio para PropertyType
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.2
 */
public class PropertyTypeRepository implements IRepositorio<PropertyType> {
    private static final String URI = "src/resources/depot/propertiesTypes.dat";
    private static PropertyTypeRepository instance;
    private ArrayList<PropertyType> propertyTypes;
    private Serializer serializer;

    public PropertyTypeRepository() throws DataCannotBeAccessedException {
        this.serializer = Serializer.getInstance();
        try {
            this.propertyTypes = (ArrayList<PropertyType>) serializer.open(URI);
        } catch (DataCannotBeAccessedException e) {
            this.propertyTypes = new ArrayList<PropertyType>();
            loadDefault();
            update();
        }
    }

    private void loadDefault() throws DataCannotBeAccessedException {
        PropertyType house = new PropertyType("House","Simply a house.");
        PropertyType rowHouse = new PropertyType("Row House","Any of a row of houses joined by common sidewalls.");
        PropertyType apartment = new PropertyType("Apartment","A room or set of rooms fitted especially with housekeeping facilities and usually leased as a dwelling.");
        PropertyType emptyGround = new PropertyType("Empty Ground", "An empty lot.");
        PropertyType building = new PropertyType("Building", "A structure with a roof and walls, such as a house, school, store, or factory.");

        emptyGround.setLimitRooms(0,0);
        building.setLimitRooms(1,-1);

        propertyTypes.add(house);
        propertyTypes.add(rowHouse);
        propertyTypes.add(apartment);
        propertyTypes.add(emptyGround);
        propertyTypes.add(building);

        update();
    }

    @Override
    public boolean add(PropertyType object) throws DataCannotBeAccessedException {
        boolean out = propertyTypes.add(object);
        update();
        return out;
    }

    @Override
    public boolean remove(PropertyType object) throws DataCannotBeAccessedException {
        boolean out = propertyTypes.remove(object);
        update();
        return out;
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
    public void update() throws DataCannotBeAccessedException {
        serializer.save(URI,propertyTypes);
    }
}
