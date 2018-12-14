package elfoAPI.sale;

import elfoAPI.calendar.CalendarTools;
import elfoAPI.calendar.schedule.Schedule;
import elfoAPI.calendar.schedule.ScheduleDay;
import elfoAPI.calendar.schedule.ScheduleEvent;
import elfoAPI.calendar.schedule.ScheduleRepository;
import elfoAPI.data.IIdentifiable;
import elfoAPI.exception.calendar.EventInvalidException;
import elfoAPI.exception.sale.MonthsForRentOrFinanceInvalidException;
import elfoAPI.exception.sale.SaleIsFinalizedException;
import elfoAPI.users.User;
import elfoAPI.users.UserController;
import elfoAPI.users.UserTools;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.1.15
 */
public class Sale implements IIdentifiable, Serializable {

    public static final int IN_CASH = 0;
    public static final int IN_FINANCE = 1;
    public static final int IN_RENT = 2;
    public static final int SIMPLE_RENT = 3;
    public static final int SIMPLE_FINANCE = 4;
    public static final String[] METHODS = new String[]{"Full Payment Purchase","Finance Purchase","Rent"};
    transient private ISellable product;
    private String productCode;
    private final String identity;
    private int method;
    private int months;
    private Sale[] subSales;
    private boolean aproved;
    private boolean lateFinalizer;
    private boolean finalizer;
    private int[] buyDay;
    private int[] payday;
    private int[] saleDay;
    private String buyer;

    /**
     * @param buyer Buyer
     * @param method Payment method
     * @param product Product
     */
    public Sale(User buyer,int method, ISellable product, String identity) {
        this.buyer = buyer.getIdentity();
        this.method = method;
        this.product = product;
        this.productCode = product.getIdentity();
        this.finalizer = false;
        this.buyDay = CalendarTools.getCurrentDate();
        this.identity = identity;
    }

    public Sale(User buyer, int method, ISellable product, String identity, int[] payday){
        this(buyer, method, product, identity);
        this.finalizer = false;
        this.buyDay = CalendarTools.getCurrentDate();
        this.payday = payday;
    }

    /**
     * @return Sale Code
     */
    public String getIdentity(){
        return identity;
    }

    public void finalizer(){
        try {
            if (getLate() != null && getLate().length > 0) {
                //atrasado e finalizado
                this.lateFinalizer = true;
            } else{
                //nao ta atrasado
                this.lateFinalizer = false;
            }
        } catch (SaleIsFinalizedException e) {
            //fazer nada, ja esta finalizado
            return;
        }
        this.finalizer = true;
        if(method == SIMPLE_RENT || method == SIMPLE_FINANCE){
            return;
        }
        setProduct(product);
        unbrandSubsalesEvents();
        if(subSales != null) {
            for (Sale sale : subSales) {
                sale.finalizer();
            }
        }
    }

    public int[] getBuyDay(){
        return buyDay;
    }
    public int[] getPayday(){
        return payday;
    }

    public int[] getSaleDay(){
        return saleDay;
    }

    public Sale[] getPedding() throws SaleIsFinalizedException {
        if(method != IN_RENT && method != IN_FINANCE){
            //nao tem subsales
            return null;
        }
        ArrayList<Sale> salesPedding = new ArrayList<>();
        for(Sale sale: subSales) {
            if (!sale.isAproved()) {
                salesPedding.add(sale);
            }
        }
        return salesPedding.toArray(new Sale[salesPedding.size()]);
    }

    public Sale[] getLate() throws SaleIsFinalizedException {
        if(method != IN_RENT && method != IN_FINANCE){
            //nao tem subsales
            return null;
        }

        Sale[] pedding = getPedding();
        ArrayList<Sale> late = new ArrayList<>();
        for(Sale sale: pedding){
            if(sale.isBefore(CalendarTools.getCurrentDate())){
                late.add(sale);
            }
        }
        return late.toArray(new Sale[late.size()]);
    }


    private ScheduleDay getDay(int[] date){
        Schedule schedule = ScheduleRepository.getInstance().get(buyer,date[2]);
        return schedule.getDayOfDate(date[1], date[0]);
    }

    private void unbrandSubsalesEvents(){
        if(subSales != null){
            for(int i = 0; i < subSales.length; i++){
                Sale subSale = subSales[i];
                ScheduleDay day = getDay(subSale.getPayday());
                day.deleteEvent(identity + "-" + i);
            }
        }
    }

    private void setMonths(int months){
        this.months = months;
    }

    public void setMonths(int months, int[] payday) throws EventInvalidException, MonthsForRentOrFinanceInvalidException {
        if(months <= 0){
            throw new MonthsForRentOrFinanceInvalidException();
        }
        this.payday = payday;
        this.months = months;
        this.subSales = new Sale[months];
        for(int i = 0; i < months; i++){
            int[] date = CalendarTools.dateChanger(30*i,payday);
            ScheduleDay day = getDay(date);
            ScheduleEvent scheduleEvent = new ScheduleEvent("Rent of Sale:" + identity + " price:" + product.getRentPrice(), identity + "-" + i, day);
            day.addEvents(scheduleEvent);
            User buyerUser = UserController.getInstance().getUser(buyer);
            if(method == IN_RENT) {
                subSales[i] = new Sale(buyerUser, SIMPLE_RENT, product, this.identity + "-" + i, date);
                subSales[i].setMonths(months);
            }else if(method == IN_FINANCE){
                subSales[i] = new Sale(buyerUser, SIMPLE_FINANCE, product, this.identity + "-" + i, date);
                subSales[i].setMonths(months);
            }
        }
    }

    public int getMonths(){
        return months;
    }

