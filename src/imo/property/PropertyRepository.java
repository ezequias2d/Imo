package imo.property;

import elfoAPI.data.IRepositorio;
import elfoAPI.data.Serializer;
import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.sale.ISellableRepository;
import elfoAPI.sale.SaleController;

import java.util.ArrayList;


/**
 * Implementa o repositorio de propriedade
 * @author Jose Romulo Pereira
 * @version 0.0.19
 */
public class PropertyRepository implements IRepositorio<Property>, ISellableRepository<Property> {
    private static final String URI = "src/resources/depot/properties.dat";
    private Serializer serializer;
    private ArrayList<Property> properties;

    public PropertyRepository() throws DataCannotBeAccessedException {
        this.serializer = Serializer.getInstance();
        try {
            this.properties = (ArrayList<Property>) serializer.open(URI);
            SaleController.getInstace().setSellableRepository(this);
        } catch (DataCannotBeAccessedException e) {
            this.properties = new ArrayList<Property>();
            update();
        }
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
     * Add Property
     * @param property Property
     * @return if added
     */
    @Override
    public boolean add(Property property) throws DataCannotBeAccessedException {
        boolean out = properties.add(property);
        update();
        return out;
    }

    /**
     * @param property Property
     * @return if removed
     */
    @Override
    public boolean remove(Property property) throws DataCannotBeAccessedException {
        boolean out = properties.remove(property);
        update();
        return out;
    }

    /**
     * @param index Index/Code
     * @return Property of index
     */
    @Override
    public Property get(int index) {
        return properties.get(index);
    }

    /**
     * @return Properties ArrayList
     */
    @Override
    public Property[] toArray(){
        Property[] out = new Property[properties.size()];
        for(int i = 0; i < properties.size(); i++){
            out[i] = properties.get(i);
        }
        return out;
    }

    @Override
    public Property get(String identity) {
        for(Property property : properties){
            if(property.getIdentity().equals(identity)){
                return property;
            }
        }
        return null;
    }



    @Override
    public void update() throws DataCannotBeAccessedException {
        serializer.save(URI,properties);
    }
}
