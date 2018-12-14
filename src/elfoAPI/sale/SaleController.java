package elfoAPI.sale;

import elfoAPI.calendar.CalendarTools;
import elfoAPI.exception.calendar.EventInvalidException;
import elfoAPI.exception.calendar.InvalidCalendarDateException;
import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.exception.sale.MonthsForRentOrFinanceInvalidException;
import elfoAPI.exception.sale.ProductNotAvaliableException;
import elfoAPI.exception.sale.SaleIsFinalizedException;
import elfoAPI.users.User;
import elfoAPI.users.UserController;

import java.util.ArrayList;

/**
 * Representa a classe que manipula o repositorio e gerencia a
 * criaçao e confirmaçao de "Sale"s no geral
 *
 * @author Ezequias Moises dos Santos Silva
 * @version 0.1.29
 */
public class SaleController {
    private static SaleController saleController;
    private SaleRepository saleRepository;
    private UserController userController;
    private int saleCount;
    private int[] today;

    private SaleController() throws DataCannotBeAccessedException {
        userController = UserController.getInstance();
        saleRepository = new SaleRepository();
        saleCount = 0;
        for(Sale sale : saleRepository.toArray()){
            int i = Integer.valueOf(sale.getIdentity());
            if(i > saleCount){
                saleCount = i + 1;
            }
        }
        today = CalendarTools.getCurrentDate();
    }
    /**
     * Pega instancia unica
     * @return Instance of SaleController
     */
    public static SaleController getInstace() {
        if(saleController == null){
            try {
                saleController = new SaleController();
            } catch (DataCannotBeAccessedException e) {
                //nao faz sentido continuar
                System.exit(124);
            }
        }
        return saleController;
    }

    public void setSellableRepository(ISellableRepository sellableRepository){
        saleRepository.setSellableRepository(sellableRepository);
    }

    /**
     * Cria uma nova compra
     * @param user Buyer
     * @param method Method
     * @param product Product Code
     * @return A New Sale
     */
    public Sale newSale(User user, int method, ISellable product) throws DataCannotBeAccessedException, ProductNotAvaliableException {
        if(product.isAvaliable()) {
            Sale sale = new Sale(user, method, product, "" + saleCount++);
            saleRepository.add(sale);
            return sale;
        }else{
            throw new ProductNotAvaliableException();
        }

    }
    public Sale newSale(User user, int method, ISellable product, int months, int[] payday) throws DataCannotBeAccessedException,
            ProductNotAvaliableException, EventInvalidException, MonthsForRentOrFinanceInvalidException, InvalidCalendarDateException {
        if(product.isAvaliable()) {
            if(payday == null || payday.length != 3){
                throw new InvalidCalendarDateException();
            }
            Sale sale = new Sale(user, method, product, "" + saleCount++);
            sale.setMonths(months,payday);
            saleRepository.add(sale);
            return sale;
        }else{
            throw new ProductNotAvaliableException();
        }

    }

    public void deleteSale(Sale sale) throws DataCannotBeAccessedException {
        saleRepository.remove(sale);
    }

    /**
     * Pega compras de um CPF
     * @param cpf Cpf
     * @return ArrayList with sales of CPF user
     */
    public ArrayList<Sale> getSalesOfCpf(int[] cpf){
        ArrayList<Sale> out = new ArrayList<Sale>();
        for(Sale s: saleRepository.toArray()){
            if(s.isCpfBuyer(cpf)){
                out.add(s);
            }
        }
        return out;
    }

    /**
     * Pega uma compra por codigo
     * @param code Sale Code
     * @return Sale of Code or Null (if no exist)
     */
    public Sale getSaleOfCode(String code){
        for (Sale s: saleRepository.toArray()) {
            if(s.getIdentity().equals(code)){
                return s;
            }
        }
        return null;
    }

