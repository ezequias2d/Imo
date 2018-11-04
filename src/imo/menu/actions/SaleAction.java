package imo.menu.actions;

import elfo.sale.Sale;
import elfo.sale.SaleControl;
import elfo.users.UserControl;
import elfo.users.UserTools;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class SaleAction {
    private UserControl userControl;
    private Scanner sc;
    private SaleControl saleControl;


    public SaleAction(){
        userControl = UserControl.getInstance();
        sc = UserTools.getScanner();
        saleControl = SaleControl.getInstace();
    }

    /**
     * Sales report completed
     * @param days Amount of days before today to count
     * @return Relatory String
     */
    public String getVisualRelatory(int days){
        double totalSale = 0;
        String out = "";
        ArrayList<Sale> sales = saleControl.getAprovedPurchases(days);
        for(Sale s: sales){
            if(s != null){
                totalSale += s.getPrice();
                out += s.toString() + "\n";
            }else{
                out += "NULL\n";
            }
        }
        out += String.format("\nTotal: $%.2f\n",totalSale);
        return out;
    }

    /**
     * @return Screen with unfinished purchases
     */
    public String getVisualPendingPurchases(){
        String out = "";
        ArrayList<Sale> sales = saleControl.getPendingPurchases();
        for(Sale s: sales){
            if(s != null){
                out += String.format("&%d - %s\n",s.getSaleCode(),s.toString());
            }
        }
        return out;
    }
    /**
     * @return Screen with finished purchases
     */
    public String getVisualAprovedPurchases(){
        String out = "";
        ArrayList<Sale> sales = saleControl.getAprovedPurchases();
        for(Sale s: sales){
            if(s != null){
                out += String.format("&%d - %s\n",s.getSaleCode(),s.toString());
            }
        }
        return out;
    }

    /**
     * @return Sale Confirmed
     */
    public Sale confirmSale(){
        if(userControl.getPermission(sc.next()) && userControl.isADM1()){
            System.out.printf("Enter the sales code\n>");
            int index = sc.nextInt();
            Sale sale = saleControl.getSaleOfCode(index);
            System.out.println(sale);
            System.out.printf("Are you sure to confirm?(y/n):");
            if(sc.next().toCharArray()[0] == 'y'){
                sale.setAproved(true);
                System.out.printf("Sale aproved!\n");
                return sale;
            }
        }
        return null;
    }
}
