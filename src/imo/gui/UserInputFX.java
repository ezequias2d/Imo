package imo.gui;

import elfo.calendar.CalendarTools;
import elfo.exception.calendar.EventInvalidException;
import elfo.exception.calendar.HourNotExistException;
import elfo.users.IUserInput;
import elfo.users.UserTools;
import imo.exception.EventNotBrandException;
import imo.functions.ImoCalendarFunctions;
import imo.functions.Imobily;
import imo.property.Property;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class UserInputFX implements IUserInput {
    @FXML
    private TextField inputText;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private Stage mainStage;

    private final StringProperty out;

    public UserInputFX() {
        out = new SimpleStringProperty();
    }

    @Override
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

    @Override
    public String getText(String mensager) {
        return getText(mensager,"");
    }

    @Override
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

    @Override
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

    @Override
    public void showMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
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
}
