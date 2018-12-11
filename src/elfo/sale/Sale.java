package elfo.sale;

import elfo.calendar.CalendarTools;
import elfo.calendar.Day;
import elfo.data.IIdentifiable;
import elfo.exception.sale.SaleIsCanceledException;
import elfo.users.User;
import elfo.users.UserTools;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class Sale implements IIdentifiable {

    public static final int IN_CASH = 0;
    public static final int IN_FINANCE = 1;
    public static final int IN_RENT = 2;
    public static final String[] METHODS = new String[]{"Full Payment Purchase","Finance Purchase","Rent"};
    private static int totalSale;
    private ISellable product;
    private final String identity;
    private int method;
    private boolean aproved;
    private boolean canceled;
    private Day buyDay;
    private Day saleDay;
    private User buyer;

    /**
     * @param buyer Buyer
     * @param method Payment method
     * @param product Product
     */
    public Sale(User buyer,int method, ISellable product) {
        this.buyer = buyer;
        this.method = method;
        this.product = product;
        this.canceled = false;
        buyDay = new Day(CalendarTools.getCurrentDate());
        identity = String.valueOf(totalSale);
        totalSale += 1;
    }

    /**
     * @return Sale Code
     */
    public String getIdentity(){
        return identity;
    }

    public void cancel(){
        this.canceled = true;
    }

    public Day getBuyDay(){
        return buyDay;
    }

    public Day getSaleDay(){
        return saleDay;
    }

    public boolean isAfter(int day, int month, int year){
        if(this.saleDay != null){
            return this.saleDay.isAfter(day,month,year);
        }else{
            return this.buyDay.isAfter(day,month,year);
        }
    }
    public boolean isAfter(int[] date){
        return isAfter(date[0],date[1],date[2]);
    }

    /**
     * @return String Form
     */
    @Override
    public String toString(){
        String out = "";
        out = String.format("Buyer:%s - %s, Product Code:%s, Price:$%.2f, Aproved:",
                buyer.getFormalName(), UserTools.convertCpfToString(buyer.getCpf()),
                product.getIdentity(), getPrice());
        out += aproved + ", Buy:" + buyDay.toString();
        if(saleDay != null){
            out += ", Sale:" + saleDay.toString();
        }
        out += ", Sale CODE:" + identity;
        return out;
    }

    /**
     * @return if aproved
     */
    public boolean isAproved() throws SaleIsCanceledException {
        cheakCancel();
        return aproved;
    }

    /**
     * @param bool Boolean
     */
    public void setAproved(boolean bool) throws SaleIsCanceledException {
        cheakCancel();
        aproved = bool;
        if(bool){
            saleDay = new Day(CalendarTools.getCurrentDate());
        } else{
            saleDay = null;
        }
    }

    private void cheakCancel() throws SaleIsCanceledException {
        if(canceled){
            throw new SaleIsCanceledException(this);
        }
    }

    /**
     * @return Price
     */
    public double getPrice(){
        double price;
        if(method == 2){
            price = product.getRentPrice();
        }else{
            price = product.getBuyPrice();
        }
        return price;
    }

    /**
     * @param cpf CPF
     * @return if Cpf buyer
     */
    public boolean isCpfBuyer(int[] cpf){
        return buyer.isCpf(cpf);
    }

    /**
     * @return Product Code
     */
    public String getProductIdentity(){
        return product.getIdentity();
    }

    /**
     * @return Method
     */
    public int getMethod(){
        return method;
    }

}
