package imo;

import imo.exception.ParameterOutOfTypeException;
import imo.property.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private Stage clientStage;
    private Stage realtorStage;
    private Stage REMStage;
    private Pane rootLayout;

    private ImoController imoController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        demonstracaoPropriedades();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Login Screen - Imo Project");
        this.primaryStage.setResizable(false);
        initRootLayout();
        clientStage(new Stage());
    }

    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LoginScreen.fxml"));
            rootLayout = loader.load();
            LoginScreenController loginScreenController = loader.getController();
            loginScreenController.setMainApp(this);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void demonstracaoPropriedades(){
        //Cria propriedades para demonstracao
        PropertyRepository propertyRepository = PropertyRepository.getInstance();
        for(int u = 1; u < 10; u++){
            try {
                PropertyType propertyType = PropertyTypeRepository.getInstace().get("House"); //tipo generico de casa
                Property property = propertyRepository.createNewProperty(u * 10, u, 0, propertyType);
                property.setName("Nome da propriedade" + u);
                for (int i = 0; i < 2 * (u + 1); i++) {//floor
                    property.setBuyPrice((i + 1) * 1000 + property.getBuyPrice());
                    for (int j = 0; j < Room.values().length; j++) {//type room
                        for (int k = 0; k < (j * u / (i + 1 + u)); k++) {//rooms
                            Room room = Room.values()[j - 1];
                            room.setArea(u * 10.0 / 11.0 * ((j + 1.0) / (i + 1.0)));
                            property.addRoom(room);
                        }
                    }
                }
            }catch (ParameterOutOfTypeException parameterOutOfTypeException) {
                System.out.println(parameterOutOfTypeException.getMessage());
            }
        }
    }
    private void clientStage(Stage clientStage){
        this.clientStage = clientStage;
        this.clientStage.setTitle("Client Screen - Imo Project");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ImoScreen.fxml"));
            Scene scene = new Scene(loader.load());
            imoController = loader.getController();
            imoController.setMainScene(scene);
            this.clientStage.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void showClientScreem(){
        if(this.clientStage != null){
            this.clientStage.show();
            imoController.reloadUserInfo();
        }
    }
    public void hideLoginScreen(){
        primaryStage.hide();
    }
    public static void main(String[] args){
        launch(args);
    }
}
