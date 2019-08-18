package com.colin.attendeelogger.Utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLiteHelper implements Operations
{
    private String url = "jdbc:sqlite:database/data.db";
    protected Connection connection;
    protected Statement statement;
    public SQLiteHelper()
    {
        File dataDir = new File("database");
        if (!dataDir.exists())
            dataDir.mkdir();
        connect();
    }

    // Synchronized because it is accessing the same resource
    private synchronized void connect()
    {
        try
        {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            boolean isCreated = statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "attendees(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "first_name VARCHAR(100)," +
                    "last_name VARCHAR(100)," +
                    "gender VARCHAR(1)," +
                    "employer_name VARCHAR(100)," +
                    "phone_number VARCHAR(100));");
            if (isCreated)
                System.out.println("Database and table is created successfully");
        } catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Something went wrong");
        }
    }
}
