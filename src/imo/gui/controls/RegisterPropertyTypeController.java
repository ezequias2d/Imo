package imo.gui.controls;

import elfoAPI.exception.data.DataCannotBeAccessedException;
import imo.Imobily;
import imo.gui.view.UserInputFX;
import imo.property.PropertyType;
import imo.property.Room;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controle da tela de registrador de Property Type
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.5
 */
public class RegisterPropertyTypeController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea descriptionTextArea;

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

    private Scene mainScene;

    private Imobily imobily;

    private UserInputFX userInputFX;

    private Stage stage;

    private PropertyType propertyType;

    /**
     * Inicializador da interface Initialzable
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Seta sena princiapal
     * @param mainScene Main scene
     */
    public void setMainScene(Scene mainScene){
        this.mainScene = mainScene;
    }

    /**
     * Seta Imobily
     * @param imobily Imobily
     */
    public void setImobily(Imobily imobily){
        this.imobily = imobily;
    }

    /**
     * Seta UserInputFX
     * @param userInputFX UserInputFX
     */
    public void setUserInputFX(UserInputFX userInputFX){
        this.userInputFX = userInputFX;
    }

    /**
     * Seta stage
     * @param stage Stage
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * Carrega proprertyType
     * @param propertyType PropertyType
     */
    public void load(PropertyType propertyType){
        String[] rooms = Room.TYPE_NAME;
        String[] roomsTypesNames = {"bedroom","livingroom","diningroom","kitchen","closet","bathroom","garage",
                "office", "garden", "balcony", "lobby"};
        for(int i = 0; i < roomsTypesNames.length; i++){
            TextField textFieldMin = (TextField) mainScene.lookup("#" + roomsTypesNames[i] + "MinTextField");
            TextField textFieldMax = (TextField) mainScene.lookup("#" + roomsTypesNames[i] + "MaxTextField");
            textFieldMin.setText(String.format(Locale.US,"%.2f",propertyType.getRoom(rooms[i]).getMin()));
            textFieldMax.setText(String.format(Locale.US,"%.2f",propertyType.getRoom(rooms[i]).getMax()));
            userInputFX.onlyNumberTextField(textFieldMax);
            userInputFX.onlyNumberTextField(textFieldMin);
        }
        roomsMinTextField.setText(String.format(Locale.US,"%.2f",propertyType.getRooms().getMin()));
        roomsMaxTextField.setText(String.format(Locale.US,"%.2f",propertyType.getRooms().getMax()));

        areaMinTextField.setText(String.format(Locale.US,"%.2f",propertyType.getArea().getMin()));
        areaMaxTextField.setText(String.format(Locale.US,"%.2f",propertyType.getArea().getMax()));

        buyMinTextField.setText(String.format(Locale.US,"%.2f",propertyType.getBuyPrice().getMin()));
        buyMaxTextField.setText(String.format(Locale.US,"%.2f",propertyType.getBuyPrice().getMax()));

        rentMinTextField.setText(String.format(Locale.US,"%.2f",propertyType.getRentPrice().getMin()));
        rentMaxTextField.setText(String.format(Locale.US,"%.2f",propertyType.getRentPrice().getMax()));

        floorsMinTextField.setText(String.format(Locale.US,"%.2f",propertyType.getFloor().getMin()));
        floorsMaxTextField.setText(String.format(Locale.US,"%.2f",propertyType.getFloor().getMax()));

        userInputFX.onlyNumberTextField(roomsMinTextField, roomsMaxTextField,
                areaMinTextField, areaMaxTextField,
                buyMinTextField, buyMaxTextField,
                rentMinTextField, rentMaxTextField,
                floorsMinTextField,floorsMaxTextField);

        nameTextField.setText(propertyType.getName());
        descriptionTextArea.setText(propertyType.getDescription());
        this.propertyType = propertyType;
    }

    /**
     * Salva property type
     */
    @FXML
    private void save(){
        String[] rooms = Room.TYPE_NAME;
        String[] roomsTypesNames = {"bedroom","livingroom","diningroom","kitchen","closet","bathroom","garage",
                "office", "garden", "balcony", "lobby"};
        if(propertyType == null) {
            propertyType = new PropertyType(nameTextField.getText(), descriptionTextArea.getText());
            try {
                imobily.addPropertyType(propertyType);
            } catch (DataCannotBeAccessedException e) {
                userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
            }
        }else{
            propertyType.setName(nameTextField.getText());
            propertyType.setDescription(descriptionTextArea.getText());
        }
        for(int i = 0; i < roomsTypesNames.length; i++){
            TextField textFieldMin = (TextField) mainScene.lookup("#" + roomsTypesNames[i] + "MinTextField");
            TextField textFieldMax = (TextField) mainScene.lookup("#" + roomsTypesNames[i] + "MaxTextField");
            propertyType.setLimitRoom(rooms[i],intOfTextField(textFieldMin),intOfTextField(textFieldMax));
        }
        propertyType.setLimitRooms(intOfTextField(roomsMinTextField),intOfTextField((roomsMaxTextField)));
        propertyType.setLimitArea(intOfTextField(areaMinTextField),intOfTextField((areaMaxTextField)));
        propertyType.setLimitBuyPrice(intOfTextField(buyMinTextField),intOfTextField((buyMaxTextField)));
        propertyType.setLimitRentPrice(intOfTextField(rentMinTextField),intOfTextField((rentMaxTextField)));
        propertyType.setLimitFloor(intOfTextField(floorsMinTextField),intOfTextField((floorsMaxTextField)));

        try {
            imobily.updateProperty();
        } catch (DataCannotBeAccessedException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
        stage.close();
    }

    /**
     * transforma conteudo de textField em inteiro
     * @param textField TextField
     * @return Int value of textField
     */
    private int intOfTextField(TextField textField){
        return Integer.valueOf(textField.getText());
    }

}
