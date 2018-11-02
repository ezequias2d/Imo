package elfo.sale;

import elfo.calendar.Day;
import elfo.calendar.ElfoCalendar;
import elfo.calendar.Schedule;
import elfo.users.UserControl;
import elfo.users.UserTools;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class SaleControl {
    static Schedule scheduleSale;
    private static SaleControl saleControl;
    private ArrayList<Sale> sales;
    private UserControl userControl;
    private Scanner sc;
    private Day hereDay;
    private SaleControl(){
        userControl = UserControl.getUserControl();
        sales = new ArrayList<Sale>();
        sc = UserTools.getScanner();
        hereDay = getScheduleSale().getElfoCalendar().getDay();
    }
    public Day getDay(){
        return hereDay;
    }
    public static Schedule getScheduleSale() {
        if(scheduleSale == null){
           scheduleSale  = new Schedule();
        }
        return scheduleSale;
    }
    public static SaleControl getInstace(){
        if(saleControl == null){
            saleControl = new SaleControl();
        }
        return saleControl;
    }
    public Sale newSale(int[] buyerCpf, double price, int method, int productCode){
        Sale sale = Sale.newSale(buyerCpf,price,method,productCode);
        sales.add(sale);
        return sale;
    }

    public ArrayList<Sale> getSalesOfCpf(int[] cpf){
        ArrayList<Sale> out = new ArrayList<Sale>();
        for(Sale s: sales){
            if(s.isCpfBuyer(cpf)){
                out.add(s);
            }
        }
        return out;
    }
    public String getVisualRelatory(int days){
        double totalSale = 0;
        String out = "";
        Day[] relatorySales = scheduleSale.getElfoCalendar().getBeforeDays(days);
        if(userControl.getPermission(sc.next()) && userControl.isADM1()) {
            for (int i = 0; i < relatorySales.length; i++) {
                ArrayList<Object> sales = relatorySales[i].getSave();
                for(int j = 0; j < sales.size(); j++){
                    Sale sale = (Sale)sales.get(i);
                    if(sale.isAproved()){
                        totalSale += sale.getPrice();
                        out += sale.getInfo() + "\n";
                    }
                }
                if(sales.size() == 0){
                    out += "NULL\n";
                }
            }
        }
        return out;
    }
    public String getVisualPendingPurchases(){
        String out = "";
        if(userControl.getPermission(sc.next()) && userControl.isADM1()){
            for(int i = 0; i < sales.size(); i++){
                Sale s = sales.get(i);
                if(!s.isAproved()){
                    out += String.format("&%d - %s\n",i,s.getInfo());
                }
            }
        }
        return out;
    }
    public Sale getSaleOfCode(int code){
        for (Sale s: sales) {
            if(s.getSaleCode() == code){
                return s;
            }
        }
        return null;
    }
    public String getVisualAprovedPurchases(){
        String out = "";
        if(userControl.getPermission(sc.next()) && userControl.isADM1()){
            for(int i = 0; i < sales.size(); i++){
                Sale s = sales.get(i);
                if(s.isAproved()){
                    out += String.format("&%d - %s\n",i,s.getInfo());
                }

            }
        }
        return out;
    }
    public Sale confirmSale(){
        if(userControl.getPermission(sc.next()) && userControl.isADM1()){
            System.out.printf("enter the sales code\n>");
            int index = sc.nextInt();
            Sale sale = sales.get(index);
            System.out.println(getSaleOfCode(index));
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
