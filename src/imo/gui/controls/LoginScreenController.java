package imo.gui.controls;

import elfo.exception.data.DataCannotBeAccessedException;
import elfo.exception.user.UserInvalidException;
import elfo.exception.user.UserIsRegistredException;
import elfo.users.User;
import elfo.users.UserController;
import elfo.users.UserTools;
import imo.MainApp;

import imo.gui.UserInputFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    @FXML
    private TextField cpfLogOnTextField;
    @FXML
    private PasswordField passwordLogOnTextField;


    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField cpfSigInTextField;
    @FXML
    private PasswordField passwordSigInTextField;


    private UserController userController;

    private MainApp mainApp;

    private UserInputFX userInputFX;

    public LoginScreenController(){
        userController = UserController.getInstance();
    }

    @FXML
    private void logOn(){
        if (userController.login(UserTools.stringToCpf(cpfLogOnTextField.getText()),passwordLogOnTextField.getText())) {
            mainApp.getLoginStage().hide();
            mainApp.getImoScreenStage().show();
            ImoController imoController = mainApp.getImoController();
            imoController.reloadUserInfo();
            if(userController.isADM1()){
                imoController.adm1();
            }else if(userController.isADM2()){
                imoController.adm2();
            }
            imoController.reset();
            imoController.configureTextField();
            resetText();
        }else{
            userInputFX.showMessage("Something wrong is not right","Something wrong is not right", "Verify that your information is correct");
        }

    }
    private void resetText(){
        cpfLogOnTextField.setText("");
        cpfSigInTextField.setText("");
        passwordLogOnTextField.setText("");
        passwordSigInTextField.setText("");
        fullNameTextField.setText("");
    }

    @FXML
    private void sigIn(){
        String fullName = fullNameTextField.getText();
        int[] cpf = UserTools.stringToCpf(cpfSigInTextField.getText());
        String firstName = UserTools.getFirstName(fullName);
        firstName = userInputFX.getText("First Name",firstName);
        String lastName = userInputFX.getText("Last Name",UserTools.getLastName(fullName));
        String password = passwordSigInTextField.getText();
        if(password.equals(userInputFX.getText("Confirm the Password"))){
            try {
                userController.registerNewUser(fullName,cpf,password,firstName,lastName, User.LEVEL_NORMAL);
                userInputFX.showMessage("User Registered", "Information from the user " + firstName + " " + lastName, "The user is registered!");
                resetText();
            } catch (UserIsRegistredException | UserInvalidException | DataCannotBeAccessedException e) {
                userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
            }

        }
    }

    @FXML
    private void initialize() {
        userInputFX.onlyNumberTextField(cpfLogOnTextField,cpfSigInTextField);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setUserInputFX(UserInputFX userInputFX){
        this.userInputFX = userInputFX;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
