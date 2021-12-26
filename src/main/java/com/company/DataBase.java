package com.company;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    public Connection connection;

    DataBase() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
    }

    public void addInDataBase(Sport sport) throws SQLException {
        var statement = connection.prepareStatement(
                "INSERT INTO sport(`section`, `subsection`, `title`,`description`,`start`, `stop`,`address`,`participants`) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");{
            statement.setObject(1, sport.getSection());
            statement.setObject(2, sport.getSubsection());
            statement.setObject(3, sport.getTitle());
            statement.setObject(4, sport.getDescription());
            statement.setObject(5, sport.getStart());
            statement.setObject(6, sport.getStop());
            statement.setObject(7, sport.getAddress());
            statement.setObject(8, sport.getParticipants());
            statement.execute();
        }
    }
}
