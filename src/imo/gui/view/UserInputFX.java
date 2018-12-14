package imo.gui.view;

import elfoAPI.calendar.CalendarTools;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class UserInputFX {

    private TextField inputText;

    private PasswordField inputPassword;

    private Stage mainStage;

    private final StringProperty out;

    public UserInputFX() {
        out = new SimpleStringProperty();
    }


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


    public String getText(String mensager) {
        return getText(mensager,"");
    }



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


    public void showMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


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


    public double getNumber(String mensager, double content) {
        HBox hBox = new HBox();
        Dialog<Double> dialog = new Dialog<Double>();
        TextField textField = new TextField();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        dialog.setContentText(mensager);
        dialog.setTitle(mensager);
        dialog.getDialogPane().getButtonTypes().addAll(passButton, ButtonType.CANCEL);

        textField.setPromptText(mensager);
        textField.setText("" + content);
        onlyNumberTextField(textField);

        hBox.getChildren().add(textField);
        hBox.setPadding(new Insets(16));
        HBox.setHgrow(textField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(hBox);
        Platform.runLater(textField::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(passButton)) {
                return Double.valueOf(textField.getText());
            }
            return null;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }


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
                } catch (HourNotExistException | EventInvalidException | EventNotBrandException e) {
                    showMessage(e.getClass().getSimpleName(),e.getClass().getSimpleName(),e.getMessage());
                }
            }
            return false;
        });
        dialog.showAndWait();
    }

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

    private void saleRentOrFinance(Imobily imobily, Property property, int type){
        VBox vBox = new VBox();
        Dialog<Boolean> dialog = new Dialog<Boolean>();

        Label saleTypeLabel = new Label("Payday");
        DatePicker datePicker = new DatePicker();
        Label monthsLabel = new Label("Months");
        TextField monthsTextField = new TextField();

        onlyNumberTextField(monthsTextField);
        monthsTextField.setText("1");

        datePicker.setValue(LocalDate.now());

        dialog.setContentText("Sale - " + Sale.METHODS[type]);
        dialog.setTitle("Sale - " + Sale.METHODS[type]);
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
                    imobily.newSale(property,type,months,payday);
                } catch (ProductNotAvaliableException | DataCannotBeAccessedException | EventInvalidException | MonthsForRentOrFinanceInvalidException | InvalidCalendarDateException e) {
                    showMessage(e.getClass().getName(),e.getClass().getName(),e.getMessage());
                }
            }
            return false;
        });
        dialog.showAndWait();
    }

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
}
