package imo;

import elfo.exception.data.DataCannotBeAccessedException;
import imo.gui.controls.ImoController;
import imo.gui.controls.LoginScreenController;
import imo.exception.ParameterOutOfTypeException;
import imo.property.*;
import imo.gui.UserInputFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static final String LOGIN_SCREEN_URI = "gui/view/LoginScreen.fxml";
    private static final String IMO_SCREEN_URI = "gui/view/ImoScreen.fxml";
    public static final String REGISTER_PROPERTY_SCREEN_URI = "gui/view/RegisterPropertyScreen.fxml";
    public static final String REGISTER_USER_SCREEN_URI = "gui/view/RegisterUserScreen.fxml";

    private Stage loginStage;
    private Stage imo;
    private Pane rootLayout;


    private UserInputFX userInputFX;
    private ImoController imoController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //demonstracaoPropriedades();
        this.loginStage = primaryStage;
        this.loginStage.setTitle("Login Screen - Imo Project");
        this.loginStage.setResizable(false);
        initRootLayout();
        clientStage(new Stage());
    }

    public void initRootLayout(){
        try{
            userInputFX = new UserInputFX();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(LOGIN_SCREEN_URI));
            rootLayout = loader.load();
            LoginScreenController loginScreenController = loader.getController();
            loginScreenController.setMainApp(this);
            loginScreenController.setUserInputFX(userInputFX);
            Scene scene = new Scene(rootLayout);
            loginStage.setScene(scene);
            loginStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private void clientStage(Stage clientStage){
        this.imo = clientStage;
        this.imo.setTitle("Client Screen - Imo Project");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(IMO_SCREEN_URI));
            Scene scene = new Scene(loader.load());
            imoController = loader.getController();
            imoController.setMainApp(this);
            imoController.setMainScene(scene);
            this.imo.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ImoController getImoController(){
        return imoController;
    }
    public Stage getLoginStage(){
        return loginStage;
    }
    public Stage getImoScreenStage(){
        return imo;
    }

    public static void main(String[] args){
        launch(args);
    }
}