    /**
     * @param days Amount of days before today to count
     * @return ArrayList with finished purchases of before days
     */
    public ArrayList<Sale> getAprovedPurchases(int days) throws SaleIsFinalizedException {
        ArrayList<Sale> out = new ArrayList<Sale>();
        int date[] = CalendarTools.dateChanger(- days,today);
        for(int i = 0; saleRepository.get(i).isAfter(date) && saleRepository.get(i).isAproved(); i ++){
            out.add(saleRepository.get(i));
        }
        return out;
    }

    /**
     * Pega compras confirmadas
     * @return ArrayList with finished purchases
     */
    public ArrayList<Sale> getAprovedPurchases() throws SaleIsFinalizedException {
        ArrayList<Sale> out = new ArrayList<Sale>();

        for(Sale sale : saleRepository.toArray()){
            if(sale.isAproved()){
                out.add(sale);
            }
        }

        return out;
    }

    /**
     * Pega compras nao confirmadas
     * @return ArrayList with unfinished purchases
     */
    public ArrayList<Sale> getPendingPurchases() throws SaleIsFinalizedException {
        ArrayList<Sale> out = new ArrayList<Sale>();
        for(Sale sale: saleRepository.toArray()){
            if(!sale.isAproved()){
                out.add(sale);
            }
        }
        return out;
    }

    public ArrayList<Sale> getSales(User user){
        ArrayList<Sale> salesUser = new ArrayList<>();
        for(Sale sale : saleRepository.toArray()){
            if(sale.isCpfBuyer(user.getCpf())){
                salesUser.add(sale);
            }
        }
        return salesUser;
    }

    public ArrayList<Sale> getLate(User user) {
        ArrayList<Sale> late = new ArrayList<>();
        for(Sale sale: getSales(user)){
            try {
                if (sale.getLate().length > 0) {
                    late.add(sale);
                }
            } catch (SaleIsFinalizedException e) {
                //nada, so esta verificando
            }
        }
        return late;
    }

    public ArrayList<Sale> getAproved(User user) {
        ArrayList<Sale> aproved = new ArrayList<>();
        for(Sale sale: getSales(user)){
            try {
                if(sale.isAproved()){
                    aproved.add(sale);
                }
            } catch (SaleIsFinalizedException e) {
                //nada, so esta verificando
            }
        }
        return aproved;
    }

    public ArrayList<Sale> getNotAproved(User user) {
        ArrayList<Sale> notAproved = new ArrayList<>();
        for(Sale sale: getSales(user)){
            try {
                if(!sale.isAproved()){
                    notAproved.add(sale);
                }
            } catch (SaleIsFinalizedException e) {
                //nada, so esta verificando
            }
        }
        return notAproved;
    }

    public ArrayList<Sale> getFinalizer(User user)  {
        ArrayList<Sale> canceled = new ArrayList<>();
        for(Sale sale: getSales(user)){
            try {
                sale.isAproved();
            } catch (SaleIsFinalizedException e) {
                canceled.add(sale);
            }
        }
        return canceled;
    }
    public int getSalesNumber(User user){
        return getSales(user).size();
    }

    public String getStatus(User user){
        ArrayList<Sale> salesUser = getSales(user);
        int aproved = 0;
        int notAproved = 0;
        int pedding = 0;
        int late = 0;
        int finalize = 0;
        for(Sale sale: salesUser){
            try{
                if(sale.isAproved()){
                    aproved++;
                }else{
                    notAproved++;
                }
                if(sale.getMethod() == Sale.IN_RENT || sale.getMethod() == Sale.IN_FINANCE) {
                    if (sale.getLate() != null) {
                        late += sale.getLate().length;
                    }
                    if(sale.getPedding() != null){
                        pedding += sale.getPedding().length;
                    }
                }
            } catch (SaleIsFinalizedException e) {
                finalize++;
            }
        }
        return aproved + "|" + notAproved + "|" + pedding + "|"+ late + "|" + finalize;
    }

    public void update() throws DataCannotBeAccessedException {
        saleRepository.update();
    }
}
