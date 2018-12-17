package elfoAPI.sale;

import elfoAPI.data.Serializer;
import elfoAPI.exception.data.DataCannotBeAccessedException;

import java.util.ArrayList;

/**
 * Classe que implementa um repositorio de Sale
 * @version 0.0.3
 * @author EZequias Moises dos Santos Silva
 */
public class SaleRepository implements ISaleRepository {

    private static final String URI = "src/resources/depot/sales.dat";

    private ArrayList<Sale> sales;
    private Serializer serializer;

    /**
     * Constructor
     */
    public SaleRepository() throws DataCannotBeAccessedException {
        this.serializer = Serializer.getInstance();
        try {
            this.sales = (ArrayList<Sale>) serializer.open(URI);
        } catch (DataCannotBeAccessedException e) {
            this.sales = new ArrayList<Sale>();
            update();
        }
    }

    public void setSellableRepository(ISellableRepository sellableRepository){
        for(Sale sale: sales){
            ISellable sellable = sellableRepository.get(sale.getProductIdentity());
            sale.setProduct(sellable);
        }
    }

    /**
     * Adiciona ao repositorio
     * @param object Object to add
     * @return True
     */
    @Override
    public boolean add(Sale object) throws DataCannotBeAccessedException {
        boolean out = sales.add(object);
        serializer.save(URI,sales);
        return out;
    }

    /**
     * Remove do repositorio
     * @param object Object to remove
     * @return if removed
     */
    @Override
    public boolean remove(Sale object) throws DataCannotBeAccessedException {
        boolean out = sales.remove(object);
        serializer.save(URI,sales);
        return out;
    }

    /**
     * Pega por index
     * @param index  Index
     * @return Sale of index
     */
    @Override
    public Sale get(int index) {
        return sales.get(index);
    }

    /**
     * Pega por identificador
     * @param identity id
     * @return Sale of identity
     */
    @Override
    public Sale get(String identity) {
        for(Sale sale : sales){
            if(sale.getIdentity().equals(identity)){
                return sale;
            }
        }
        return null;
    }

    /**
     * Pega index do objeto no repositorio
     * @param object Object
     * @return index of object
     */
    @Override
    public int get(Sale object) {
        return sales.indexOf(object);
    }


    /**
     * Cria um array com todos os "Sale"s do repositorio
     * @return Array of sales
     */
    @Override
    public Sale[] toArray() {
        Sale[] salesArray = new Sale[sales.size()];
        for(int i = 0;i < sales.size(); i++){
            salesArray[i] = sales.get(i);
        }
        return salesArray;
    }

    /**
     * Atualiza dados persistentes
     */
    @Override
    public void update() throws DataCannotBeAccessedException {
        serializer.save(URI, sales);
    }
}
