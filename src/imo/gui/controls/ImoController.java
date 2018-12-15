package imo.gui.controls;

import elfoAPI.calendar.CalendarTools;
import elfoAPI.calendar.schedule.ScheduleEvent;
import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.exception.sale.SaleIsFinalizedException;
import elfoAPI.exception.user.permission.UserInvalidPermissionException;
import elfoAPI.sale.Sale;
import elfoAPI.users.User;
import imo.Imobily;
import imo.MainApp;
import imo.property.Property;
import imo.property.PropertyType;
import imo.property.Room;
import imo.gui.view.UserInputFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * Controle da tela principal do projeto Imo
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.5
 */
public class ImoController implements Initializable {
    /*
     * Funcionais
    * */
    private Imobily imobily;
    private Scene mainScene;
    private UserInputFX userInputFX;
    private MainApp mainApp;
    /*
     *  Aba Calendario
     */
    @FXML
    private DatePicker calendarDatePicker;

    @FXML
    private TableView<ScheduleEvent> eventDayTableView;
    @FXML
    private TableColumn<ScheduleEvent, String> dateColum;
    @FXML
    private TableColumn<ScheduleEvent, String> startColumn;
    @FXML
    private TableColumn<ScheduleEvent, String> endColumn;
    @FXML
    private TableColumn<ScheduleEvent, String> eventColumn;

    @FXML
    private ComboBox<Integer> calendarComboBox;

    /*
       Filtragem
     */
    @FXML
    private CheckBox seeUnavailablecheckBox;
    @FXML
    private Label infoPropertyLabel;
    @FXML
    private ComboBox<PropertyType> propertyTypeComboBox;

    @FXML
    private TextField areaMinTextField;
    @FXML
    private TextField areaMaxTextField;
    @FXML
    private TextField floorsMinTextField;
    @FXML
    private TextField floorsMaxTextField;

    @FXML
    private TextField roomsMinTextField;
    @FXML
    private TextField roomsMaxTextField;


    @FXML
    private TextField buyMinTextField;
    @FXML
    private TextField buyMaxTextField;

    @FXML
    private TextField rentMinTextField;
    @FXML
    private TextField rentMaxTextField;

    // Lista de propriedades
    @FXML
    private TableView<Property> tableViewProperty;
    @FXML
    private TableColumn<Property,String> code;
    @FXML
    private TableColumn<Property,String> area;
    @FXML
    private TableColumn<Property,String> floors;
    @FXML
    private TableColumn<Property,String> rooms;
    @FXML
    private TableColumn<Property,String> rentPrice;
    @FXML
    private TableColumn<Property,String> buyPrice;

    // Account tab
    @FXML
    private Label labelAccountInfo;

    //ADM Objects
    @FXML
    private Menu registerMenu;
    @FXML
    private MenuItem registerUserButton;
    @FXML
    private Button deleteAccountButton;
    @FXML
    private Button changePasswordButton;

    //Sale Screen
    @FXML
    private Button saleDeleteButton;

    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> userType;
    @FXML
    private TableColumn<User, String> userStatus;
    @FXML
    private TableColumn<User, String> userName;
    @FXML
    private TableColumn<User, String> userSales;

    @FXML
    private TableView<Sale> saleTableView;
    @FXML
    private TableColumn<Sale, String> saleCode;
    @FXML
    private TableColumn<Sale, String> saleStatus;
    @FXML
    private TableColumn<Sale, String> salePrice;
    @FXML
    private TableColumn<Sale, String> saleMethod;
    @FXML
    private TableColumn<Sale, String> saleProperty;

    @FXML
    private TableView<Sale> subSaleTableView;
    @FXML
    private TableColumn<Sale, String> subSaleDayPayment;
    @FXML
    private TableColumn<Sale, String> subSaleStatus;
    @FXML
    private TableColumn<Sale, String> subsalePriceTableColumn;

