package elfo.sale;

import elfo.calendar.Day;
import elfo.calendar.Schedule;
import elfo.users.UserControl;
import elfo.users.UserTools;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class SaleControl {
    static Schedule scheduleSale;
    private static SaleControl saleControl;
    private ArrayList<Sale> sales;
    private UserControl userControl;
    private Scanner sc;
    private Day today;

    private SaleControl(){
        userControl = UserControl.getInstance();
        sales = new ArrayList<Sale>();
        sc = UserTools.getScanner();
        today = getScheduleSale().getElfoCalendar().getDay();
    }

    /**
     * @return Today Day
     */
    public Day getDay(){
        return today;
    }

    /**
     * @return Schedule Sale
     */
    public static Schedule getScheduleSale() {
        if(scheduleSale == null){
           scheduleSale  = new Schedule();
        }
        return scheduleSale;
    }

    /**
     * @return Instance of SaleControl
     */
    public static SaleControl getInstace(){
        if(saleControl == null){
            saleControl = new SaleControl();
        }
        return saleControl;
    }

    /**
     * @param buyerCpf Buyer CPF
     * @param price Price
     * @param method Method
     * @param productCode Product Code
     * @return A New Sale
     */
    public Sale newSale(int[] buyerCpf, double price, int method, int productCode){
        Sale sale = Sale.create(buyerCpf,price,method,productCode);
        sales.add(sale);
        return sale;
    }

    /**
     * @param cpf Cpf
     * @return ArrayList with sales of CPF user
     */
    public ArrayList<Sale> getSalesOfCpf(int[] cpf){
        ArrayList<Sale> out = new ArrayList<Sale>();
        for(Sale s: sales){
            if(s.isCpfBuyer(cpf)){
                out.add(s);
            }
        }
        return out;
    }



    /**
     * @param code Sale Code
     * @return Sale of Code or Null (if no exist)
     */
    public Sale getSaleOfCode(int code){
        for (Sale s: sales) {
            if(s.getSaleCode() == code){
                return s;
            }
        }
        return null;
    }

    /**
     * @param days Amount of days before today to count
     * @return ArrayList with finished purchases of before days
     */
    public ArrayList<Sale> getAprovedPurchases(int days){
        ArrayList<Sale> out = new ArrayList<Sale>();
        Day[] relatorySales = scheduleSale.getElfoCalendar().getBeforeDays(days);
        if(userControl.getPermission(sc.next()) && userControl.isADM1()) {
            for (int i = 0; i < relatorySales.length; i++) {
                ArrayList<Object> sales = relatorySales[i].getSave();
                for(int j = 0; j < sales.size(); j++){
                    Sale sale = (Sale)sales.get(i);
                    if(sale.isAproved()){
                        out.add(sale);
                    }
                }
                if(sales.size() == 0){
                    out.add(null);
                }
            }
        }
        return out;
    }

    /**
     * @return ArrayList with finished purchases
     */
    public ArrayList<Sale> getAprovedPurchases(){
        ArrayList<Sale> out = new ArrayList<Sale>();
        if(userControl.getPermission(sc.next()) && userControl.isADM1()){
            for(int i = 0; i < sales.size(); i++){
                Sale s = sales.get(i);
                if(s.isAproved()){
                    out.add(s);
                }

            }
        }
        return out;
    }

    /**
     * @return ArrayList with unfinished purchases
     */
    public ArrayList<Sale> getPendingPurchases(){
        ArrayList<Sale> out = new ArrayList<Sale>();
        if(userControl.getPermission(sc.next()) && userControl.isADM1()){
            for(int i = 0; i < sales.size(); i++){
                Sale s = sales.get(i);
                if(!s.isAproved()){
                    out.add(s);
                }
            }
        }
        return out;
    }
}