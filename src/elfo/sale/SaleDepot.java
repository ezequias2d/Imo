package elfo.sale;

import elfo.calendar.CalendarTools;
import elfo.calendar.Day;
import elfo.calendar.schedule.Schedule;
import elfo.exception.user.UserInvalidException;
import elfo.exception.user.UserIsRegistredException;
import elfo.users.UserController;
import elfo.users.UserTools;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class SaleDepot {
    private static Schedule scheduleSale;
    private static SaleDepot saleDepot;
    private ArrayList<Sale> sales;
    private UserController userController;
    private Scanner sc;
    private Day today;

    private SaleDepot() {
        userController = UserController.getInstance();
        sales = new ArrayList<Sale>();
        sc = UserTools.getScanner();
        today = getScheduleSale().getDay();
    }

    /**
     * @return Today ScheduleDay
     */
    public Day getDay(){
        return today;
    }

    /**
     * @return schedule Sale
     */
    public static Schedule getScheduleSale() {
        if(scheduleSale == null){
           scheduleSale  = new Schedule();
        }
        return scheduleSale;
    }

    /**
     * @return Instance of SaleDepot
     */
    public static SaleDepot getInstace() {
        if(saleDepot == null){
            saleDepot = new SaleDepot();
        }
        return saleDepot;
    }

    /**
     * @param buyerCpf Buyer CPF
     * @param price Price
     * @param method Method
     * @param productCode Product Code
     * @return A New Sale
     */
    public Sale newSale(int[] buyerCpf, double price, int method, int productCode) throws UserInvalidException, UserIsRegistredException {
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
        int date[] = CalendarTools.dateChanger(- days,today.getDay(),today.getMonth(),today.getYear());
        if(userController.getPermission(sc.next()) && userController.isADM1()) {
            int i = 0;
            while (sales.get(i).isAfter(date[0],date[1],date[2]) && sales.get(i).isAproved()){
                out.add(sales.get(i));
                i++;
            }
        }
        return out;
    }

    /**
     * @return ArrayList with finished purchases
     */
    public ArrayList<Sale> getAprovedPurchases(){
        ArrayList<Sale> out = new ArrayList<Sale>();
        if(userController.getPermission(sc.next()) && userController.isADM1()){
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
        if(userController.getPermission(sc.next()) && userController.isADM1()){
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
