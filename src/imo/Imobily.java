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
import elfoAPI.sale.ISaleRepository;
import elfoAPI.sale.Sale;
import elfoAPI.sale.SaleController;
import elfoAPI.users.User;
import elfoAPI.users.UserController;
import elfoAPI.users.UserTools;
import imo.exception.EventNotBrandException;
import imo.exception.ParameterOutOfTypeException;
import imo.exception.PropertyIsUnavailable;
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
 *      - Registrar novos tipos
 *
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.3
 */
public class Imobily {
    private UserController userController;

    private Schedule schedule;
    private IScheduleRepository scheduleRepository;
    private DeltaTime deltaTime;

    private PropertyController propertyController;

    private DeltaNumber buyLimimt;
    private DeltaNumber rentLimit;
    private DeltaNumber areaLimit;
    private DeltaNumber floorsLimit;
    private DeltaNumber roomsLimit;

    private SaleController saleController;


    /**
     *  Construtor de Imobily
     */
    public Imobily() throws DataCannotBeAccessedException {
        userController = UserController.getInstance();

        scheduleRepository = ScheduleRepository.getInstance();
        deltaTime = new DeltaTime(0,30);
        scheduleUpdate(CalendarTools.getCurrentYear());

        propertyController = PropertyController.getInstance();
        buyLimimt = new DeltaNumber();
        rentLimit = new DeltaNumber();
        areaLimit = new DeltaNumber();
        floorsLimit = new DeltaNumber();
        roomsLimit = new DeltaNumber();
        saleController = SaleController.getInstace();
    }

    //----------ACCOUNT
    public boolean login(String cpf, String password){
        return userController.login(UserTools.stringToCpf(cpf),password);
    }

    /**
     * Se e Manager(Gerente/ADM1)
     * @return True se sim
     */
    public boolean isManager(){
        return userController.isADM1();
    }

    /**
     * Se e RealEstateBroker(Corretor/ADM2)
     * @return True se sim
     */
    public boolean isRealEstateBroker(){
        return userController.isADM2();
    }

    /**
     * Se e consumer(Consumidor/NORMAL)
     * @return True se sim
     */
    public boolean isConsumer(){
        return userController.isNORMAL();
    }

    /**
     * Pega conta logada
     * @return Conta logada
     */
    public User getLoadedAccount(){
        return userController.getLoadedAccount();
    }

    /**
     * Pega informaçoes da conta logada
     * @return String Account Info
     */
    public String getAccountInfo(){
        return String.format("Full Name:%s\nFormal Name:%s\nCpf:%s\n",
                userController.getFullNameCurrent(),
                userController.getFormalNameCurrent(),
                UserTools.convertCpfToString(userController.getCpfCurrent()));
    }

    /**
     * Muda senha da conta atual
     * @param oldPassword Senha antiga
     * @param newPassword Nova senha
     */
    public void changePassword(String oldPassword, String newPassword) throws DataCannotBeAccessedException, UserInvalidPermissionException {
        userController.changeYourPassword(oldPassword,newPassword);
    }

    /**
     * Pega usuarios
     * @return Todos os usuarios
     */
    public User[] getUsers(){
        return userController.getUsers();
    }

    /**
     * Muda nome completo de um usuario
     * @param password Sennha do usuario
     * @param fullname Novo nome completo
     */
    public void changeFullName(String password, String fullname) throws DataCannotBeAccessedException, UserInvalidPermissionException {
        userController.changeYourName(password,fullname);
    }

    /**
     * Muda nome formal
     * @param password Senha
     * @param firstName Novo primeiro nome
     * @param lastName Novo sobrenome
     */
    public void changeFormalName(String password, String firstName, String lastName) throws DataCannotBeAccessedException, UserInvalidPermissionException {
        userController.changeYourFormalName(password,firstName,lastName);
    }

    /**
     * Muda senha de qualquer conta
     * @param adm1Password Senha de ADM1
     * @param cpf CPF da conta para mudar senha
     * @param newPassword Nova senha
     */
    public void changePasswordADM1(String adm1Password, int[] cpf, String newPassword) throws UserInvalidPermissionException, DataCannotBeAccessedException {
        userController.changePassword(userController.getUser(cpf),adm1Password,newPassword);
    }

