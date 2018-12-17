package imo.gui.controls;


import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.exception.user.UserInvalidException;
import elfoAPI.exception.user.UserIsRegistredException;
import elfoAPI.exception.user.permission.UserInvalidPermissionException;
import elfoAPI.users.User;
import imo.Imobily;
import elfoAPI.users.UserTools;
import imo.gui.view.UserInputFX;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controle da tela de registro de usuario
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.4
 */
public class RegisterUserController implements Initializable {
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField cpfTextField;
    @FXML
    private PasswordField passwordSigInTextField;
    @FXML
    private ComboBox<Integer> typeComboBox;

    private UserInputFX userInput;

    private Stage stage;

    private Imobily imobily;

    /**
     * Inicializador da interface Initializable
     * e carrega typeComboBox com tipos de usuario
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Callback<ListView<Integer>, ListCell<Integer>> cellFactory = new Callback<ListView<Integer>, ListCell<Integer>>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> l) {
                return new ListCell<Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else if(item.equals(User.LEVEL_ADM1)) {
                            setText("ADM1");
                        } else if(item.equals(User.LEVEL_ADM2)) {
                            setText("ADM2");
                        } else if(item.equals(User.LEVEL_NORMAL)) {
                            setText("NORMAL");
                        }
                    }
                };
            }
        };
        typeComboBox.setButtonCell(cellFactory.call(null));
        typeComboBox.setCellFactory(cellFactory);
        typeComboBox.setItems(FXCollections.observableArrayList(0,1,2));
        typeComboBox.setValue(typeComboBox.getItems().get(2));
    }

    /**
     * Evento do botao register
     */
    @FXML
    private void register(){
        String fullName = fullNameTextField.getText();
        String cpf = cpfTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String password = passwordSigInTextField.getText();
        int type = typeComboBox.getValue();
        if(password.equals(userInput.getPassword("Confirm the Password"))){
            try {
                if(type == User.LEVEL_ADM1){
                    String passwordADM1 = userInput.getPassword("ADM1 Password");
                    imobily.getPermission(passwordADM1, type);
                }
                imobily.register(fullName, cpf, password, firstName, lastName, typeComboBox.getValue());
                userInput.showMessage("User Registered", "Information from the user " + firstName + " " + lastName, "The user is registered!");
                stage.close();
            } catch (UserIsRegistredException | UserInvalidException | DataCannotBeAccessedException | UserInvalidPermissionException e) {
                userInput.showMessage(e.getClass().getName(),e.getMessage(),e.getLocalizedMessage());
            }
        }
    }

    /**
     * Seta imobily
     * @param imobily
     */
    public void setImobily(Imobily imobily){
        this.imobily = imobily;
    }

    /**
     * Evento de atualizaçao dos nomes formais enquanto digita no TextField de full name
     */
    @FXML
    private void updateFirstLastName(){
        firstNameTextField.setText(UserTools.getFirstName(fullNameTextField.getText()));
        lastNameTextField.setText(UserTools.getLastName(fullNameTextField.getText()));
    }

    /**
     * Seta Stage
     * @param stage Stage
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * Seta userInputFX
     * @param userInputFX UserInputFX
     */
    public void setUserInputFX(UserInputFX userInputFX){
        this.userInput = userInputFX;
        userInputFX.cpfTextField(cpfTextField);
    }
}
