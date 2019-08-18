package com.colin.attendeelogger.Controllers;

import com.colin.attendeelogger.Managers.AttendeeManager;
import com.colin.attendeelogger.Models.Attendee;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AttendeesController implements Initializable
{
    @FXML
    TextField firstName_TextField,lastName_TextField,phoneNumber_TextField,employerName_TextField;
    @FXML
    ChoiceBox<String> gender_ChoiceBox;

    @FXML
    TableView<Attendee> attendants_TableView;
    @FXML
    TableColumn<String, Attendee>firstName_TableColumn,lastName_TableColumn,gender_TableColumn,employer_TableColumn,phoneNumber_TableColumn;
    private AttendeeManager attendeeManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attendeeManager = new AttendeeManager();
        initChoiceBox();
        initTable();
    }

    private void initChoiceBox()
    {
        gender_ChoiceBox.getItems().addAll("Set Gender","M","F");
        gender_ChoiceBox.setValue("Set Gender");
    }

    private void initTable()
    {
         firstName_TableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
         lastName_TableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
         gender_TableColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
         employer_TableColumn.setCellValueFactory(new PropertyValueFactory<>("employerName"));
         phoneNumber_TableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
         populateTable();
    }

    @FXML
    private void insertAttendant(ActionEvent event)
    {

        if (gender_ChoiceBox.getValue().equals("Set Gender") || firstName_TextField.getText().isEmpty() ||
        lastName_TextField.getText().isEmpty()|| phoneNumber_TextField.getText().isEmpty() || employerName_TextField.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Whoops");
            alert.setHeaderText("Looks like you did something wrong");
            alert.setContentText("You have some empty field(s)");
            alert.show();
            return;
        }


        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String firstName = firstName_TextField.getText();
                String lastName = lastName_TextField.getText();
                String phoneNumber = phoneNumber_TextField.getText();
                String employerName = employerName_TextField.getText();
                String gender = gender_ChoiceBox.getValue();
                Attendee attendee = new Attendee(null,firstName,lastName,gender,employerName,phoneNumber);
                attendeeManager.insert(attendee);
                return null;
            }
        };

        new Thread(task).start();
        task.setOnSucceeded(e->{
            populateTable();

            firstName_TextField.clear();
            lastName_TextField.clear();
            phoneNumber_TextField.clear();
            employerName_TextField.clear();
            gender_ChoiceBox.setValue("Set Gender");
        });

        task.setOnFailed(e->
        {
            task.getException().printStackTrace();
        });
    }


    private void populateTable()
    {
        Task<ArrayList<Attendee>> task = new Task<ArrayList<Attendee>>() {
            @Override
            protected ArrayList<Attendee> call() throws Exception {
                return attendeeManager.getRecords();
            }
        };

        new Thread(task).start();
        task.setOnSucceeded(e->
        {
            attendants_TableView.getItems().clear();
            attendants_TableView.getItems().addAll(task.getValue());
        });

        task.setOnFailed(e->{
            task.getException().printStackTrace();
        });
    }

    @FXML
    public void removeAttendant(ActionEvent event)
    {
        if (attendants_TableView.getSelectionModel().getSelectedItem() == null)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("What you are about to do is irreversible");
        alert.setContentText("Are sure you want to this ?");
        alert.showAndWait();

        // Return if the user wishes to do otherwise
        if (alert.getResult() != ButtonType.OK)
            return;

        Attendee attendee = attendants_TableView.getSelectionModel().getSelectedItem();
        String id = attendee.getId();
        attendeeManager.remove(Integer.parseInt(id));
        populateTable();
    }
}