    /**
     * Deleta quase qualquer conta
     * @param cpf CPF da conta
     * @param adm1Password Senha de ADM1
     */
    public void deleteAccount(int[] cpf, String adm1Password) throws UserInvalidPermissionException, DataCannotBeAccessedException {
        userController.deleteAccount(adm1Password,cpf);
    }

    /**
     * Faz logout
     */
    public void logout(){
        userController.logout();
    }

    /**
     * Registra novo usuario
     * @param fullName Nome completo
     * @param cpf CPF
     * @param password Senha
     * @param firstName Primeiro Nome
     * @param lastName Ultimo nome
     * @param userType Tipo do usuario
     */
    public void register(String fullName, String cpf, String password, String firstName, String lastName, int userType) throws UserIsRegistredException, UserInvalidException, DataCannotBeAccessedException {
        userController.registerNewUser(fullName,UserTools.stringToCpf(cpf),password,firstName,lastName,userType);
    }


    //-----------CALENDAR------------

    /**
     * Pega eventos de uma data ate passar 'n' dias
     * @param date Data
     * @param period Periodo(n dias)
     * @return Eventos
     */
    public ArrayList<ScheduleEvent> getPeriodEvents(LocalDate date, int period) throws DataCannotBeAccessedException {
        int[] dateInt = CalendarTools.convertToDate(date);
        ArrayList<ScheduleEvent> events = new ArrayList<ScheduleEvent>();
        for(int i = 0; i < period; i ++){
            ScheduleDay day = getDay(dateInt);
            events.addAll(day.getEvents());
            dateInt = CalendarTools.dateChanger(1,dateInt);
        }
        return events;
    }

    /**
     * Atualiza schedule para um ano especifico
     * @param year Ano
     */
    private void scheduleUpdate(int year) throws DataCannotBeAccessedException {
        schedule = scheduleRepository.get(userController.getCurrentIdentity(), year);
    }

    /**
     * Marca um evento de visita a propriedade
     * @param property Propriedade
     * @param date Data
     * @param time Horario
     */
    public void eventBrand(Property property, LocalDate date, String time) throws EventInvalidException, EventNotBrandException, HourNotExistException, DataCannotBeAccessedException, PropertyIsUnavailable {
        if(property.isAvaliable()) {
            int[] dateV = CalendarTools.convertToDate(date);
            int[] timeV = CalendarTools.convertToHour(time);
            eventBrand(property, dateV, timeV);
        } else{
            throw new PropertyIsUnavailable(property);
        }
    }

