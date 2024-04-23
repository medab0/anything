package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MySQLConnection{

    public static final String URL =
            "jdbc:mysql://localhost:3306/anything";

    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    static Connection getConnection(){

        Connection c = null;
        try
        {

            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            System.out.println("DB Connected");
        }catch (SQLException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
