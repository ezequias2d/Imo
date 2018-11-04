package elfo.sale;

import elfo.calendar.Day;
import elfo.users.User;
import elfo.users.UserControl;
import elfo.users.UserTools;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class Sale {

    public static final int IN_CASH = 0;
    public static final int IN_FINANCE = 1;
    private static int totalSale;
    private int productCode;
    private int saleCode;
    private int method;
    private double price;
    private boolean aproved;
    private Day buyDay;
    private Day saleDay;
    private User buyer;

    /**
     * @param buyerCpf Buyer CPF in int[]
     * @param price Price value
     * @param method Payment method
     * @param productCode Product Code
     */
    private Sale(int[] buyerCpf,double price, int method, int productCode){
        UserControl userControl = UserControl.getInstance();
        this.buyer = userControl.getUser(buyerCpf,UserControl.LEVEL_NORMAL);
        this.price = price;
        this.method = method;
        this.productCode = productCode;
        this.buyDay = SaleControl.getInstace().getDay();
        this.buyDay.addSave(this);
        saleCode = totalSale;
        totalSale += 1;
    }

    /**
     * @return Sale Code
     */
    public int getSaleCode(){
        return saleCode;
    }

    /**
     * @return String Form
     */
    @Override
    public String toString(){
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

    /**
     * @return if aproved
     */
    public boolean isAproved(){
        return aproved;
    }

    /**
     * @param bool Boolean
     */
    public void setAproved(boolean bool){
        if(bool){
            saleDay = SaleControl.getInstace().getDay();
        }else{
            saleDay = null;
        }
        aproved = bool;
    }

    /**
     * @return Price
     */
    public double getPrice(){
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
    public int getProductCode(){
        return productCode;
    }

    /**
     * @return Method
     */
    public int getMethod(){
        return method;
    }

    /**
     * @param buyerCpf Buyer CPF
     * @param price Price
     * @param method Method
     * @param productCode Product Code
     * @return A New Sale or null
     */
    static public Sale create(int[] buyerCpf,double price, int method, int productCode){
        if(UserTools.authenticateCpf(buyerCpf)){
            return new Sale(buyerCpf,price,method,productCode);
        }
        return null;
    }
}
