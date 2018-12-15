package imo.gui.view;

import elfoAPI.calendar.CalendarTools;
import elfoAPI.exception.ElfoException;
import elfoAPI.exception.calendar.EventInvalidException;
import elfoAPI.exception.calendar.HourNotExistException;
import elfoAPI.exception.calendar.InvalidCalendarDateException;
import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.exception.sale.MonthsForRentOrFinanceInvalidException;
import elfoAPI.exception.sale.ProductNotAvaliableException;
import elfoAPI.sale.Sale;
import elfoAPI.users.UserTools;
import imo.exception.EventNotBrandException;
import imo.Imobily;
import imo.exception.PropertyIsUnavailable;
import imo.property.Property;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * GUI com usuario usando JavaFX
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.7
 */
public class UserInputFX {

    /**
     * Cria janela pedindo senha
     * @param mensager Mensagem
     * @return Senha
     */
    public String getPassword(String mensager) {
        HBox hBox = new HBox();
        Dialog<String> dialog = new Dialog<String>();
        PasswordField passwordField = new PasswordField();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        dialog.setContentText(mensager);
        dialog.setTitle(mensager);
        dialog.getDialogPane().getButtonTypes().addAll(passButton, ButtonType.CANCEL);

        passwordField.setPromptText("Password");

        hBox.getChildren().add(passwordField);
        hBox.setPadding(new Insets(16));
        HBox.setHgrow(passwordField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(hBox);
        Platform.runLater(passwordField::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(passButton)) {
                return passwordField.getText();
            }
            return null;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }

    /**
     * Cria janela pedindo um texto
     * @param mensager Mensagem
     * @return Texto
     */
    public String getText(String mensager) {
        return getText(mensager,"");
    }

    /**
     * Cria janela pedindo um texto e preenche o textField com o content
     * @param mensager Mensagem
     * @param content Conteudo
     * @return Texto
     */
    public String getText(String mensager, String content) {
        HBox hBox = new HBox();
        Dialog<String> dialog = new Dialog<String>();
        TextField textField = new TextField();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        dialog.setContentText(mensager);
        dialog.setTitle(mensager);
        dialog.getDialogPane().getButtonTypes().addAll(passButton, ButtonType.CANCEL);

        textField.setPromptText(mensager);
        textField.setText(content);

        hBox.getChildren().add(textField);
        hBox.setPadding(new Insets(16));
        HBox.setHgrow(textField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(hBox);
        Platform.runLater(textField::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(passButton)) {
                return textField.getText();
            }
            return null;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }


    /**
     * Cria janela pedindo um CPF
     * @param mensager Mensagem
     * @return CPF
     */
    public int[] getCPF(String mensager) {
        HBox hBox = new HBox();
        Dialog<String> dialog = new Dialog<String>();
        TextField textField = new TextField();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        dialog.setContentText(mensager);
        dialog.setTitle("CPF");
        dialog.getDialogPane().getButtonTypes().addAll(passButton, ButtonType.CANCEL);

        textField.setPromptText("CPF");

        hBox.getChildren().add(textField);
        hBox.setPadding(new Insets(16));
        HBox.setHgrow(textField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(hBox);
        Platform.runLater(textField::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(passButton)) {
                return textField.getText();
            }
            return null;
        });
        dialog.showAndWait();
        return UserTools.stringToCpf(dialog.getResult());
    }


    /**
     * Mosta mensagem de alerta
     * @param title Titulo
     * @param header Corpo
     * @param content Conteudo
     * @return Texto
     */
    public void showMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Mostra mensagem pedindo confimaçao de açao
     * @param title Titulo
     * @param content Conteudo
     * @return Se aceito
     */
    public boolean confirmationMessage(String title, String content){
        Dialog<Boolean> dialog = new Dialog<Boolean>();
        dialog.setContentText(content);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(ButtonType.YES)) {
                return true;
            }
            return false;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }

