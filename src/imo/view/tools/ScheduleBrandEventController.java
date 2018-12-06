package imo.view.tools;

import elfo.calendar.CalendarTools;
import elfo.exception.calendar.EventInvalidException;
import elfo.exception.calendar.HourNotExistException;
import imo.functions.ImoCalendarFunctions;
import imo.property.Property;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ScheduleBrandEventController implements Initializable {
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField hourTextField;

    @FXML
    private Label labelStatus;


    private ImoCalendarFunctions imoCalendarFunctions;
    private Property property;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setImoCalendarFunctions(ImoCalendarFunctions imoCalendarFunctions){
        this.imoCalendarFunctions = imoCalendarFunctions;
    }
    public void setProperty(Property property){
        this.property = property;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void okButton(){
        int[] date = CalendarTools.convertToDate(datePicker.getValue().toString());
        int[] time = CalendarTools.convertToHour(hourTextField.getText());
        System.out.println(datePicker.getValue().toEpochDay());
        try {
            imoCalendarFunctions.eventBrand(property,date,time);
            stage.close();
        } catch (HourNotExistException | EventInvalidException e) {
            labelStatus.setText(e.getMessage());
        }
    }

}
