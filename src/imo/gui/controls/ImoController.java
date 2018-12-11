package imo.gui.controls;

import elfo.calendar.schedule.ScheduleEvent;
import elfo.exception.data.DataCannotBeAccessedException;
import elfo.exception.user.permission.UserInvalidPermissionException;
import elfo.users.UserController;
import imo.MainApp;
import imo.functions.*;
import imo.property.Property;
import imo.property.PropertyController;
import imo.property.PropertyType;
import imo.property.Room;
import imo.gui.UserInputFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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


    public ImoController(){
        imobily = new Imobily(new UserInputFX());
        userInputFX = new UserInputFX();
    }

    public void adm2(){
        registerMenu.setVisible(true);
    }
    public void adm1(){
        adm2();
        registerUserButton.setVisible(true);
        deleteAccountButton.setVisible(true);
        changeCpfButton.setVisible(true);
        changePasswordButton.setVisible(true);
    }

    public void loadPropertys(ArrayList<Property> properties){
        code.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getIdentity())));
        area.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getArea())));
        floors.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getFloors())));
        rooms.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getNumberOfRooms())));
        rentPrice.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getRentPrice())));
        buyPrice.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getBuyPrice())));
        this.tableViewProperty.setItems(FXCollections.observableArrayList(properties));
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
        loadPropertys(imobily.getSearch());
        loadPropertyTypes(imobily.getPropertyTypes());
        calendarComboBoxConfigure();
        propertyTypeComboBox.setValue(imobily.getPropertyTypes()[0]);
        calendarDatePicker.setValue(LocalDate.now());
        selectDate();
    }
    public void reset() {
        if(propertyTypeComboBox != null)
            propertyTypeComboBox.setValue(imobily.getPropertyTypes()[0]);
        if(calendarDatePicker != null) {
            calendarDatePicker.setValue(LocalDate.now());
            selectDate();
        }
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
        loader.setLocation(MainApp.class.getResource("gui/RegisterUserScreen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            RegisterUserController registerUserController = loader.getController();
            registerUserController.setStage(stage);
            registerUserController.setUserInput(userInputFX);
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
            stage.setScene(scene);
            stage.setTitle("Register Property");
            stage.show();
        } catch (IOException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void modifyProperty(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(MainApp.REGISTER_PROPERTY_SCREEN_URI.));
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
    private void buyButtonAction(){
        Property selectedProperty = tableViewProperty.getSelectionModel().getSelectedItem();
        //imobily.
    }
}
