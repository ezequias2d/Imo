package imo;

import elfo.calendar.CalendarTools;
import elfo.calendar.schedule.Schedule;
import elfo.calendar.schedule.ScheduleDay;
import elfo.calendar.schedule.ScheduleEvent;
import elfo.exception.user.UserInvalidPermissionException;
import elfo.users.UserController;
import imo.functions.ImoAccountFunctions;
import imo.functions.ImoCalendarFunctions;
import imo.functions.ImoPropertyFunctions;
import imo.functions.ImoSaleFunctions;
import imo.property.Property;
import imo.property.PropertyRepository;
import imo.property.PropertyType;
import imo.property.Room;
import imo.view.UserInputFX;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ImoController implements Initializable {
    /*
     * Funcionais
    * */
    private ImoAccountFunctions imoAccountFunctions;
    private ImoCalendarFunctions imoCalendarFunctions;
    private ImoPropertyFunctions imoPropertyFunctions;
    private ImoSaleFunctions imoSaleFunctions;

    private UserController userController;
    private PropertyRepository propertyRepository;
    private Scene mainScene;
    private UserInputFX userInputFX;
    /*
     *  Aba Calendario
     */
    @FXML
    private DatePicker calendarDatePicker;
    @FXML
    private ListView<ScheduleEvent> calendarListView;

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

    public ImoController(){
        userController = UserController.getInstance();
        propertyRepository = PropertyRepository.getInstance();
        userInputFX = new UserInputFX();
    }

    public void loadPropertys(ArrayList<Property> properties){
        code.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(propertyRepository.get(param.getValue()))));
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
        infoPropertyLabel.setText(tableViewProperty.getSelectionModel().getSelectedItem().toString());
    }
    @FXML
    private void buttonBrandEvent(){
        userInputFX.brandEvent(imoCalendarFunctions,tableViewProperty.getSelectionModel().getSelectedItem());
    }

    public void setMainScene(Scene scene){
        this.mainScene = scene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imoAccountFunctions = new ImoAccountFunctions(userInputFX);
        imoCalendarFunctions = new ImoCalendarFunctions();
        imoPropertyFunctions = new ImoPropertyFunctions();
        imoSaleFunctions = new ImoSaleFunctions();
        loadPropertys(propertyRepository.getProperties());
        loadPropertyTypes(propertyRepository.getPropertiesTypes());
        calendarDatePicker.setValue(LocalDate.now());
        selectDate();
    }

    private double doubleOfTextField(TextField textField){
        return Double.valueOf(textField.getText());
    }

    @FXML
    private void updateSearch(){
        imoPropertyFunctions.setAreaLimit(doubleOfTextField(areaMinTextField), doubleOfTextField(areaMaxTextField));
        imoPropertyFunctions.setFloorsLimit(doubleOfTextField(floorsMinTextField),doubleOfTextField(floorsMaxTextField));
        imoPropertyFunctions.setMoneyLimit(doubleOfTextField(buyMinTextField),doubleOfTextField(buyMaxTextField));
        imoPropertyFunctions.setRoomsLimit(doubleOfTextField(roomsMinTextField),doubleOfTextField(roomsMaxTextField));

        ArrayList<Property> properties = imoPropertyFunctions.getSearch(this.propertyTypeComboBox.getSelectionModel().getSelectedItem());
        Room[] rooms = Room.values();
        String[] roomsTypesNames = {"bedroom","livingroom","diningroom","kitchen","closet","bathroom","garage",
                                        "office", "garden", "balcony", "lobby"};
        for(int i = 0; i < roomsTypesNames.length; i++){
            TextField textFieldMin = (TextField) mainScene.lookup("#" + roomsTypesNames[i] + "MinTextField");
            TextField textFieldMax = (TextField) mainScene.lookup("#" + roomsTypesNames[i] + "MaxTextField");
            properties = imoPropertyFunctions.getSearch(doubleOfTextField(textFieldMin),doubleOfTextField(textFieldMax),
                    properties,rooms[i].getType());
        }
        loadPropertys(properties);
    }


    //Account tab Buttons
    @FXML
    private void changeYourPasswordButton(){
        try {
            imoAccountFunctions.changePassword();
            reloadUserInfo();
        } catch (IOException e) {
            userInputFX.msgBox(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void changeYourFullName(){
        try{
            imoAccountFunctions.changeFullName();
            reloadUserInfo();
        } catch (IOException e) {
            userInputFX.msgBox(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void changeYourFormalNameButton(){
        try{
            imoAccountFunctions.changeFormalName();
            reloadUserInfo();
        } catch (IOException e) {
            userInputFX.msgBox(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void deleteAccountButton(){
        try {
            imoAccountFunctions.deleteAccount();
        } catch (UserInvalidPermissionException e) {
            userInputFX.msgBox(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void changePasswordButton(){
        try {
            imoAccountFunctions.changePasswordADM1();
            reloadUserInfo();
        } catch (UserInvalidPermissionException | IOException e) {
            userInputFX.msgBox(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void changeCPFButton(){
        try{
            imoAccountFunctions.changeCpfADM1();
            reloadUserInfo();
        } catch (UserInvalidPermissionException | IOException e) {
            userInputFX.msgBox(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }

    public void reloadUserInfo(){
        this.labelAccountInfo.setText(imoAccountFunctions.getAccountInfo());
    }


    //aba Calendario

    private void updateListViewCalendar(ArrayList<ScheduleEvent> scheduleEvents){
        this.calendarListView.setItems(FXCollections.observableArrayList(scheduleEvents));
    }

    @FXML
    private void selectDate(){
        ScheduleDay day = imoCalendarFunctions.getDay(CalendarTools.convertToDate(calendarDatePicker.getValue()));
        updateListViewCalendar(day.getEvents());
    }
}
