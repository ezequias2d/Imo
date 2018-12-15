package imo;

import elfoAPI.exception.data.DataCannotBeAccessedException;
import imo.gui.controls.ImoController;
import imo.gui.controls.LoginScreenController;
import imo.gui.view.UserInputFX;
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
    public static final String REGISTER_PROPERTY_TYPE_SCREEN_URI = "gui/view/RegisterPropertyTypeScreen.fxml";
    public static final String REGISTER_USER_SCREEN_URI = "gui/view/RegisterUserScreen.fxml";
    public static final String PROPERTY_TYPES_SCREEN_URI = "gui/view/PropertyTypesScreen.fxml";

    private Stage loginStage;
    private Stage imo;
    private Pane rootLayout;


    private UserInputFX userInputFX;
    private ImoController imoController;
    private Imobily imobily;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //demonstracaoPropriedades();
        this.loginStage = primaryStage;
        this.loginStage.setTitle("Login Screen - Imo Project");
        this.loginStage.setResizable(false);
        initRootLayout();
        imoStage(new Stage());

    }

    public void initRootLayout(){
        try{
            userInputFX = new UserInputFX();
            imobily = new Imobily();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(LOGIN_SCREEN_URI));
            rootLayout = loader.load();
            LoginScreenController loginScreenController = loader.getController();
            loginScreenController.setMainApp(this);
            loginScreenController.setUserInputFX(userInputFX);
            loginScreenController.setImobily(imobily);
            Scene scene = new Scene(rootLayout);
            loginStage.setScene(scene);
            loginStage.show();
        }catch (IOException | DataCannotBeAccessedException e){
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }


    private void imoStage(Stage clientStage){
        this.imo = clientStage;
        this.imo.setTitle("Client Screen - Imo Project");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(IMO_SCREEN_URI));
            Scene scene = new Scene(loader.load());
            imoController = loader.getController();//loader.getController();
            imoController.setMainApp(this);
            imoController.setMainScene(scene);
            imoController.setImobily(imobily);
            imoController.setUserInputFX(userInputFX);
            imoController.start();
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