    /**
     * Marca evento de visita a propriedade
     * @param property Propriedade
     * @param date Data em int[]
     * @param time Horario em int[]
     */
    public void eventBrand(Property property, int[] date, int[] time) throws HourNotExistException, EventInvalidException, EventNotBrandException, DataCannotBeAccessedException {
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

    /**
     * Pega dia de uma data espeficica
     * @param date Data
     * @return Day of date
     */
    public ScheduleDay getDay(int[] date) throws DataCannotBeAccessedException {
        scheduleUpdate(date[2]);
        schedule.setDate(date[2], date[1], date[0]);
        ScheduleDay out = schedule.getDay();
        schedule.upgradeToCurrentDay();
        return out;
    }

    /**
     * Pega schedule atual
     * @return Schedule
     */
    public Schedule getSchedule(){
        return schedule;
    }

    //----------PROPERTY-------------

    /**
     * Seta limite de preço de compra
     * @param min Minimum
     * @param max Maximum
     */
    public void setBuyLimit(double min, double max){
        buyLimimt.setDelta(min, max);
    }

    /**
     * Seta limite de preço do aluguel
     * @param min Minimum
     * @param max Maximum
     */
    public void setRentLimit(double min, double max){
        rentLimit.setDelta(min, max);
    }

    /**
     * Seta limite de area
     * @param min Minimum
     * @param max Maximum
     */
    public void setAreaLimit(double min, double max){
        areaLimit.setDelta(min, max);
    }

    /**
     * Seta limite de andares
     * @param min Minimum
     * @param max Maximum
     */
    public void setFloorsLimit(double min, double max){
        floorsLimit.setDelta(min, max);
    }

    /**
     * Seta limite de quartos
     * @param min Minimum
     * @param max Maximum
     */
    public void setRoomsLimit(double min, double max){
        floorsLimit.setDelta(min, max);
    }

    /**
     * Deleta propriedade
     * @param property Property
     */
    public void deleteProperty(Property property) throws DataCannotBeAccessedException {
        propertyController.delete(property);
    }

    /**
     * Registra propriedade
     * @param rooms Rooms
     * @param floors Floors
     * @param area Terrain Area
     * @param buyPrice Buy Price
     * @param rentPrice Rent Price
     * @param propertyType Property Type
     * @param andress Andress
     */
    public void registerProperty(ArrayList<Room> rooms, int floors, double area, double buyPrice, double rentPrice, PropertyType propertyType, String andress) throws ParameterOutOfTypeException, DataCannotBeAccessedException {
        propertyController.createNewProperty(rooms,floors,area,buyPrice,rentPrice,propertyType, andress);
    }
    /**
     * Pega pesquisa
     * @param propertyTypes Property Types for filter
     * @param seeUnvaliable If it's to pick up unavailable
     * @return Search of property
     */
    public ArrayList<Property> getSearch(boolean seeUnvaliable,PropertyType... propertyTypes){
        return propertyController.filterProperties(seeUnvaliable,buyLimimt,rentLimit,areaLimit,floorsLimit,roomsLimit,propertyTypes);
    }

    /**
     * Filtra Lista de Property pelos Room's
     * @param min Minumum
     * @param max Maximum
     * @param listToFilter Lista para filtrar
     * @param typeRoom Tipo do Room
     * @return Lista filtrada
     */
    public ArrayList<Property> getSearch(double min, double max, ArrayList<Property> listToFilter, String typeRoom){
        return propertyController.filterProperties(typeRoom, listToFilter,new DeltaNumber(min,max));
    }

    /**
     * Pega vetor de tipos de propriedades para filtrar(com 'All' no meio)
     * @return PropertyTypes
     */
    public PropertyType[] getPropertyTypesFilter(){
        return propertyController.getPropertiesTypesFilter();
    }

    /**
     * Pega vetor de tipos de propriedades
     * @return PropertyTypes
     */
    public PropertyType[] getPropertyTypes(){
        return propertyController.getPropertiesTypes();
    }

    /**
     * Adiciona PropertyType
     * @param propertyType Property Type
     */
    public void addPropertyType(PropertyType propertyType) throws DataCannotBeAccessedException {
        propertyController.addPropertyType(propertyType);
        propertyController.update();
    }

    /**
     * Deleta Property Type
     * @param propertyType Property Type
     */
    public void deletePropertyType(PropertyType propertyType) throws DataCannotBeAccessedException {
        propertyController.deletePropertyType(propertyType);
        propertyController.update();
    }

    /**
     *Atualiza repositorio de Property
     */
    public void updateProperty() throws DataCannotBeAccessedException {
        propertyController.update();
    }

    //---------------SALE-------------

    /**
     * Sales report Approved
     * @return Sales Approved Report
     */
    public String getAprovedReport() {
        ArrayList<Sale> sales =  saleController.getAprovedPurchases();
        String out = "";
        double totalPrice = 0;
        for(Sale sale : sales){
            out += sale.toString() + "\n";
            totalPrice += sale.getPrice();
        }
        out += String.format("\nTotal:%.2f",totalPrice);
        return out;
    }

    /**
     * Sales report Pedding
     * @return Sales Pedding Report
     */
    public String getPeddingReport() {
        ArrayList<Sale> sales =  saleController.getPendingPurchases();
        String out = "";
        double totalPrice = 0;
        for(Sale sale : sales){
            out += sale.toString() + "\n";
            totalPrice += sale.getPrice();
        }
        out += String.format("\nTotal:%.2f",totalPrice);
        return out;
    }


    /**
     * Sales report Approved
     * @param days Days
     * @return Sales Approved Report
     */
    public String getAprovedReport(int days) {
        ArrayList<Sale> sales =  saleController.getAprovedPurchases(days);
        String out = "";
        double totalPrice = 0;
        for(Sale sale : sales){
            out += sale.toString() + "\n";
            totalPrice += sale.getPrice();
        }
        out += String.format("\nTotal:%.2f",totalPrice);
        return out;
    }

    /**
     * Sales report Pedding
     * @param days Days
     * @return Sales Pedding Report
     */
    public String getPeddingReport(int days) {
        ArrayList<Sale> sales =  saleController.getPendingPurchases(days);
        String out = "";
        double totalPrice = 0;
        for(Sale sale : sales){
            out += sale.toString() + "\n";
            totalPrice += sale.getPrice();
        }
        out += String.format("\nTotal:%.2f",totalPrice);
        return out;
    }


    /**
     * Create a New Sale
     * @param method Method of pay
     * @param property Property to sale
     * @return A new Sale
     */
    public Sale newSale(Property property, int method) throws ProductNotAvaliableException, DataCannotBeAccessedException {
        return saleController.newSale(userController.getLoadedAccount(), method, property);
    }
    /**
     * Create a New Sale for Rent or Finance(with payday)
     * @param method Method of pay
     * @param property Property to sale
     * @param payday  Payday
     * @param months Months to Payday
     * @return A new Sale
     */
    public Sale newSale(Property property, int method, int months, int[] payday) throws ProductNotAvaliableException, DataCannotBeAccessedException, EventInvalidException, MonthsForRentOrFinanceInvalidException, InvalidCalendarDateException {
        return saleController.newSale(userController.getLoadedAccount(), method, property, months, payday);
    }

    /**
     * Delete Sale
     * @param adm1Password ADM1 Password
     * @param sale Sale
     */
    public void deleteSale(Sale sale, String adm1Password) throws DataCannotBeAccessedException, UserInvalidPermissionException {
        saleController.deleteSale(sale,adm1Password);
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

    /**
     * Finaliza uma Sale
     * @param sale Sale
     */
    public void finalizeSale(Sale sale) throws DataCannotBeAccessedException {
        sale.finalizer();
        saleController.update();
    }

    /**
     * Pega sales de um usuario
     * @param user Usuario
     * @return Sales do usuario
     */
    public ArrayList<Sale> getSales(User user){
        return saleController.getSales(user);
    }

    /*
        Removido por nao ter dado tempo da implementa]ap grafica
     */
    /**
     * Pega sales atrasadas de um usuario
     * @param user Usuario
     * @return Sales atradadas do usuario
     */
    public ArrayList<Sale> getLate(User user) {
        return saleController.getLate(user);
    }

    /**
     * Pega sales aprovadas de um usuario
     * @param user Usuario
     * @return Sales aprovadas do usuario
     */
    public ArrayList<Sale> getAproved(User user) {
        return saleController.getAproved(user);
    }

    /**
     * Pega sales nao aprovadas de um usuario
     * @param user Usuario
     * @return Sales nao aprovadas do usuario
     */
    public ArrayList<Sale> getNotAproved(User user) {
        return saleController.getNotAproved(user);
    }

    /**
     * Pega sales finalizadas de um usuario
     * @param user Usuario
     * @return Sales finalizadas do usuario
     */
    public ArrayList<Sale> getFinalizer(User user)  {
        return saleController.getFinalized(user);
    }

    /**
     * Pega numero de Sales d eum usuario
     * @param user Usuario
     * @return Numero de Sales do usuario
     */
    public int getSalesNumber(User user){
        return saleController.getSalesNumber(user);
    }

    /**
     * Pega status do usuario em relaçao a sales
     * @param user Usuario
     * @return Status
     */
    public String getStatus(User user){
        return saleController.getStatus(user);
    }

    public void getPermission(String password, int type) throws UserInvalidPermissionException {
        userController.getPermission(password,type);
    }
}
