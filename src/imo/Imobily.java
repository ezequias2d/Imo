package imo;

import elfoAPI.calendar.CalendarTools;
import elfoAPI.calendar.schedule.*;
import elfoAPI.exception.calendar.EventInvalidException;
import elfoAPI.exception.calendar.HourNotExistException;
import elfoAPI.exception.calendar.InvalidCalendarDateException;
import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.exception.sale.MonthsForRentOrFinanceInvalidException;
import elfoAPI.exception.sale.ProductNotAvaliableException;
import elfoAPI.exception.sale.SaleIsFinalizedException;
import elfoAPI.exception.user.UserInvalidException;
import elfoAPI.exception.user.UserIsRegistredException;
import elfoAPI.exception.user.permission.UserInvalidPermissionException;
import elfoAPI.number.DeltaNumber;
import elfoAPI.sale.Sale;
import elfoAPI.sale.SaleController;
import elfoAPI.users.User;
import elfoAPI.users.UserController;
import elfoAPI.users.UserTools;
import imo.exception.EventNotBrandException;
import imo.exception.ParameterOutOfTypeException;
import imo.gui.view.UserInputFX;
import imo.property.Property;
import imo.property.PropertyController;
import imo.property.PropertyType;
import imo.property.Room;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Facade(Fachada) da Imobiliaria
 *
 * Classe destinada a representar uma imobiliaria com funçoes de:
 *      - Marca visita
 *      - Perquisar Propriedade(Ultilizando filtros)
 *      - Comprar ou alugar uma propriedade
 *      - Registrar usuarios
 *      - Fazer login no sistema
 *      - Registrar Propriedades por
 *          Tipo(casa, apartamento e etc)
 *          Area do terreno
 *          Area total
 *          Andares
 *          Comodos Individuais
 *          Preço(aluguel e completo)
 *
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.3
 */
public class Imobily {
    private UserController userController;
    private UserInputFX userInput;

    private Schedule schedule;
    private ScheduleRepository scheduleRepository;
    private DeltaTime deltaTime;

    private PropertyController propertyController;

    private DeltaNumber moneyLimit;
    private DeltaNumber areaLimit;
    private DeltaNumber floorsLimit;
    private DeltaNumber roomsLimit;

    private SaleController saleController;

    public Imobily(UserInputFX userInput){
        userController = UserController.getInstance();
        this.userInput = userInput;

        scheduleRepository = ScheduleRepository.getInstance();
        deltaTime = new DeltaTime(0,30);
        scheduleUpdate(CalendarTools.getCurrentYear());

        propertyController = PropertyController.getInstance();
        moneyLimit = new DeltaNumber();
        areaLimit = new DeltaNumber();
        floorsLimit = new DeltaNumber();
        roomsLimit = new DeltaNumber();
        saleController = SaleController.getInstace();
    }

    //----------ACCOUNT
    public boolean login(String cpf, String password){
        return userController.login(UserTools.stringToCpf(cpf),password);
    }

    public boolean isManager(){
        return userController.isADM1();
    }

    public boolean isRealEstateBroker(){
        return userController.isADM2();
    }

    public boolean isConsumer(){
        return userController.isNORMAL();
    }

    public User getLoadedAccount(){
        return userController.getLoadedAccount();
    }
    /**
     *
     */
    public String getAccountInfo(){
        return String.format("Full Name:%s\nFormal Name:%s\nCpf:%s\n",
                userController.getFullNameCurrent(),
                userController.getFormalNameCurrent(),
                UserTools.convertCpfToString(userController.getCpfCurrent()));
    }

    /**
     * Menu Command
     */
    public void changePassword() throws DataCannotBeAccessedException, UserInvalidPermissionException {
        String oldPassword = userInput.getPassword("Old Password");
        String newPassword = userInput.getPassword("New Password");
        userController.changeYourPassword(oldPassword,newPassword);
    }

    public User[] getUsers(){
        return userController.getUsers();
    }

    public void changeFullName() throws DataCannotBeAccessedException, UserInvalidPermissionException {
        String password = userInput.getPassword("Password");
        String fullname = userInput.getText("Full Name");
        userController.changeYourName(password,fullname);
    }

    /**
     * Menu Command
     */
    public void changeFormalName() throws DataCannotBeAccessedException, UserInvalidPermissionException {
        String password = userInput.getPassword("Password");
        String firstName = userInput.getText("First Name");
        String lastName = userInput.getText("Last Name");
        userController.changeYourFormalName(password,firstName,lastName);
    }
    /**
     * Menu Command
     */

