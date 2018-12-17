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

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.4
 */
public class Run extends Application {
    /*
     * Links para os FXML
     */
    private static final String LOGIN_SCREEN_URI = "gui/view/LoginScreen.fxml";
    private static final String IMO_SCREEN_URI = "gui/view/ImoScreen.fxml";
    public static final String REGISTER_PROPERTY_SCREEN_URI = "gui/view/RegisterPropertyScreen.fxml";
    public static final String REGISTER_PROPERTY_TYPE_SCREEN_URI = "gui/view/RegisterPropertyTypeScreen.fxml";
    public static final String REGISTER_USER_SCREEN_URI = "gui/view/RegisterUserScreen.fxml";
    public static final String PROPERTY_TYPES_SCREEN_URI = "gui/view/PropertyTypesScreen.fxml";

    private Stage loginStage;
    private Stage imoStage;

    private UserInputFX userInputFX;
    private ImoController imoController;
    private Imobily imobily;

    /**
     * Statrt de Application
     */
    @Override
    public void start(Stage primaryStage) {
        //demonstracaoPropriedades();
        this.loginStage = primaryStage;
        this.loginStage.setTitle("Login Screen - Imo Project");
        this.loginStage.setResizable(false);
        initRootLayout();
        imoStage(new Stage());

    }

    /**
     * Inicia layout de loginScreen
     */
    public void initRootLayout(){
        try{
            userInputFX = new UserInputFX();
            imobily = new Imobily();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Run.class.getResource(LOGIN_SCREEN_URI));
            Pane rootLayout = loader.load();
            LoginScreenController loginScreenController = loader.getController();
            loginScreenController.setRun(this);
            loginScreenController.setUserInputFX(userInputFX);
            loginScreenController.setImobily(imobily);
            Scene scene = new Scene(rootLayout);
            loginStage.setScene(scene);
            loginStage.show();
        }catch (IOException | DataCannotBeAccessedException e){
            userInputFX.showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
        }
    }

    /**
     * Carrega imoScreen
     * @param imoStage Stage
     */
    private void imoStage(Stage imoStage){
        this.imoStage = imoStage;
        this.imoStage.setTitle("Client Screen - Imo Project");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Run.class.getResource(IMO_SCREEN_URI));
            Scene scene = new Scene(loader.load());
            imoController = loader.getController();//loader.getController();
            imoController.setRun(this);
            imoController.setMainScene(scene);
            imoController.setImobily(imobily);
            imoController.setUserInputFX(userInputFX);
            imoController.start();
            this.imoStage.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Pega imoController
     * @return ImoController
     */
    public ImoController getImoController(){
        return imoController;
    }

    /**
     * Pega LoginStage
     * @return Login Stage
     */
    public Stage getLoginStage(){
        return loginStage;
    }

    /**
     * Pega ImoScreenStage
     * @return ImoScreen Stage
     */
    public Stage getImoScreenStage(){
        return imoStage;
    }


    /*
     * Executa programa
     */
    public static void main(String[] args){
        launch(args);
    }
}
