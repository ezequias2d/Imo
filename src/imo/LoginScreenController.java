package imo;

import elfo.users.UserController;
import elfo.users.UserTools;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginScreenController {
    @FXML
    private TextField userInput;
    @FXML
    private PasswordField passInput;

    private UserController userController;

    private MainApp mainApp;

    public LoginScreenController(){
        userController = UserController.getInstance();

    }

    @FXML
    private void login(){
        if (userController.login(UserTools.stringToCpf(userInput.getText()),passInput.getText())) {
            mainApp.hideLoginScreen();
            mainApp.showClientScreem();
        }

    }

    @FXML
    private void initialize() {

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
