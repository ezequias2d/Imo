package imo.gui.controls;


import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.exception.user.UserInvalidException;
import elfoAPI.exception.user.UserIsRegistredException;
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
                        } else if(item.equals(0)) {
                            setText("ADM1");
                        } else if(item.equals(1)) {
                            setText("ADM2");
                        } else if(item.equals(2)) {
                            setText("NORMAL");
                        }
                    }
                };
            }
        };
        typeComboBox.setButtonCell(cellFactory.call(null));
        typeComboBox.setCellFactory(cellFactory);
        typeComboBox.setItems(FXCollections.observableArrayList(0,1,2));
    }
    @FXML
    private void register(){
        String fullName = fullNameTextField.getText();
        String cpf = cpfTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String password = passwordSigInTextField.getText();
        if(password.equals(userInput.getText("Confirm the Password"))){
            try {
                imobily.register(fullName,cpf,password,firstName,lastName, typeComboBox.getValue());
                userInput.showMessage("User Registered", "Information from the user " + firstName + " " + lastName, "The user is registered!");
                stage.close();
            } catch (UserIsRegistredException | UserInvalidException | DataCannotBeAccessedException e) {
                userInput.showMessage(e.getClass().getName(),e.getMessage(),e.getLocalizedMessage());
            }
        }
    }

    public void setImobily(Imobily imobily){
        this.imobily = imobily;
    }

    @FXML
    private void updateFirstLastName(){
        firstNameTextField.setText(UserTools.getFirstName(fullNameTextField.getText()));
        lastNameTextField.setText(UserTools.getLastName(fullNameTextField.getText()));
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setUserInput(UserInputFX userInput){
        this.userInput = userInput;
        userInput.cpfTextField(cpfTextField);
    }
}
