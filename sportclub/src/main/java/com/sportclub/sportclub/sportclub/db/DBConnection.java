package com.sportclub.sportclub.sportclub.db;

import lombok.Getter;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection dbConnection;
    private  Connection connection;

    private DBConnection() throws SQLException{
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportclub2","root","Ijse@1234");
    }

    public static DBConnection getInstance() throws SQLException{
        if (dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }
}