    public boolean isAfter(int day, int month, int year){
        if(this.payday != null) {
            return this.getDay(payday).isAfter(day,month,year);
        }else if(this.saleDay != null){
            return this.getDay(saleDay).isAfter(day,month,year);
        }else{
            return this.getDay(buyDay).isAfter(day,month,year);
        }
    }

    public boolean isBefore(int day, int month, int year){
        if(this.payday != null) {
            return this.getDay(payday).isBefore(day,month,year);
        }else if(this.saleDay != null){
            return this.getDay(saleDay).isBefore(day,month,year);
        }else{
            return this.getDay(buyDay).isBefore(day,month,year);
        }
    }

    public boolean isBeforeLastDay(int day, int month, int year){
        if(method != IN_RENT){
            return false;
        }
        int[] date = CalendarTools.dateChanger(30,subSales[months - 1].getPayday());
        return this.getDay(date).isBefore(day,month,year);
    }

    private boolean isBeforeLastDay(int[] date){
        return isBeforeLastDay(date[0],date[1],date[2]);
    }

    public boolean isAfter(int[] date){
        return isAfter(date[0],date[1],date[2]);
    }
    public boolean isBefore(int[] date){
        return isBefore(date[0],date[1],date[2]);
    }


    /**
     * @return if aproved
     */
    public boolean isAproved() throws SaleIsFinalizedException {
        cheakFinalizer();
        if(method == IN_CASH || method == SIMPLE_RENT || method == SIMPLE_FINANCE) {
            return aproved;
        }
        boolean flag = true;

        for (Sale sale : subSales) {
            if (sale != null && !sale.isAproved()) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    public boolean isAprovedFinal(){
        if(method == IN_CASH || method == SIMPLE_RENT || method == SIMPLE_FINANCE) {
            return aproved;
        }
        boolean flag = true;
        for (Sale sale : subSales) {
            if (sale != null && !sale.isAprovedFinal()) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    public String getMethodText(){
        switch (method){
            case IN_CASH:
                return "CASH";
            case IN_FINANCE:
                return "FINANCE";
            case IN_RENT:
                return "RENT";
            case SIMPLE_RENT:
                return "SIMPLE_RENT";
            case SIMPLE_FINANCE:
                return "SIMPLE FINANCE";
            default:
                return "";
        }
    }

    /**
     * @param bool Boolean
     */
    public void setAproved(boolean bool) throws SaleIsFinalizedException {
        cheakFinalizer();
        aproved = bool;
        product.setAvaliable(!bool);
        if(bool){
            saleDay = CalendarTools.getCurrentDate();
        } else{
            saleDay = null;
        }
    }

    private void cheakFinalizer() throws SaleIsFinalizedException {
        if(finalizer){
            throw new SaleIsFinalizedException(this);
        }
    }

    public Sale[] getSubSales(){
        return subSales;
    }

    /**
     * @return Price
     */
    public double getPrice(){
        double price;
        if(method == IN_RENT || method == SIMPLE_RENT){
            price = product.getRentPrice();
        }else if(method == SIMPLE_FINANCE){
            price = product.getBuyPrice() / ((double) months);
        }else{
            price = product.getBuyPrice();
        }
        return price;
    }

    public String getStatus(){
        try {
            if(product == null){
                return "NULL";
            }
            if(isAproved()){
                return "APROVED";
            }else if((getLate() != null && getLate().length > 0) ||
                    ((method == SIMPLE_RENT || method == SIMPLE_FINANCE) && isBefore(CalendarTools.getCurrentDate()))){
                return "LATE";
            }else if((getPedding() != null && getPedding().length > 0) ||
                    method == SIMPLE_RENT || method == SIMPLE_FINANCE) {
                return "PEDDING";
            }else{
                return "NOT APROVED";
            }
        } catch (SaleIsFinalizedException e) {
            if(lateFinalizer){
                return "LATE/FINALIZER";
            } else if(!aproved) {
                return "FINALIZER";
            }else{
                return "APROVED/FINALIZER";
            }
        }
    }

    /**
     * @param cpf CPF
     * @return if Cpf buyer
     */
    public boolean isCpfBuyer(int[] cpf){
        return buyer.equals(UserTools.convertCpfToString(cpf));
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

    String getProductCode(){
        return productCode;
    }

    void setProduct(ISellable sellable){
        this.product = sellable;
        if(method == IN_FINANCE || method == IN_RENT){
            for(Sale sale: subSales){
                sale.setProduct(product);
            }
        }

        boolean avaliable = product.isAvaliable();
        if(method == IN_RENT){
            if(finalizer ||                                             //aluguel finalizado
                    isBeforeLastDay(CalendarTools.getCurrentDate()) ||  //se ja passou do ultimo dia do aluguel
                    !subSales[0].isAprovedFinal()) {                    //se nao foi aprovado o primeiro mes
                avaliable = true;
            }else {
                avaliable = false;
            }
        }else if(method == IN_FINANCE){
            try {
                if(subSales[0].isAproved()){    //se esta aprovado pelo menos o primeiro
                    avaliable = false;            //entao a pessoa iniciou a divida
                }else{
                    avaliable = true;             //ainda nao se comprometeu, logo fica disponivel
                }
            } catch (SaleIsFinalizedException e) {
                if(isAprovedFinal()){
                    avaliable = false;            //finalizado com divida completa, indisponivel
                }else{
                    avaliable = true;             //finalizado mas nao pago, disponivel
                }
            }
        }else if(method == IN_CASH){
            if(isAprovedFinal()){
                avaliable = false;              //infisponivel
            }else{
                avaliable = true;               //disponivel
            }
        }
        product.setAvaliable(avaliable);
    }

}
