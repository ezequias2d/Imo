package imo.gui.controls;

import elfoAPI.exception.data.DataCannotBeAccessedException;
import imo.Imobily;
import imo.Run;
import imo.gui.view.UserInputFX;
import imo.property.PropertyType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controle de tela de Property Types
 * @author Jose Romulo Pereira
 * @version 0.0.5
 */
public class PropertyTypesController implements Initializable {

    @FXML
    private TableView<PropertyType> typesTableView;
    @FXML
    private TableColumn<PropertyType, String> nameColumn;
    @FXML
    private TableColumn<PropertyType, String> descriptionColumn;

    private Imobily imobily;
    private UserInputFX userInputFX;

    /**
     * Inicializador da classe Initializable
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Carrega tipos de propriedade no table view
     */
    private void loadTypes(PropertyType[] array){
        nameColumn.setCellValueFactory((param) -> new SimpleStringProperty(String.valueOf(param.getValue().getName())));
        descriptionColumn.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDescription()));
        typesTableView.setItems(FXCollections.observableArrayList(array));
        typesTableView.refresh();
    }

    /**
     * Seta Imobily
     * @param imobily Imobily
     */
    public void setImobily(Imobily imobily){
        this.imobily = imobily;
        loadTypes(imobily.getPropertyTypes());
    }

    /**
     * Seta UserInputFX
     * @param userInputFX UserInputFX
     */
    public void setUserInputFX(UserInputFX userInputFX){
        this.userInputFX = userInputFX;
    }

    /**
     * Evento para chamar ganela de criar nova property type
     */
    @FXML
    private void create(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Run.class.getResource(Run.REGISTER_PROPERTY_TYPE_SCREEN_URI));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            RegisterPropertyTypeController registerPropertyTypeController = loader.getController();
            registerPropertyTypeController.setUserInputFX(userInputFX);
            registerPropertyTypeController.setStage(stage);
            registerPropertyTypeController.setImobily(imobily);
            registerPropertyTypeController.setMainScene(scene);
            stage.setScene(scene);
            stage.setTitle("Register Property Type");
            stage.show();
        } catch (IOException e) {
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
        loadTypes(imobily.getPropertyTypes());
    }

    /**
     * Evento para remover Propeprty Type
     */
    @FXML
    private void remove(){
        PropertyType propertyType = typesTableView.getSelectionModel().getSelectedItem();
        if(propertyType != null && userInputFX.confirmationMessage("You sure?", "Delete selected Property Type?\nThis will delete all properties of this type!")){
            try {
                imobily.deletePropertyType(propertyType);
                loadTypes(imobily.getPropertyTypes());
            } catch (DataCannotBeAccessedException e) {
                userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
            }
        }
    }

    /**
     * Evento para chamar tela para editar Property Type
     */
    @FXML
    private void configure(){
        PropertyType propertyType = typesTableView.getSelectionModel().getSelectedItem();
        if(propertyType != null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Run.class.getResource(Run.REGISTER_PROPERTY_TYPE_SCREEN_URI));
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
                stage.setScene(scene);
                RegisterPropertyTypeController registerPropertyTypeController = loader.getController();
                registerPropertyTypeController.setMainScene(scene);
                registerPropertyTypeController.setUserInputFX(userInputFX);
                registerPropertyTypeController.setStage(stage);
                registerPropertyTypeController.setImobily(imobily);
                stage.show();
                registerPropertyTypeController.load(propertyType);
                stage.setTitle("Configure Property Type");
            } catch (IOException e) {
                userInputFX.showMessage(e.getClass().getName(), e.getClass().getName(), e.getMessage());
            }
            loadTypes(imobily.getPropertyTypes());
        }
    }

}