    public void changePasswordADM1() throws UserInvalidPermissionException, DataCannotBeAccessedException {
        String password = userInput.getPassword("ADM1 Password");
        int[] cpf = userInput.getCPF("CPF");
        String newPassword = userInput.getPassword("New Password");
        userController.changePassword(userController.getUser(cpf),password,newPassword);
    }
    /**
     * Menu Command
     */
    public void changeCpfADM1() throws UserInvalidPermissionException, DataCannotBeAccessedException {
        String password = userInput.getPassword("AM1 Password");
        int[] cpf = userInput.getCPF("CPF");
        int[] newCpf = userInput.getCPF("New CPF");
        userController.changeCpf(userController.getUser(cpf),password,newCpf);
    }

    /**
     */
    public void deleteAccount() throws UserInvalidPermissionException, DataCannotBeAccessedException {
        String password = userInput.getPassword("ADM Password");
        int[] cpf = userInput.getCPF("CPF");
        userController.deleteAccount(password,cpf);
    }

    public void logout(){
        userController.logout();
    }
    public void register(String fullName, String cpf, String password, String firstName, String lastName, int userType) throws UserIsRegistredException, UserInvalidException, DataCannotBeAccessedException {
        userController.registerNewUser(fullName,UserTools.stringToCpf(cpf),password,firstName,lastName,userType);
    }


    //-----------CALENDAR------------
    public ArrayList<ScheduleEvent> getPeriodEvents(LocalDate date, int period){
        int[] dateInt = CalendarTools.convertToDate(date);
        ArrayList<ScheduleEvent> events = new ArrayList<ScheduleEvent>();
        for(int i = 0; i < period; i ++){
            ScheduleDay day = getDay(dateInt);
            events.addAll(day.getEvents());
            dateInt = CalendarTools.dateChanger(1,dateInt);
        }
        return events;
    }

    public void eventBrand(Property property, LocalDate date, String time) throws EventInvalidException, EventNotBrandException, HourNotExistException {
        int[] dateV = CalendarTools.convertToDate(date);
        int[] timeV = CalendarTools.convertToHour(time);
        eventBrand(property,dateV,timeV);
    }

    /**
     * Get schedule of current cpf
     */
    private void scheduleUpdate(int year){
        schedule = scheduleRepository.get(userController.getCurrentIdentity(), year);
    }

    /**
     */
    public void eventBrand(Property property, int[] date, int[] time) throws HourNotExistException, EventInvalidException, EventNotBrandException {
        String userFormalName = userController.getFormalNameCurrent();
        Schedule propertySchedule = property.getSchedule();
        String code = property.getIdentity();
        int minutes = time[1];
        int hour = time[0];
        String textEvent = String.format("Visit of property of code %s, ", code);
        scheduleUpdate(date[2]);


        if (this.schedule.isDisponible(date[1], date[0], hour, minutes, deltaTime) &&               //verifica se o usuario possui tempo disponivel
                propertySchedule.isDisponible(date[1], date[0], hour, minutes, deltaTime)) {        //verifica se o imovel esta disponivel

            User[] users = userController.getUsers(User.LEVEL_ADM2);                                //pega todos usuarios do tipo ADM2(Corretores de imoveis)
            users = CalendarTools.lessUserLoadedWithEvents(users, date[1], date[0]);                //ordena por corretores menos carregados(com eventos) no dia

            for (User adm2 : users) {
                Schedule schedule = scheduleRepository.get(adm2.getIdentity());
                schedule.createNewEvent(textEvent + userFormalName, date[1], date[0], hour, minutes, deltaTime);

                String ADM2FormalName = adm2.getFormalName();

                this.schedule.createNewEvent(textEvent + ADM2FormalName, date[1], date[0], hour, minutes, deltaTime);
                propertySchedule.createNewEvent(textEvent + "ADM2:" + ADM2FormalName + ", NORMAL:" + userFormalName, date[1], date[0], hour, minutes, deltaTime);
                return;
            }
        }
        throw new EventNotBrandException(new ScheduleEvent("",hour,minutes,deltaTime, schedule.getDayOfDate(date[1],date[0])),schedule,propertySchedule);
    }

    public ScheduleDay getDay(int[] date){
        scheduleUpdate(date[2]);
        schedule.setDate(date[2], date[1], date[0]);
        ScheduleDay out = schedule.getDay();
        schedule.upgradeToCurrentDay();
        return out;
    }
    public Schedule getSchedule(){
        return schedule;
    }

