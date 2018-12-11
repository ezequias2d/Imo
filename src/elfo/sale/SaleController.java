package elfo.sale;

import elfo.calendar.CalendarTools;
import elfo.exception.data.DataCannotBeAccessedException;
import elfo.exception.sale.ProductNotAvaliableException;
import elfo.exception.sale.SaleIsCanceledException;
import elfo.exception.user.UserDoesNotExistForThisTypeException;
import elfo.exception.user.permission.UserInvalidPermissionException;
import elfo.users.User;
import elfo.users.UserController;

import java.io.IOException;
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
    private int[] today;

    private SaleController() {
        userController = UserController.getInstance();
        saleRepository = new SaleRepository();
        today = CalendarTools.getCurrentDate();
    }
    /**
     * Pega instancia unica
     * @return Instance of SaleController
     */
    public static SaleController getInstace() {
        if(saleController == null){
            saleController = new SaleController();
        }
        return saleController;
    }
    /**
     * Cria uma nova compra
     * @param buyerCpf Buyer CPF
     * @param method Method
     * @param product Product Code
     * @return A New Sale
     */
    public Sale newSale(int[] buyerCpf, int method, ISellable product) throws IOException, DataCannotBeAccessedException, UserDoesNotExistForThisTypeException, ProductNotAvaliableException {
        if(product.isAvaliable()) {
            User userBuy = userController.getUser(buyerCpf, User.LEVEL_NORMAL);
            Sale sale = new Sale(userBuy, method, product);
            saleRepository.add(sale);
            return sale;
        }else{
            throw new ProductNotAvaliableException();
        }

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
    public ArrayList<Sale> getAprovedPurchases(int days, String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        ArrayList<Sale> out = new ArrayList<Sale>();
        int date[] = CalendarTools.dateChanger(- days,today);
        if(userController.getPermission(password,User.LEVEL_ADM1)) {
            for(int i = 0; saleRepository.get(i).isAfter(date) && saleRepository.get(i).isAproved(); i ++){
                out.add(saleRepository.get(i));
            }
        }
        return out;
    }

    /**
     * Pega compras confirmadas
     * @return ArrayList with finished purchases
     */
    public ArrayList<Sale> getAprovedPurchases(String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        ArrayList<Sale> out = new ArrayList<Sale>();
        if(userController.getPermission(password,User.LEVEL_ADM1)){
            for(Sale sale : saleRepository.toArray()){
                if(sale.isAproved()){
                    out.add(sale);
                }
            }
        }
        return out;
    }

    /**
     * Pega compras nao confirmadas
     * @return ArrayList with unfinished purchases
     */
    public ArrayList<Sale> getPendingPurchases(String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        ArrayList<Sale> out = new ArrayList<Sale>();
        userController.getPermission(password,User.LEVEL_ADM1);
        for(Sale sale: saleRepository.toArray()){
            if(!sale.isAproved()){
                out.add(sale);
            }
        }
        return out;
    }
}