    /**
     * Cria janela para marca evento
     * @param imobily Imobily
     * @param property Propriedade
     */
    public void brandEvent(Imobily imobily, Property property){
        VBox vBox = new VBox();
        Dialog<Boolean> dialog = new Dialog<Boolean>();

        Label dateLabel = new Label("Date");
        Label hourLabel = new Label("hour");
        TextField hour = new TextField();
        DatePicker date = new DatePicker();
        ButtonType button = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        dialog.setContentText("Brand Event");
        dialog.setTitle("Brand Event");
        dialog.getDialogPane().getButtonTypes().addAll(button, ButtonType.CANCEL);

        date.setValue(LocalDate.now());
        hour.setText("7:30");
        hour.setPromptText("00:00");

        vBox.setSpacing(8);

        vBox.getChildren().add(dateLabel);
        vBox.getChildren().add(date);
        vBox.getChildren().add(hourLabel);
        vBox.getChildren().add(hour);
        vBox.setPadding(new Insets(16));
        HBox.setHgrow(date, Priority.ALWAYS);

        dialog.getDialogPane().setContent(vBox);
        Platform.runLater(date::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(button)) {
                try {
                    imobily.eventBrand(property,date.getValue(),hour.getText());
                    return true;
                } catch (ElfoException e) {
                    showMessage(e.getClass().getSimpleName(),e.getClass().getSimpleName(),e.getMessage());
                }
            }
            return false;
        });
        dialog.showAndWait();
    }

    /**
     * Cria janela para fazer uma Sale
     * @param imobily Imobily
     * @param property Property
     */
    public void sale(Imobily imobily, Property property){
        VBox vBox = new VBox();
        Dialog<Boolean> dialog = new Dialog<Boolean>();

        Label saleTypeLabel = new Label("Sale type");
        ChoiceBox<String> saleTypeChoiceBox = new ChoiceBox<>();

        saleTypeChoiceBox.setItems(FXCollections.observableArrayList(Sale.METHODS));
        saleTypeChoiceBox.setValue(saleTypeChoiceBox.getItems().get(0));

        dialog.setContentText("Sale");
        dialog.setTitle("Sale");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.NEXT, ButtonType.CANCEL);

        vBox.setSpacing(8);
        vBox.setPadding(new Insets(16));

        vBox.getChildren().addAll(saleTypeLabel, saleTypeChoiceBox);

        VBox.setVgrow(saleTypeChoiceBox, Priority.ALWAYS);

        dialog.getDialogPane().setContent(vBox);
        Platform.runLater(saleTypeChoiceBox::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(ButtonType.NEXT)) {
                int selected = saleTypeChoiceBox.getSelectionModel().getSelectedIndex();
                if(selected == Sale.IN_RENT || selected == Sale.IN_FINANCE){
                    saleRentOrFinance(imobily,property,selected);
                }else{
                    try {
                        imobily.newSale(property,selected);
                    } catch (ProductNotAvaliableException | DataCannotBeAccessedException e) {
                        showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
                    }
                }
            }
            return false;
        });
        dialog.showAndWait();
    }

    /**
     * Cria janela para escolher tempo de alugel ou finança
     * @param imobily Imobily
     * @param property Property
     * @param method Method
     */
    private void saleRentOrFinance(Imobily imobily, Property property, int method){
        VBox vBox = new VBox();
        Dialog<Boolean> dialog = new Dialog<Boolean>();

        Label saleTypeLabel = new Label("Payday");
        DatePicker datePicker = new DatePicker();
        Label monthsLabel = new Label("Months");
        TextField monthsTextField = new TextField();

        onlyNumberTextField(monthsTextField);
        monthsTextField.setText("1");

        datePicker.setValue(LocalDate.now());

        dialog.setContentText("Sale - " + Sale.METHODS[method]);
        dialog.setTitle("Sale - " + Sale.METHODS[method]);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

        vBox.setSpacing(8);
        vBox.setPadding(new Insets(16));

        vBox.getChildren().addAll(saleTypeLabel, datePicker, monthsLabel, monthsTextField);

        VBox.setVgrow(datePicker, Priority.ALWAYS);

        dialog.getDialogPane().setContent(vBox);
        Platform.runLater(monthsTextField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(ButtonType.FINISH)) {
                int months = Integer.valueOf(monthsTextField.getText());
                int[] payday = CalendarTools.convertToDate(datePicker.getValue());
                try {
                    imobily.newSale(property,method,months,payday);
                } catch (ElfoException e) {
                    showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
                }
            }
            return false;
        });
        dialog.showAndWait();
    }

    /**
     * Aplica evento a alteraçao de texto de um TextField para aceitar apenas caracteres compativeis com Double
     * @param textFields TextFields
     */
    public void onlyNumberTextField(TextField... textFields){
        for(TextField textField : textFields) {
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String old, String newText) {
                    if (!newText.isEmpty()) {
                        try {
                            Double.parseDouble(newText);
                        } catch (NumberFormatException e) {
                            if (old != null) {
                                textField.setText(old);
                            } else {
                                textField.clear();
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * Aplica evento a alteraçao de texto de um TextField para aceitar apenas CPF
     * @param textFields TextFields
     */
    public void cpfTextField(TextField... textFields){
        for(TextField textField : textFields) {
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String old, String newText) {
                    if (!newText.isEmpty()) {
                        char textArray[] = newText.toCharArray();
                        if(textArray.length > UserTools.getCpfNull().length){
                            if (old != null) {
                                textField.setText(old);
                            } else {
                                textField.clear();
                            }
                        }
                        for (char c : textArray) {
                            try {
                                int i = Integer.parseInt(c + "");
                            } catch (NumberFormatException e) {
                                if (old != null) {
                                    textField.setText(old);
                                } else {
                                    textField.clear();
                                }
                                return;
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * Cria janela de TextArea preencido com o relatorio
     * @param mensager Mensagem
     */
    public void showRelatory(String mensager) {
        AnchorPane anchorPane = new AnchorPane();

        Dialog<String> dialog = new Dialog<String>();
        TextArea textArea = new TextArea();

        dialog.setContentText("Relatory");
        dialog.setTitle("Relatory");
        dialog.setWidth(800);
        dialog.setHeight(600);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        textArea.setPromptText(mensager);
        textArea.setText(mensager);

        anchorPane.getChildren().add(textArea);
        AnchorPane.setBottomAnchor(textArea, 0.0);
        AnchorPane.setLeftAnchor(textArea, 0.0);
        AnchorPane.setRightAnchor(textArea, 0.0);
        AnchorPane.setTopAnchor(textArea, 0.0);

        dialog.getDialogPane().setContent(anchorPane);
        Platform.runLater(textArea::requestFocus);
        dialog.showAndWait();
    }

    public void about(){
        AnchorPane anchorPane = new AnchorPane();

        Dialog<String> dialog = new Dialog<String>();
        Label label = new Label();

        dialog.setContentText("About");
        dialog.setTitle("About");
        dialog.setWidth(400);
        dialog.setHeight(500);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        String aboutText = "Imo Projet 1.0.2a" +
                "\nAlunos:" +
                "\n     Ezequias Moises dos Santos Silva" +
                "\n     Jose Romulo Pereira" +
                "\nDisciplina: POO" +
                "\nProfesora: Thaís Alves Burity Rocha"+
                "\nSemetre: 2018.2";
        label.setText(aboutText);
        label.setWrapText(true);

        anchorPane.getChildren().add(label);
        AnchorPane.setBottomAnchor(label, 16.0);
        AnchorPane.setLeftAnchor(label, 16.0);
        AnchorPane.setRightAnchor(label, 16.0);
        AnchorPane.setTopAnchor(label, 16.0);

        dialog.getDialogPane().setContent(anchorPane);
        Platform.runLater(label::requestFocus);
        dialog.showAndWait();
    }
}
