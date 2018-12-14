package imo.gui.controls;

import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.exception.user.UserInvalidException;
import elfoAPI.exception.user.UserIsRegistredException;
import elfoAPI.users.User;
import elfoAPI.users.UserController;
import elfoAPI.users.UserTools;
import imo.Imobily;
import imo.MainApp;

import imo.gui.view.UserInputFX;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

    private Imobily imobily;

    public LoginScreenController(){
        userController = UserController.getInstance();
    }

    @FXML
    private void logOn(){
        //if (userController.login(UserTools.stringToCpf(cpfLogOnTextField.getText()),passwordLogOnTextField.getText())) {
        if(imobily.login(cpfLogOnTextField.getText(),passwordLogOnTextField.getText())){
            mainApp.getLoginStage().hide();
            mainApp.getImoScreenStage().show();
            ImoController imoController = mainApp.getImoController();
            imoController.reloadUserInfo();
            if(imobily.isManager()){
                imoController.adm1();
            }else if(imobily.isRealEstateBroker()){
                imoController.adm2();
            }else if(imobily.isConsumer()){
                imoController.normal();
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
        if(password.equals(userInputFX.getPassword("Confirm the Password"))){
            try {
                userController.registerNewUser(fullName,cpf,password,firstName,lastName, User.LEVEL_NORMAL);
                userInputFX.showMessage("User Registered", "Information from the user " + firstName + " " + lastName, "The user is registered!");
                resetText();
            } catch (UserIsRegistredException | UserInvalidException | DataCannotBeAccessedException e) {
                userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
            }

        }
    }

    public void start() {
        passwordLogOnTextField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    logOn();
                }
            }
        });
        passwordSigInTextField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    sigIn();
                }
            }
        });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setUserInputFX(UserInputFX userInputFX){
        this.userInputFX = userInputFX;
        userInputFX.cpfTextField(cpfLogOnTextField,cpfSigInTextField);
    }
    public void setImobily(Imobily imobily){
        this.imobily = imobily;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }
}
