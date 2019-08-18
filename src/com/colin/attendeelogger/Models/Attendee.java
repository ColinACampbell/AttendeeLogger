package com.colin.attendeelogger.Models;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import javafx.beans.property.SimpleStringProperty;

public class Attendee
{
    private SimpleStringProperty id;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty gender;
    private SimpleStringProperty employerName;
    private SimpleStringProperty phoneNumber;


    public Attendee(@Nullable String id, @NotNull String firstName, @NotNull String lastName, @NotNull String gender, @NotNull String employerName, @NotNull String phoneNumber)
    {
        if (id == null)
            id = "";
        this.id = new SimpleStringProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.gender = new SimpleStringProperty(gender);
        this.employerName = new SimpleStringProperty(employerName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getId() {
        return id.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getEmployerName() {
        return employerName.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }
}
