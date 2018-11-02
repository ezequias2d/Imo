package elfo.sale;

import elfo.calendar.Day;
import elfo.calendar.Schedule;
import elfo.calendar.ScheduleControl;
import elfo.users.User;
import elfo.users.UserControl;
import elfo.users.UserTools;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class Sale {

    public static final int IN_CASH = 0;
    public static final int IN_FINANCE = 1;
    private static int saleCode;
    private int productCode;
    private int method;
    private double price;
    private boolean aproved;
    private UserControl userControl;
    private Day buyDay;
    private Day saleDay;
    User buyer;

    private Sale(int[] buyerCpf,double price, int method, int productCode){
        this.userControl = UserControl.getUserControl();
        this.buyer = userControl.getUserOfCpfAndType(buyerCpf,UserControl.LEVEL_NORMAL);
        this.price = price;
        this.method = method;
        this.productCode = productCode;
        this.buyDay = SaleControl.getInstace().getDay();
        this.buyDay.addSave(this);
        saleCode += 1;
    }

    public int getSaleCode(){
        return saleCode;
    }

    public String getInfo(){
        String out = "";
        out = String.format("Buyer:%s - %s, Product Code:%d, Price:$%.2f, Aproved:",
                buyer.getFormalName(), UserTools.convertCpfToString(buyer.getCpf()),
                productCode, price);
        out += aproved + ", Buy:" + buyDay.toString();
        if(saleDay != null){
            out += ", Sale:" + saleDay.toString();
        }
        out += ", Sale CODE:" + saleCode;
        return out;
    }
    public boolean isAproved(){
        return aproved;
    }
    public void setAproved(boolean bool){
        if(bool){
            saleDay = SaleControl.getInstace().getDay();
        }else{
            saleDay = null;
        }
        aproved = bool;
    }
    public double getPrice(){
        return price;
    }
    public String toString(){
        return getInfo();
    }
    public boolean isCpfBuyer(int[] cpf){
        return buyer.isCpf(cpf);
    }
    public int getProductCode(){
        return productCode;
    }
    public int getMethod(){
        return method;
    }
    static public Sale newSale(int[] buyerCpf,double price, int method, int productCode){
        if(UserTools.authenticateCpf(buyerCpf)){
            return new Sale(buyerCpf,price,method,productCode);
        }
        return null;
    }
}
