package imo.gui.controls;

import elfoAPI.exception.data.DataCannotBeAccessedException;
import imo.exception.ParameterOutOfTypeException;
import imo.Imobily;
import imo.property.Property;
import imo.property.PropertyController;
import imo.property.PropertyType;
import imo.property.Room;
import imo.gui.view.UserInputFX;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterPropertyController implements Initializable {

    @FXML
    private TextField addressTextField;
    @FXML
    private TextField floorsTextField;
    @FXML
    private TextField areaTextField;
    @FXML
    private TextField rentTextField;
    @FXML
    private TextField buyTextField;
    @FXML
    private TableView<Room> roomsTableView;
    @FXML
    private TableColumn<Room,String> areaColumn;
    @FXML
    private TableColumn<Room,String> typeColumn;
    @FXML
    private ComboBox<PropertyType> propertyTypeComboBox;
    @FXML
    private Label areaLabel;


    private UserInputFX userInputFX;

    private ArrayList<Room> rooms;

    private Stage stage;

    private Property property;

    private Imobily imobily;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rooms = new ArrayList<Room>();
        loadComboBoxPropertyType(propertyTypeComboBox);
    }

    public void setUserInputFX(UserInputFX userInputFX){
        this.userInputFX = userInputFX;
    }

    public void setProperty(Property property){
        this.property = property;
        buyTextField.setText("" + property.getBuyPrice());
        rentTextField.setText("" + property.getRentPrice());
        floorsTextField.setText("" + property.getFloors());
        addressTextField.setText(property.getAndress());
        propertyTypeComboBox.setValue(property.getPropertyType());
        areaTextField.setText("" + property.getTerrainArea());
        this.rooms.addAll(property.getRooms());
        loadRooms(rooms);
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setImobily(Imobily imobily){
        this.imobily = imobily;
    }
    @FXML
    private void save(){
        double buyPrice = getValueTextField(buyTextField);
        double rentPrice = getValueTextField(rentTextField);
        int floors = (int)getValueTextField(floorsTextField);
        double terrainArea = getValueTextField(areaTextField);
        String andress = addressTextField.getText();
        PropertyType propertyType = propertyTypeComboBox.getSelectionModel().getSelectedItem();
        try {
            if(property == null) {
                imobily.registerProperty(rooms,floors,terrainArea,buyPrice,rentPrice,propertyType,andress);
            }else {
                property.setPropertyType(propertyType);
                property.setFloors(floors);
                property.setAndress(andress);
                property.replacingRooms(rooms);
                property.setBuyPrice(buyPrice);
                property.setRentPrice(rentPrice);
                property.setTerrainArea(terrainArea);
            }
            stage.close();
        } catch (ParameterOutOfTypeException | DataCannotBeAccessedException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }
    @FXML
    private void removeRoom(){
        if(userInputFX.confirmationMessage("Are you sure?","Are you sure? Delete Selection Room")){
            rooms.remove(roomsTableView.getSelectionModel().getSelectedItem());
            roomsTableView.setItems(FXCollections.observableArrayList(rooms));
            loadRooms(rooms);
        }
    }

    /**
     * Calcula valor de Y caso Y seja 0 baseado na Area e X(y = area/x)
     * Ou
     * Muda a area caso Y seja maior que zero
     *
     * @param main TextField aplicado(pode ser x)
     * @param secondary Texfield secundario(pode ser y)
     * @param tOut TextField de saida(area)
     */
    private void areaEscale(TextField main, TextField secondary, TextField tOut){
        main.focusedProperty().addListener((ov, oldV, newV) -> {
            if(!newV) {
                double s = getValueTextField(secondary);
                double m = getValueTextField(main);
                double out = getValueTextField(tOut);
                if (s == 0 && m != 0 && out > 0) {
                    secondary.setText(format(out / m));
                } else if (s > 0) {
                    tOut.setText(format(m * s));
                } else if(s < 0){
                    main.setText("0.0");
                    tOut.setText("0.0");
                }
            }
        });
    }

    private String format(double num){
        return String.format(Locale.US,"%.2f",num);
    }

    /**
     * Quando a area e alterada, escalona X(elemenent1) e Y(element2) para dar a area
     * @param element1 Elemento 1 que compoe a area
     * @param element2 Elemento 2 que compoe a area
     * @param tOut Elemento de Area
     */
    private void areaEscaleOut(TextField element1, TextField element2, TextField tOut){
        tOut.focusedProperty().addListener((ov, oldV, newV) -> {
            if(!newV) {
                double s = getValueTextField(element1);
                double m = getValueTextField(element2);
                double out = getValueTextField(tOut);
                if((s != 0) && (m != 0)){
                    double newS = Math.sqrt((out * s) / m);
                    double newM = Math.sqrt((out * m) / s);
                    element1.setText(format(newS));
                    element2.setText(format(newM));
                }else{
                    element1.setText("0.0");
                    element2.setText("0.0");
                }
            }
        });
    }

    private boolean configureRoom(Room room){
        HBox hBox = new HBox();
        Dialog<Room> dialog = new Dialog<Room>();
        Label typeLabel = new Label("Room Type");
        VBox vBox = new VBox();
        Label xLabel = new Label("X");
        Label yLabel = new Label("Y");
        Label tLabel = new Label("Area");
        TextField xArea = new TextField();
        TextField yArea = new TextField();
        TextField tArea = new TextField();
        hBox.getChildren().addAll(xLabel,xArea,yLabel,yArea,tLabel,tArea);

        userInputFX.onlyNumberTextField(xArea,yArea,tArea);
        areaEscale(xArea,yArea,tArea);
        areaEscale(yArea,xArea,tArea);
        areaEscaleOut(xArea,yArea,tArea);

        ComboBox<Integer> typeComboBox = new ComboBox<Integer>();

        loadComboBoxRoom(typeComboBox);
        typeComboBox.setValue(0);
        for(int i = 0; i < Room.TYPE_NAME.length; i++){
            String typeString = Room.TYPE_NAME[i];
            if(typeString.equals(room.getType())){
                typeComboBox.setValue(i);
                break;
            }
        }

        if(room.getWidth() != -1){
            xArea.setText(room.getWidth() + "");
        }else{
            xArea.setText("0.0");
        }
        if(room.getLenght() != -1) {
            yArea.setText(room.getLenght() + "");
        }else{
            yArea.setText("0.0");
        }
        tArea.setText(room.getArea() + "");
        dialog.setTitle("Room Configure");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        vBox.getChildren().addAll(hBox,typeLabel,typeComboBox);
        vBox.setPadding(new Insets(16));
        vBox.setSpacing(5);

        hBox.setSpacing(5);

        dialog.getDialogPane().setContent(vBox);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(ButtonType.OK)) {
                double x = getValueTextField(xArea);
                double y = getValueTextField(yArea);
                double area = getValueTextField(tArea);
                String type = Room.TYPE_NAME[typeComboBox.getSelectionModel().getSelectedItem()];
                room.setType(type);
                if(x != 0 || y != 0){
                    room.setArea(x,y);
                }else{
                    room.setArea(area);
                }
                return room;
            }
            return null;
        });
        dialog.showAndWait();
        return dialog.getResult() != null;
    }

    @FXML
    private void createRoom(){
        Room room = new Room(Room.TYPE_NAME[0]);
        if(configureRoom(room)){
            rooms.add(room);
            loadRooms(rooms);
        }
    }

    @FXML
    private void configure(){
        Room selected = roomsTableView.getSelectionModel().getSelectedItem();
        if(configureRoom(selected)){
            roomsTableView.refresh();
        }
    }

    private double getValueTextField(TextField textField){
        if(textField.getText().equals("")){
            return 0;
        }else{
            return Double.valueOf(textField.getText());
        }
    }

    private void loadComboBoxRoom(ComboBox<Integer> roomComboBox){
        Callback<ListView<Integer>, ListCell<Integer>> cellFactory = new Callback<ListView<Integer>, ListCell<Integer>>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> l) {
                return new ListCell<Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setText(Room.TYPE_NAME[item]);
                        }
                    }
                };
            }
        };
        roomComboBox.setButtonCell(cellFactory.call(null));
        roomComboBox.setCellFactory(cellFactory);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for(int i = 0; i < Room.TYPE_NAME.length; i++){
            arrayList.add(i);
        }
        roomComboBox.setItems(FXCollections.observableArrayList(arrayList));
    }
    private void loadComboBoxPropertyType(ComboBox<PropertyType> roomComboBox){
        Callback<ListView<PropertyType>, ListCell<PropertyType>> cellFactory = new Callback<ListView<PropertyType>, ListCell<PropertyType>>() {
            @Override
            public ListCell<PropertyType> call(ListView<PropertyType> l) {
                return new ListCell<PropertyType>() {
                    @Override
                    protected void updateItem(PropertyType item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        };
        roomComboBox.setButtonCell(cellFactory.call(null));
        roomComboBox.setCellFactory(cellFactory);
        roomComboBox.setItems(FXCollections.observableArrayList(PropertyController.getInstance().getPropertiesTypes()));
    }
    private void loadRooms(ArrayList<Room> rooms){
        areaColumn.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getArea())));
        typeColumn.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getType()));
        roomsTableView.setItems(FXCollections.observableArrayList(rooms));
        double totalArea = 0;
        for(Room room : rooms){
            totalArea += room.getArea();
        }
        areaLabel.setText(String.format("Total area: %.2f", totalArea));
    }
}
