package imo.functions;

import elfo.calendar.CalendarTools;
import elfo.calendar.schedule.ScheduleDay;
import elfo.calendar.schedule.ScheduleEvent;
import elfo.exception.calendar.EventInvalidException;
import elfo.exception.calendar.HourNotExistException;
import elfo.exception.data.DataCannotBeAccessedException;
import elfo.exception.sale.ProductNotAvaliableException;
import elfo.exception.sale.SaleIsCanceledException;
import elfo.exception.user.UserDoesNotExistForThisTypeException;
import elfo.exception.user.UserInvalidException;
import elfo.exception.user.UserIsRegistredException;
import elfo.exception.user.permission.UserInvalidPermissionException;
import elfo.sale.Sale;
import elfo.users.IUserInput;
import elfo.users.User;
import elfo.users.UserTools;
import imo.exception.EventNotBrandException;
import imo.exception.ParameterOutOfTypeException;
import imo.property.Property;
import imo.property.PropertyType;
import imo.property.Room;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Imobily {
    private ImoAccountFunctions accountFunctions;
    private ImoPropertyFunctions propertyFunctions;
    private ImoCalendarFunctions calendarFunctions;
    private ImoSaleFunctions saleFunctions;

    public Imobily(IUserInput userInput){
        this.accountFunctions = new ImoAccountFunctions(userInput);
        this.propertyFunctions = new ImoPropertyFunctions();
        this.calendarFunctions = new ImoCalendarFunctions();
        this.saleFunctions = new ImoSaleFunctions();
    }

    //----------ACCOUNT
    public String getAccountInfo(){
        return accountFunctions.getAccountInfo();
    }

    /**
     * Menu Command
     */
    public void changePassword() throws DataCannotBeAccessedException, UserInvalidPermissionException {
        accountFunctions.changePassword();
    }

    /**
     * Menu command
     */
    public void changeFullName() throws DataCannotBeAccessedException, UserInvalidPermissionException {
        accountFunctions.changeFormalName();
    }

    /**
     * Menu Command
     */
    public void changeFormalName() throws DataCannotBeAccessedException, UserInvalidPermissionException {
        accountFunctions.changeFormalName();
    }
    /**
     * Menu Command
     */

    public void changePasswordADM1() throws UserInvalidPermissionException, DataCannotBeAccessedException {
        accountFunctions.changePasswordADM1();
    }
    /**
     * Menu Command
     */
    public void changeCpfADM1() throws UserInvalidPermissionException, DataCannotBeAccessedException {
        accountFunctions.changeCpfADM1();
    }

    /**
     */
    public void deleteAccount() throws UserInvalidPermissionException, DataCannotBeAccessedException {
        accountFunctions.deleteAccount();
    }

    public void logout(){
        accountFunctions.logout();
    }
    public void register(String fullName, String cpf, String password, String firstName, String lastName, int userType) throws UserIsRegistredException, UserInvalidException, DataCannotBeAccessedException {
        accountFunctions.register(fullName,cpf,password,firstName, lastName, userType);
    }


    //-----------CALENDAR------------
    public ArrayList<ScheduleEvent> getPeriodEvents(LocalDate date, int period){
        int[] dateInt = CalendarTools.convertToDate(date);
        ArrayList<ScheduleEvent> events = new ArrayList<ScheduleEvent>();
        for(int i = 0; i < period; i ++){
            ScheduleDay day = calendarFunctions.getDay(dateInt);
            events.addAll(day.getEvents());
            dateInt = CalendarTools.dateChanger(1,dateInt);
        }
        return events;
    }

    public void eventBrand(Property property, LocalDate date, String time) throws EventInvalidException, EventNotBrandException, HourNotExistException {
        int[] dateV = CalendarTools.convertToDate(date);
        int[] timeV = CalendarTools.convertToHour(time);
        calendarFunctions.eventBrand(property,dateV,timeV);
    }

    //----------PROPERTY-------------
    public ArrayList<Property> getSearch(PropertyType... propertyTypes){
        return propertyFunctions.getSearch(propertyTypes);
    }

    public ArrayList<Property> getSearch(double min, double max, ArrayList<Property> listToFilter, String typeRoom){
        return propertyFunctions.getSearch(min,max,listToFilter,typeRoom);
    }

    public void setMoneyLimit(double min, double max){
        propertyFunctions.setMoneyLimit(min,max);
    }

    public void setAreaLimit(double min, double max){
        propertyFunctions.setAreaLimit(min,max);
    }

    public void setFloorsLimit(double min, double max){
        propertyFunctions.setFloorsLimit(min, max);
    }

    public void setRoomsLimit(double min, double max){
        propertyFunctions.setRoomsLimit(min,max);
    }

    public void deleteProperty(Property property) throws DataCannotBeAccessedException {
        propertyFunctions.deleteProperty(property);
    }

    public void registerProperty(String name, double value, double area, int floors, ArrayList<Room> rooms) throws ParameterOutOfTypeException, DataCannotBeAccessedException {
        propertyFunctions.registerProperty(name,value,area,floors,rooms);
    }
    public PropertyType[] getPropertyTypes(){
        return propertyFunctions.getPropertyTypes();
    }

    //---------------SALE-------------
    public ArrayList<Sale> getAprovedPurchases(int days, String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        return saleFunctions.getAprovedPurchases(days,password);
    }

    /**
     * @return Screen with unfinished purchases
     */
    public ArrayList<Sale> getVisualPendingPurchases(String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        return saleFunctions.getVisualPendingPurchases(password);
    }
    /**
     * @return Screen with finished purchases
     */
    public ArrayList<Sale> getVisualAprovedPurchases(String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        return saleFunctions.getVisualAprovedPurchases(password);
    }

    /**
     * Create a New Sale
     * @param method Method of pay
     * @param property Property to sale
     */
    public void newSale(Property property, int method) throws UserDoesNotExistForThisTypeException, ProductNotAvaliableException, DataCannotBeAccessedException, IOException {
        saleFunctions.newSale(property,method);
    }

    /**
     * Confima uma 'sale'
     * @param sale Sale to confime
     * @param password Password ADM1
     * @return Sale Confirmed
     */
    public void confirmSale(Sale sale, String password) throws UserInvalidPermissionException, SaleIsCanceledException {
        saleFunctions.confirmSale(sale,password);
    }

    /**
     * Desconfirma uma 'sale'
     * @param sale Sale to unconfime
     * @param password Password ADM1
     * @return Sale unconfirmed
     */
    public void unconfirmSale(Sale sale, String password) throws UserInvalidPermissionException, SaleIsCanceledException {
       saleFunctions.unconfirmSale(sale,password);
    }


}
