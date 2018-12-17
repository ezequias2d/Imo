package imo.gui.controls;

import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.exception.user.UserInvalidException;
import elfoAPI.exception.user.UserIsRegistredException;
import elfoAPI.users.User;
import elfoAPI.users.UserTools;
import imo.Imobily;
import imo.Run;

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

/**
 * Controle da tela de login
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.5
 */
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


    private Run run;

    private UserInputFX userInputFX;

    private Imobily imobily;


    /**
     * Evento do botao logOn
     * tenta logar usuario usando informaçoes de textfield e passwordfield
     */
    @FXML
    private void logOn(){
        if(imobily.login(cpfLogOnTextField.getText(),passwordLogOnTextField.getText())){
            run.getLoginStage().hide();
            run.getImoScreenStage().show();
            ImoController imoController = run.getImoController();
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

    /**
     * apaga textos das caixas de texto
     */
    private void resetText(){
        cpfLogOnTextField.clear();
        cpfSigInTextField.clear();
        passwordLogOnTextField.clear();
        passwordSigInTextField.clear();
        fullNameTextField.clear();
    }

    /**
     * Evento do botao sigIn
     * tenta registrar usuario usando informaçoes de textfield e passwordfield
     */
    @FXML
    private void sigIn(){
        String fullName = fullNameTextField.getText();
        String cpf = cpfSigInTextField.getText();
        String firstName = UserTools.getFirstName(fullName);
        firstName = userInputFX.getText("First Name",firstName);
        String lastName = userInputFX.getText("Last Name",UserTools.getLastName(fullName));
        String password = passwordSigInTextField.getText();
        if(password.equals(userInputFX.getPassword("Confirm the Password"))){
            try {
                imobily.register(fullName,cpf,password,firstName,lastName, User.LEVEL_NORMAL);
                userInputFX.showMessage("User Registered", "Information from the user " + firstName + " " + lastName, "The user is registered!");
                resetText();
            } catch (UserIsRegistredException | UserInvalidException | DataCannotBeAccessedException e) {
                userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
            }

        }
    }

    /**
     * Funçao de inicio que configure os passwordField
     */
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

    /**
     * Setea Run
     * @param run Run
     */
    public void setRun(Run run) {
        this.run = run;
    }

    /**
     * Seta UserInputFX
     * @param userInputFX UserInputFX
     */
    public void setUserInputFX(UserInputFX userInputFX){
        this.userInputFX = userInputFX;
        userInputFX.cpfTextField(cpfLogOnTextField,cpfSigInTextField);
    }

    /**
     * Seta fachada Imobily
     * @param imobily Imobily
     */
    public void setImobily(Imobily imobily){
        this.imobily = imobily;
    }

    /**
     * Inicializador da classe Initializable
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }
}