    //----------PROPERTY-------------
    public void setMoneyLimit(double min, double max){
        moneyLimit.setDelta(min, max);
    }

    public void setAreaLimit(double min, double max){
        areaLimit.setDelta(min, max);
    }

    public void setFloorsLimit(double min, double max){
        floorsLimit.setDelta(min, max);
    }

    public void setRoomsLimit(double min, double max){
        floorsLimit.setDelta(min, max);
    }

    /**
     */
    public void deleteProperty(Property property) throws DataCannotBeAccessedException {
        propertyController.delete(property);
    }
    /**
     * MenuCommand
     * Register Property
     */
    public void registerProperty(ArrayList<Room> rooms, int floors, double area, double buyPrice, double rentPrice, PropertyType propertyType, String andress) throws ParameterOutOfTypeException, DataCannotBeAccessedException {
        propertyController.createNewProperty(rooms,floors,area,buyPrice,rentPrice,propertyType, andress);
    }
    /**
     * @return Search of property
     */
    public ArrayList<Property> getSearch(PropertyType... propertyTypes){
        return propertyController.filterProperties(moneyLimit,areaLimit,floorsLimit,roomsLimit,propertyTypes);
    }

    public ArrayList<Property> getSearch(double min, double max, ArrayList<Property> listToFilter, String typeRoom){
        return propertyController.filterProperties(typeRoom, listToFilter,new DeltaNumber(min,max));
    }

    public PropertyType[] getPropertyTypes(){
        return propertyController.getPropertiesTypes();
    }

    //---------------SALE-------------
    /**
     * Sales report completed
     * @param days Amount of days before today to count
     * @return Relatory String
     */
    public ArrayList<Sale> getAprovedPurchases(int days) throws SaleIsFinalizedException {
        return saleController.getAprovedPurchases(days);
    }

    /**
     * @return Screen with unfinished purchases
     */
    public ArrayList<Sale> getVisualPendingPurchases() throws SaleIsFinalizedException {
        return saleController.getPendingPurchases();
    }
    /**
     * @return Screen with finished purchases
     */
    public ArrayList<Sale> getVisualAprovedPurchases() throws SaleIsFinalizedException {
        return saleController.getAprovedPurchases();
    }

    /**
     * Create a New Sale
     * @param method Method of pay
     * @param property Property to sale
     */
    public Sale newSale(Property property, int method) throws ProductNotAvaliableException, DataCannotBeAccessedException {
        return saleController.newSale(userController.getLoadedAccount(), method, property);
    }
    /**
     * Create a New Sale
     * @param method Method of pay
     * @param property Property to sale
     */
    public Sale newSale(Property property, int method, int months, int[] payday) throws ProductNotAvaliableException, DataCannotBeAccessedException, EventInvalidException, MonthsForRentOrFinanceInvalidException, InvalidCalendarDateException {
        return saleController.newSale(userController.getLoadedAccount(), method, property, months, payday);
    }

    public void deleteSale(Sale sale) throws DataCannotBeAccessedException {
        saleController.deleteSale(sale);
    }


    /**
     * Confima uma 'sale'
     * @param sale Sale to confime
     */
    public void confirmSale(Sale sale) throws SaleIsFinalizedException, DataCannotBeAccessedException {
        if(!sale.isAproved()){
            sale.setAproved(true);
            saleController.update();
        }
    }

    /**
     * Desconfirma uma 'sale'
     * @param sale Sale to unconfime
     */
    public void unconfirmSale(Sale sale) throws SaleIsFinalizedException, DataCannotBeAccessedException {
        if(sale.isAproved()){
            sale.setAproved(false);
            saleController.update();
        }
    }

    public void finalizeSale(Sale sale) throws DataCannotBeAccessedException {
        sale.finalizer();
        saleController.update();
    }

    public ArrayList<Sale> getSales(User user){
        return saleController.getSales(user);
    }

    public ArrayList<Sale> getLate(User user) {
        return saleController.getLate(user);
    }

    public ArrayList<Sale> getAproved(User user) {
        return saleController.getAproved(user);
    }

    public ArrayList<Sale> getNotAproved(User user) {
        return saleController.getNotAproved(user);
    }

    public ArrayList<Sale> getFinalizer(User user)  {
        return saleController.getFinalizer(user);
    }

    public int getSalesNumber(User user){
        return saleController.getSalesNumber(user);
    }

    public String getStatus(User user){
        return saleController.getStatus(user);
    }


}
