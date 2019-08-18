package com.colin.attendeelogger.Managers;

import com.colin.attendeelogger.Models.Attendee;
import com.colin.attendeelogger.Utils.SQLiteHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendeeManager extends SQLiteHelper
{



    @Override
    public boolean insert(Object object) {

        Attendee attendee = (Attendee) object;
        String firstName = attendee.getFirstName();
        String lastName = attendee.getLastName();
        String gender = attendee.getGender();
        String phoneNumber = attendee.getPhoneNumber();
        String employer = attendee.getEmployerName();

        String sqlString = "INSERT INTO attendees(first_name,last_name,gender,employer_name,phone_number) " +
                "VALUES('"+firstName+"','"+lastName+"','"+gender+"','"+employer+"','"+phoneNumber+"')";

        try {
            return statement.execute(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Object object) {
        // TODO : Implement this method
        return false;
    }

    @Override
    public boolean remove(int id) {
        String sqlString = "DELETE FROM attendees WHERE attendees.id = "+id;
        try {
            return statement.execute(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Attendee> getRecords() {
        ArrayList<Attendee> attendees = new ArrayList<>();
        try
        {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM attendees");
            while (resultSet.next())
            {
                String id = resultSet.getString("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                String phoneNumber = resultSet.getString("phone_number");
                String employerName = resultSet.getString("employer_name");
                Attendee attendee = new Attendee(id,firstName,lastName,gender,employerName,phoneNumber);
                attendees.add(attendee);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return attendees;
    }
}