    @FXML
    private HBox hboxSystem;

    /**
     * Esconde Controles de usuario
     */
    public void hideControlls(){
        registerMenu.setVisible(false);
        registerUserButton.setVisible(false);
        deleteAccountButton.setVisible(false);
        changePasswordButton.setVisible(false);
        hboxSystem.setPrefHeight(0);
        userTableView.setPrefWidth(0);
        hboxSystem.setVisible(false);
        saleDeleteButton.setVisible(false);
    }

    /**
     * Configura tela para tipo 'normal'(Cliente)
     */
    public void normal(){
        hideControlls();
        loadSales(imobily.getSales(imobily.getLoadedAccount()));    //carega conta atual em System


        //Cria evento para botao F5 do teclado(atualizar)
        KeyCombination f5KeyCombination = new KeyCodeCombination(KeyCode.F5);
        Runnable f5Command = new Runnable() {
            @Override
            public void run() {
                updateSearch();
                if(imobily.isRealEstateBroker() || imobily.isManager()){
                    User userSelected = userTableView.getSelectionModel().getSelectedItem();
                    if(userSelected != null){
                        loadSales(imobily.getSales(userSelected));
                    }
                    loadUsers(imobily.getUsers());
                }else{
                    loadSales(imobily.getSales(imobily.getLoadedAccount()));
                }
                Sale saleSelected = saleTableView.getSelectionModel().getSelectedItem();
                if(saleSelected != null){
                    loadSubSales(saleSelected.getSubSales());
                }
            }
        };
        mainScene.getAccelerators().put(f5KeyCombination,f5Command);
    }
    /**
     * Configura tela para tipo 'AM2'(Corretor)
     */
    public void adm2(){
        normal();
        registerMenu.setVisible(true);
        loadUsers(imobily.getUsers());
        hboxSystem.setPrefHeight(35);
        hboxSystem.setVisible(true);
        userTableView.setPrefWidth(260);
    }
    /**
     * Configura tela para tipo 'ADM1'(Gerente)
     */
    public void adm1(){
        adm2();
        registerUserButton.setVisible(true);
        deleteAccountButton.setVisible(true);
        changePasswordButton.setVisible(true);
        saleDeleteButton.setVisible(true);

    }
    /**
     * Seta controle Imobily
     * @param imobily Imobily
     */
    public void setImobily(Imobily imobily){
        this.imobily = imobily;
    }

    /**
     * Seta um UserInputFX
     * @param userInputFX UserInputFX
     */
    public void setUserInputFX(UserInputFX userInputFX){
        this.userInputFX = userInputFX;
    }

