package imo.functions;

import elfo.exception.data.DataCannotBeAccessedException;
import elfo.exception.sale.ProductNotAvaliableException;
import elfo.exception.sale.SaleIsCanceledException;
import elfo.exception.user.UserDoesNotExistForThisTypeException;
import elfo.exception.user.UserInvalidException;
import elfo.exception.user.UserIsRegistredException;
import elfo.exception.user.permission.UserInvalidPermissionException;
import elfo.sale.Sale;
import elfo.sale.SaleController;
import elfo.users.IUserInput;
import elfo.users.User;
import elfo.users.UserController;
import imo.property.Property;

import java.io.IOException;
import java.util.ArrayList;

public class ImoSaleFunctions {
    private UserController userController;
    private SaleController saleController;


    public ImoSaleFunctions(){

        userController = UserController.getInstance();
        saleController = SaleController.getInstace();

    }

    /**
     * Sales report completed
     * @param days Amount of days before today to count
     * @return Relatory String
     */
    public ArrayList<Sale> getAprovedPurchases(int days,String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        return saleController.getAprovedPurchases(days, password);
    }

    /**
     * @return Screen with unfinished purchases
     */
    public ArrayList<Sale> getVisualPendingPurchases(String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        return saleController.getPendingPurchases(password);
    }
    /**
     * @return Screen with finished purchases
     */
    public ArrayList<Sale> getVisualAprovedPurchases(String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        return saleController.getAprovedPurchases(password);
    }

    /**
     * Create a New Sale
     * @param method Method of pay
     * @param property Property to sale
     */
    public void newSale(Property property, int method) throws UserDoesNotExistForThisTypeException, ProductNotAvaliableException, DataCannotBeAccessedException, IOException {
        if(property != null){
            saleController.newSale(userController.getCpfCurrent(), method, property);
        }
    }

    /**
     * Confima uma 'sale'
     * @param sale Sale to confime
     * @param password Password ADM1
     * @return Sale Confirmed
     */
    public Sale confirmSale(Sale sale, String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        if((!sale.isAproved()) && userController.getPermission(password,User.LEVEL_ADM1)){
            sale.setAproved(true);
            System.out.printf("Sale aproved!\n");
            return sale;
        }
        return null;
    }

    /**
     * Desconfirma uma 'sale'
     * @param sale Sale to unconfime
     * @param password Password ADM1
     * @return Sale unconfirmed
     */
    public Sale unconfirmSale(Sale sale, String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        if(sale.isAproved() && userController.getPermission(password,User.LEVEL_ADM1)){
            sale.setAproved(true);
            System.out.printf("Sale aproved!\n");
            return sale;
        }
        return null;
    }


}
