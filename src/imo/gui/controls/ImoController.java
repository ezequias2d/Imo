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
    private Label infoPropertyLabel;
    @FXML
    private ComboBox<PropertyType> propertyTypeComboBox;

    @FXML
    private Button updateSearchButton;
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
    @FXML
    private Button changeCpfButton;

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

    public void hideControlls(){
        registerMenu.setVisible(false);
        registerUserButton.setVisible(false);
        deleteAccountButton.setVisible(false);
        changeCpfButton.setVisible(false);
        changePasswordButton.setVisible(false);
        hboxSystem.setPrefHeight(0);
        userTableView.setPrefWidth(0);
        hboxSystem.setVisible(false);
        saleDeleteButton.setVisible(false);
    }
    public void normal(){
        hideControlls();
        loadSales(imobily.getSales(imobily.getLoadedAccount()));    //carega conta atual em System


        //Cria evento para botao F5 do teclado(atualizar)
        KeyCombination f5KeyCombination = new KeyCodeCombination(KeyCode.F5);
        Runnable f5Command = new Runnable() {
            @Override
            public void run() {
                updateSearch();
                loadSales(imobily.getSales(imobily.getLoadedAccount()));
                if(imobily.isRealEstateBroker() || imobily.isManager()){
                    loadUsers(imobily.getUsers());
                }
            }
        };
        mainScene.getAccelerators().put(f5KeyCombination,f5Command);
    }

    public void adm2(){
        normal();
        registerMenu.setVisible(true);
        loadUsers(imobily.getUsers());
        hboxSystem.setPrefHeight(35);
        hboxSystem.setVisible(true);
        userTableView.setPrefWidth(260);
    }
    public void adm1(){
        adm2();
        registerUserButton.setVisible(true);
        deleteAccountButton.setVisible(true);
        changeCpfButton.setVisible(true);
        changePasswordButton.setVisible(true);
        saleDeleteButton.setVisible(true);

    }

    public void setImobily(Imobily imobily){
        this.imobily = imobily;
    }

    public void setUserInputFX(UserInputFX userInputFX){
        this.userInputFX = userInputFX;
    }

    private void loadPropertys(ArrayList<Property> properties){
        code.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getIdentity())));
        area.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getArea())));
        floors.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getFloors())));
        rooms.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getNumberOfRooms())));
        rentPrice.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getRentPrice())));
        buyPrice.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getBuyPrice())));
        this.tableViewProperty.setItems(FXCollections.observableArrayList(properties));
    }

    private void loadUsers(User[] users){
        userType.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getTypeUser())));
        userName.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getFormalName())));
        userSales.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(imobily.getSalesNumber(param.getValue()))));
        userStatus.setCellValueFactory((param) -> new SimpleStringProperty(imobily.getStatus(param.getValue())));
        userStatus.setCellValueFactory((param) -> new SimpleStringProperty(imobily.getStatus(param.getValue())));
        this.userTableView.setItems(FXCollections.observableArrayList(users));
        this.userTableView.refresh();
    }
    private void loadSales(ArrayList<Sale> sales){
        saleCode.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getIdentity()));
        saleMethod.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getMethodText()));
        salePrice.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getPrice())));
        saleProperty.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getProductIdentity())));
        saleStatus.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getStatus())));
        this.saleTableView.setItems(FXCollections.observableArrayList(sales));
        this.subSaleTableView.refresh();
    }
    private void loadSubSales(Sale[] sales){
        if(sales != null) {
            subSaleDayPayment.setCellValueFactory((param) -> new SimpleStringProperty(CalendarTools.formatDate(param.getValue().getPayday())));
            subSaleStatus.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getStatus()));
            subsalePriceTableColumn.setCellValueFactory((param) -> new SimpleStringProperty(String.format("%.2f",param.getValue().getPrice())));
            this.subSaleTableView.setItems(FXCollections.observableArrayList(sales));
            this.subSaleTableView.setPrefSize(250,200);
            this.subSaleTableView.refresh();
        }else{
            this.subSaleTableView.setPrefSize(0,200);
        }
    }


    public void loadPropertyTypes(PropertyType[] propertyTypes){
        this.propertyTypeComboBox.setItems(FXCollections.observableArrayList(propertyTypes));
    }
    @FXML
    private void onClickPropertyList(){
        if(tableViewProperty.getSelectionModel().getSelectedItem() != null) {
            infoPropertyLabel.setText(tableViewProperty.getSelectionModel().getSelectedItem().toString());
        }
    }
    @FXML
    private void buttonBrandEvent(){
        userInputFX.brandEvent(imobily,tableViewProperty.getSelectionModel().getSelectedItem());
    }

    public void setMainScene(Scene scene){
        this.mainScene = scene;
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calendarComboBoxConfigure();
        calendarDatePicker.setValue(LocalDate.now());
    }
    public void reset() {
        if(propertyTypeComboBox != null)
            propertyTypeComboBox.setValue(imobily.getPropertyTypes()[0]);
        if(calendarDatePicker != null) {
            calendarDatePicker.setValue(LocalDate.now());
            selectDate();
        }
        selectDate();
    }
    public void start(){
        loadPropertys(imobily.getSearch());
        loadPropertyTypes(imobily.getPropertyTypes());
        propertyTypeComboBox.setValue(imobily.getPropertyTypes()[0]);
    }
    private double doubleOfTextField(TextField textField){
        return Double.valueOf(textField.getText());
    }
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

    @FXML
    private void updateSearch(){
        imobily.setAreaLimit(doubleOfTextField(areaMinTextField), doubleOfTextField(areaMaxTextField));
        imobily.setFloorsLimit(doubleOfTextField(floorsMinTextField),doubleOfTextField(floorsMaxTextField));
        imobily.setMoneyLimit(doubleOfTextField(buyMinTextField),doubleOfTextField(buyMaxTextField));
        imobily.setRoomsLimit(doubleOfTextField(roomsMinTextField),doubleOfTextField(roomsMaxTextField));

        ArrayList<Property> properties = imobily.getSearch(this.propertyTypeComboBox.getSelectionModel().getSelectedItem());
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
    @FXML
    private void logout(){
        imobily.logout();
        mainApp.getImoScreenStage().hide();
        mainApp.getLoginStage().show();
    }
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
            registerUserController.setUserInput(userInputFX);
            registerUserController.setImobily(imobily);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
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
            stage.setScene(scene);
            stage.setTitle("Modify Property");
            stage.show();
        } catch (IOException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
        updateSearch();
    }
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
    @FXML
    private void changeYourPasswordButton(){
        try {
            imobily.changePassword();
            reloadUserInfo();
        } catch (DataCannotBeAccessedException | UserInvalidPermissionException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void changeYourFullName(){
        try{
            imobily.changeFullName();
            reloadUserInfo();
        } catch (DataCannotBeAccessedException | UserInvalidPermissionException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void changeYourFormalNameButton(){
        try{
            imobily.changeFormalName();
            reloadUserInfo();
        } catch (DataCannotBeAccessedException | UserInvalidPermissionException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void deleteAccountAction(){
        try {
            imobily.deleteAccount();
        } catch (UserInvalidPermissionException | DataCannotBeAccessedException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void changePasswordAction(){
        try {
            imobily.changePasswordADM1();
            reloadUserInfo();
        } catch (UserInvalidPermissionException | DataCannotBeAccessedException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void changeCPFAction(){
        try{
            imobily.changeCpfADM1();
            reloadUserInfo();
        } catch (UserInvalidPermissionException | DataCannotBeAccessedException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }

    public void reloadUserInfo(){
        this.labelAccountInfo.setText(imobily.getAccountInfo());
    }


    //aba Calendario

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

    private void updateListViewCalendar(ArrayList<ScheduleEvent> scheduleEvents){
        dateColum.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDay().toString()));
        startColumn.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getHoraryString()));
        endColumn.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEndHoraryString()));
        eventColumn.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getText()));

        this.eventDayTableView.setItems(FXCollections.observableArrayList(scheduleEvents));
        this.eventDayTableView.refresh();
    }

    private void selectDate(){
        ArrayList<ScheduleEvent> events = imobily.getPeriodEvents(calendarDatePicker.getValue(),1);
        updateListViewCalendar(events);
    }

    private void updateCalendar7Days(){
        ArrayList<ScheduleEvent> events = imobily.getPeriodEvents(calendarDatePicker.getValue(),7);
        updateListViewCalendar(events);
    }

    private void updateCalendar30Days(){
        ArrayList<ScheduleEvent> events = imobily.getPeriodEvents(calendarDatePicker.getValue(),30);
        updateListViewCalendar(events);
    }


    @FXML
    private void selectionOptionsCalendar(){
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
    }

    //   >>>   SALE   <<<
    @FXML
    private void saleButtonAction(){
        Property selectedProperty = tableViewProperty.getSelectionModel().getSelectedItem();

        if(selectedProperty != null){
            userInputFX.sale(imobily,selectedProperty);
        }
        loadSales(imobily.getSales(imobily.getLoadedAccount()));
        loadUsers(imobily.getUsers());
    }

    @FXML
    private void selectUser(){
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            loadSales(imobily.getSales(selectedUser));
        }
    }
    @FXML
    private void selectSale(){
        Sale selectedSale = saleTableView.getSelectionModel().getSelectedItem();
        if(selectedSale != null) {
            loadSubSales(selectedSale.getSubSales());
        }else{
            loadSubSales(null);
        }
    }
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
    @FXML
    private void finalizerSaleAction(){
        Sale selectedSale = saleTableView.getSelectionModel().getSelectedItem();
        if(selectedSale != null){
            if(userInputFX.confirmationMessage("Do you want to finalize this purchase?","Do you want to disconfirm this purchase?" + "\n" + "After doing this you will not be able to recover the sale.")){
                selectedSale.finalizer();
                try {
                    imobily.finalizeSale(selectedSale);
                } catch (DataCannotBeAccessedException e) {
                    userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
                }
            }
        }
    }
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
}