    /**
     * Carrega propriedades na tableView de propriedades
     * @param properties Properties
     */
    private void loadPropertys(ArrayList<Property> properties){
        code.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getIdentity())));
        area.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getArea())));
        floors.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getFloors())));
        rooms.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getNumberOfRooms())));
        rentPrice.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getRentPrice())));
        buyPrice.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getBuyPrice())));
        this.tableViewProperty.setItems(FXCollections.observableArrayList(properties));
    }

    /**
     * Carrega usuarios na tableView de usuarios
     * @param users Users
     */
    private void loadUsers(User[] users){
        userType.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getTypeUser())));
        userName.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getFormalName())));
        userSales.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(imobily.getSalesNumber(param.getValue()))));
        userStatus.setCellValueFactory((param) -> new SimpleStringProperty(imobily.getStatus(param.getValue())));
        userStatus.setCellValueFactory((param) -> new SimpleStringProperty(imobily.getStatus(param.getValue())));
        this.userTableView.setItems(FXCollections.observableArrayList(users));
        this.userTableView.refresh();
    }

    /**
     * Carrega sales na tableView de sales
     * @param sales Sales
     */
    private void loadSales(ArrayList<Sale> sales){
        saleCode.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getIdentity()));
        saleMethod.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMethodText()));
        salePrice.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getPrice())));
        saleProperty.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getProductIdentity())));
        saleStatus.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getStatus())));
        this.saleTableView.setItems(FXCollections.observableArrayList(sales));
        this.saleTableView.refresh();
    }

    /**
     * Carrega subSales na tableView de subSales
     * @param subSales SubSales
     */
    private void loadSubSales(Sale[] subSales){
        if(subSales != null) {
            subSaleDayPayment.setCellValueFactory((param) -> new SimpleStringProperty(CalendarTools.formatDate(param.getValue().getPayday())));
            subSaleStatus.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getStatus()));
            subsalePriceTableColumn.setCellValueFactory((param) -> new SimpleStringProperty(String.format("%.2f",param.getValue().getPrice())));
            this.subSaleTableView.setItems(FXCollections.observableArrayList(subSales));
            this.subSaleTableView.setPrefSize(250,200);
            this.subSaleTableView.refresh();
        }else{
            this.subSaleTableView.setPrefSize(0,200);
        }
    }

    /**
     * Carrega tipos de propriedades na tableView de tipos de propriedades
     * @param propertyTypes Property Types
     */
    public void loadPropertyTypes(PropertyType[] propertyTypes){
        this.propertyTypeComboBox.setItems(FXCollections.observableArrayList(propertyTypes));
    }

    /**
     * Evento de selecionar uma Property na tableView
     */
    @FXML
    private void onClickPropertyList(){
        if(tableViewProperty.getSelectionModel().getSelectedItem() != null) {
            infoPropertyLabel.setText(tableViewProperty.getSelectionModel().getSelectedItem().toString());
        }
    }
    /**
     * Evento do botao de marca evento(brand event)
     */
    @FXML
    private void buttonBrandEvent(){
        userInputFX.brandEvent(imobily,tableViewProperty.getSelectionModel().getSelectedItem());
    }

    /**
     * Seta sena principal
     * @param scene Scene
     */
    public void setMainScene(Scene scene){
        this.mainScene = scene;
    }

    /**
     * Seta MainApp
     * @param mainApp MainApp
     */
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    /**
     * Inicializador da interface Inicializable
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calendarComboBoxConfigure();
        calendarDatePicker.setValue(LocalDate.now());
    }

    /**
     * 'Reseta' interface
     */
    void reset() {
        if(propertyTypeComboBox != null)
            propertyTypeComboBox.setValue(imobily.getPropertyTypesFilter()[0]);
        if(calendarDatePicker != null) {
            calendarDatePicker.setValue(LocalDate.now());
            try {
                selectDate();
            } catch (DataCannotBeAccessedException e) {
                userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
            }
        }
    }

    /**
     * Inicializador de conteudo de controles
     */
    public void start(){
        loadPropertys(imobily.getSearch(false));
        loadPropertyTypes(imobily.getPropertyTypesFilter());
        propertyTypeComboBox.setValue(imobily.getPropertyTypesFilter()[0]);
    }

    /**
     * Pega texto de um TextField e o transforma em double
     * @param textField TextField
     * @return value of TextField
     */
    private double doubleOfTextField(TextField textField){
        return Double.valueOf(textField.getText());
    }

    /**
     * Configura os textFields da parte de filtro para aceitar apenas double
     */
    public void configureTextField(){
        String[] roomsTypesNames = {"bedroom","livingroom","diningroom","kitchen","closet","bathroom","garage",
                "office", "garden", "balcony", "lobby"};
        for(int i = 0; i < roomsTypesNames.length; i++) {
            TextField textFieldMin = (TextField) mainScene.lookup("#" + roomsTypesNames[i] + "MinTextField");
            TextField textFieldMax = (TextField) mainScene.lookup("#" + roomsTypesNames[i] + "MaxTextField");
            userInputFX.onlyNumberTextField(textFieldMin,textFieldMax);
        }
        userInputFX.onlyNumberTextField(areaMaxTextField,areaMinTextField,
                buyMaxTextField,buyMinTextField,
                floorsMaxTextField,floorsMinTextField,
                rentMaxTextField,rentMinTextField,
                roomsMaxTextField,roomsMinTextField);
    }

    /**
     * Evento de atualizar pesquisa
     * Associado a: botao update search, selecionar tipo de propriedade ou aperta F5
     */
    @FXML
    private void updateSearch(){
        imobily.setAreaLimit(doubleOfTextField(areaMinTextField), doubleOfTextField(areaMaxTextField));
        imobily.setFloorsLimit(doubleOfTextField(floorsMinTextField),doubleOfTextField(floorsMaxTextField));
        imobily.setBuyLimit(doubleOfTextField(buyMinTextField),doubleOfTextField(buyMaxTextField));
        imobily.setRentLimit(doubleOfTextField(rentMinTextField),doubleOfTextField(rentMaxTextField));
        imobily.setRoomsLimit(doubleOfTextField(roomsMinTextField),doubleOfTextField(roomsMaxTextField));

        ArrayList<Property> properties = imobily.getSearch(seeUnavailablecheckBox.isSelected(),this.propertyTypeComboBox.getSelectionModel().getSelectedItem());
        String[] rooms = Room.TYPE_NAME;
        String[] roomsTypesNames = {"bedroom","livingroom","diningroom","kitchen","closet","bathroom","garage",
                                        "office", "garden", "balcony", "lobby"};
        for(int i = 0; i < roomsTypesNames.length; i++){
            TextField textFieldMin = (TextField) mainScene.lookup("#" + roomsTypesNames[i] + "MinTextField");
            TextField textFieldMax = (TextField) mainScene.lookup("#" + roomsTypesNames[i] + "MaxTextField");
            properties = imobily.getSearch(doubleOfTextField(textFieldMin),doubleOfTextField(textFieldMax),
                    properties,rooms[i]);
        }
        loadPropertys(properties);
    }


    //Account tab Buttons

    /**
     * Logout
     * Associado a: botao logout no menu superior
     */
    @FXML
    private void logout(){
        imobily.logout();
        mainApp.getImoScreenStage().hide();
        mainApp.getLoginStage().show();
    }

    /**
     * Registra usuario
     * Associado a: Botao register no menu account no modo ADM1 ou ADM2
     */
    @FXML
    private void registerUser(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(MainApp.REGISTER_USER_SCREEN_URI));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            RegisterUserController registerUserController = loader.getController();
            registerUserController.setStage(stage);
            registerUserController.setUserInputFX(userInputFX);
            registerUserController.setImobily(imobily);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }

    /**
     * Cria uma nova janela de registrar propriedade
     * Associado a: Register no menu Property
     */
    @FXML
    private void registerProperty(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(MainApp.REGISTER_PROPERTY_SCREEN_URI));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            RegisterPropertyController registerPropertyController = loader.getController();
            registerPropertyController.setUserInputFX(userInputFX);
            registerPropertyController.setStage(stage);
            registerPropertyController.setImobily(imobily);
            stage.setScene(scene);
            stage.setTitle("Register Property");
            stage.show();
        } catch (IOException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
        updateSearch();
    }

    /**
     * Modifica propriedade selecionada
     * Associado a: Modify no menu Property(apenas para ADM1 e ADM2)
     */
    @FXML
    private void modifyProperty(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(MainApp.REGISTER_PROPERTY_SCREEN_URI));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            RegisterPropertyController registerPropertyController = loader.getController();
            registerPropertyController.setUserInputFX(userInputFX);
            registerPropertyController.setStage(stage);
            registerPropertyController.setProperty(tableViewProperty.getSelectionModel().getSelectedItem());
            registerPropertyController.setImobily(imobily);
            stage.setScene(scene);
            stage.setTitle("Modify Property");
            stage.show();
        } catch (IOException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
        updateSearch();
    }

    /**
     * Deleta propridade selecionada
     * Associado a: Delete no menu Property(apenas para ADM1)
     */
    @FXML
    private void deleteProperty(){
        Property property = tableViewProperty.getSelectionModel().getSelectedItem();
        if(property != null && userInputFX.confirmationMessage("Are you sure?","Are you sure? Delete Selection property?")){
            try {
                imobily.deleteProperty(property);
            } catch (DataCannotBeAccessedException e) {
                userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
            }
        }
        updateSearch();
    }

    /**
     * Cria tela e mostra de configuraçoes de tipo de propriedades
     * Associado a: Types no menu Property
     */
    @FXML
    private void typesScreen(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(MainApp.PROPERTY_TYPES_SCREEN_URI));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            PropertyTypesController propertyTypesController = loader.getController();
            propertyTypesController.setUserInputFX(userInputFX);
            propertyTypesController.setImobily(imobily);
            stage.setScene(scene);
            stage.setTitle("Modify Property");
            stage.showAndWait();
        } catch (IOException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
            e.printStackTrace();
        }
        loadPropertyTypes(imobily.getPropertyTypesFilter());
    }

    /**
     * Muda senha do usuario atual
     * Associado a: Change your password na aba Account
     */
    @FXML
    private void changeYourPasswordButton(){
        try {
            String oldPass = userInputFX.getPassword("Old Password");
            String newPass = userInputFX.getPassword("Old Password");
            imobily.changePassword(oldPass, newPass);
            reloadUserInfo();
        } catch (DataCannotBeAccessedException | UserInvalidPermissionException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    /**
     * Muda nome completo do usuario atual
     * Associado a: Change your full name na aba Account
     */
    @FXML
    private void changeYourFullName(){
        try{
            String pass = userInputFX.getPassword("Password");
            String fullName = userInputFX.getText("Full Name", imobily.getLoadedAccount().getFullName());
            imobily.changeFullName(pass,fullName);
            reloadUserInfo();
        } catch (DataCannotBeAccessedException | UserInvalidPermissionException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    /**
     * Muda nome formal do usuario atual
     * Associado a: Change your formal name na aba Account
     */
    @FXML
    private void changeYourFormalNameButton(){
        try{
            String pass = userInputFX.getPassword("Password");
            String first = userInputFX.getText("First Name", imobily.getLoadedAccount().getName());
            String last = userInputFX.getText("Last Name", imobily.getLoadedAccount().getLastName());
            imobily.changeFormalName(pass,first,last);
            reloadUserInfo();
        } catch (DataCannotBeAccessedException | UserInvalidPermissionException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    /**
     * Deleta conta
     * Associado a: Deleta account(ADM1)
     */
    @FXML
    private void deleteAccountAction(){
        try {
            String password = userInputFX.getPassword("ADM1 Password");
            int[] cpf = userInputFX.getCPF("CPF to delete:");
            imobily.deleteAccount(cpf, password);
        } catch (UserInvalidPermissionException | DataCannotBeAccessedException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    /**
     * Muda senha
     * Associado a: Deleta account(ADM1)
     */
    @FXML
    private void changePasswordAction(){
        try {
            String password = userInputFX.getPassword("ADM1 Password");
            int[] cpf = userInputFX.getCPF("CPF to delete:");
            String newPassword = userInputFX.getPassword("New Password");
            imobily.changePasswordADM1(password,cpf,newPassword);
            reloadUserInfo();
        } catch (UserInvalidPermissionException | DataCannotBeAccessedException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }

    /**
     * recarrega informaçoes do usuario na aba Account
     */
    public void reloadUserInfo(){
        this.labelAccountInfo.setText(imobily.getAccountInfo());
    }


    //aba Calendario

    /**
     * Configura combobox da aba calendar
     */
    private void calendarComboBoxConfigure(){
        Callback<ListView<Integer>, ListCell<Integer>> cellFactory = new Callback<ListView<Integer>, ListCell<Integer>>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> l) {
                return new ListCell<Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else if(item.equals(0)) {
                            setText("Selected Day");
                        } else if(item.equals(1)) {
                            setText("Next 7 Days");
                        } else if(item.equals(2)) {
                            setText("Next 30 days");
                        }
                    }
                };
            }
        };
        calendarComboBox.setButtonCell(cellFactory.call(null));
        calendarComboBox.setCellFactory(cellFactory);
        calendarComboBox.setItems(FXCollections.observableArrayList(0,1,2));
        calendarComboBox.setValue(0);
    }

    /**
     * Atualiza lista de eventos da aba calendar
     * @param scheduleEvents
     */
    private void updateListViewCalendar(ArrayList<ScheduleEvent> scheduleEvents){
        dateColum.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDay().toString()));
        startColumn.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getHoraryString()));
        endColumn.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEndHoraryString()));
        eventColumn.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getText()));

        this.eventDayTableView.setItems(FXCollections.observableArrayList(scheduleEvents));
        this.eventDayTableView.refresh();
    }

    /**
     * Atualiza lista de eventos da aba calendario para eventos do dia
     */
    private void selectDate() throws DataCannotBeAccessedException {
        ArrayList<ScheduleEvent> events = imobily.getPeriodEvents(calendarDatePicker.getValue(),1);
        updateListViewCalendar(events);
    }

    /**
     * Atualiza lista de eventos da aba calendario para eventos dos proximos 7 dias
     */
    private void updateCalendar7Days() throws DataCannotBeAccessedException {
        ArrayList<ScheduleEvent> events = imobily.getPeriodEvents(calendarDatePicker.getValue(),7);
        updateListViewCalendar(events);
    }

    /**
     * Atualiza lista de eventos da aba calendario para eventos dos proximos 30 dias
     */
    private void updateCalendar30Days() throws DataCannotBeAccessedException {
        ArrayList<ScheduleEvent> events = imobily.getPeriodEvents(calendarDatePicker.getValue(),30);
        updateListViewCalendar(events);
    }


    /**
     *  Ler combobox e descide o que mostrar na tableview de eventos de calendario
     *  Associado a: combobox, datepicker, refreshButton e tecla do teclado F5
     */
    @FXML
    private void selectionOptionsCalendar(){
        try {
            switch (calendarComboBox.getSelectionModel().getSelectedItem()) {
                case 0:
                    selectDate();
                    break;
                case 1:
                    updateCalendar7Days();
                    break;
                case 2:
                    updateCalendar30Days();
                    break;
            }
        } catch (DataCannotBeAccessedException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }

    //   >>>   SALE   <<<

    /**
     * Cria nova Sale
     * Associado a: botao sale em search
     */
    @FXML
    private void saleButtonAction(){
        Property selectedProperty = tableViewProperty.getSelectionModel().getSelectedItem();

        if(selectedProperty != null){
            userInputFX.sale(imobily,selectedProperty);
        }
        loadSales(imobily.getSales(imobily.getLoadedAccount()));
        loadUsers(imobily.getUsers());
    }

    /**
     * carrega sales na tableview de sales a parti do usuario selecionado
     * Associado a: tableview de usuarios
     */
    @FXML
    private void selectUser(){
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            loadSales(imobily.getSales(selectedUser));
        }
    }
    /**
     * carrega subsales ou nao na tableview de subsales a parti da sale selecionado
     * Associado a: tableview de sales
     */
    @FXML
    private void selectSale(){
        Sale selectedSale = saleTableView.getSelectionModel().getSelectedItem();
        if(selectedSale != null) {
            loadSubSales(selectedSale.getSubSales());
        }else{
            loadSubSales(null);
        }
    }
    /**
     * aprova sale selecionada
     * associado a: approve button
     */
    @FXML
    private void approveSaleAction(){
        Sale selectedSale = saleTableView.getSelectionModel().getSelectedItem();
        if(selectedSale != null){
            if(selectedSale.getMethod() == Sale.IN_RENT || selectedSale.getMethod() == Sale.IN_FINANCE){
                selectedSale = subSaleTableView.getSelectionModel().getSelectedItem();
                if(selectedSale != null && userInputFX.confirmationMessage("Do you want to confirm this purchase?","Do you want to confirm this purchase?")){
                    try {
                        imobily.confirmSale(selectedSale);
                    } catch (SaleIsFinalizedException | DataCannotBeAccessedException e) {
                        userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
                    }
                }
            }else if(userInputFX.confirmationMessage("Do you want to confirm this purchase?","Do you want to confirm this purchase?")){
                try {
                    imobily.confirmSale(selectedSale);
                } catch (SaleIsFinalizedException | DataCannotBeAccessedException e) {
                    userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
                }
            }
        }
    }
    /**
     * desaprova sale selecionada
     * associado a: disapprove button
     */
    @FXML
    private void disapproveSaleAction(){
        Sale selectedSale = saleTableView.getSelectionModel().getSelectedItem();
        if(selectedSale != null){
            if(selectedSale.getMethod() == Sale.IN_RENT || selectedSale.getMethod() == Sale.IN_FINANCE){
                selectedSale = subSaleTableView.getSelectionModel().getSelectedItem();
                if(selectedSale != null && userInputFX.confirmationMessage("Do you want to disconfirm this purchase?","Do you want to disconfirm this purchase?")){
                    try {
                        imobily.unconfirmSale(selectedSale);
                    } catch (SaleIsFinalizedException | DataCannotBeAccessedException e) {
                        userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
                    }
                }
            }else if(userInputFX.confirmationMessage("Do you want to disconfirm this purchase?","Do you want to disconfirm this purchase?")){
                try {
                    imobily.unconfirmSale(selectedSale);
                } catch (SaleIsFinalizedException | DataCannotBeAccessedException e) {
                    userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
                }
            }
        }
    }
    /**
     * finaliza sale selecionada
     * associado a: finalize button
     */
    @FXML
    private void finalizeSaleAction(){
        Sale selectedSale = saleTableView.getSelectionModel().getSelectedItem();
        if(selectedSale != null){
            if(userInputFX.confirmationMessage("Do you want to finalize this purchase?","Do you want to disconfirm this purchase?" + "\n" + "After doing this you will not be able to recover the sale.")){
                try {
                    selectedSale.finalizer();
                    imobily.finalizeSale(selectedSale);
                } catch (DataCannotBeAccessedException e) {
                    userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
                }
            }
        }
    }
    /**
     * deleta sale selecionada
     * associado a: delete button
     */
    @FXML
    private void saleDelete(){
        Sale selectedSale = saleTableView.getSelectionModel().getSelectedItem();
        if(selectedSale != null){
            try {
                imobily.deleteSale(selectedSale);
            } catch (DataCannotBeAccessedException e) {
                userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
            }
        }
    }
    /**
     * mosta relatorio de aprovados
     */
    @FXML
    private void saleReportAproved(){
        userInputFX.showRelatory(imobily.getAprovedReport());
    }
    /**
     * mosta relatorio de depentes
     */
    @FXML
    private void saleReportPending(){
        userInputFX.showRelatory(imobily.getPeddingReport());
    }
    /**
     * mosta relatorio de aprovados nos ultimos 30 dias
     */
    @FXML
    private void saleReportAproved30Days(){
        userInputFX.showRelatory(imobily.getAprovedReport(30));
    }
    /**
     * mosta relatorio de pedentes nos ultimos 30 dias
     */
    @FXML
    private void saleReportPending30Days(){
        userInputFX.showRelatory(imobily.getPeddingReport(30));
    }

    @FXML
    private void about(){
        userInputFX.about();
    }
}
