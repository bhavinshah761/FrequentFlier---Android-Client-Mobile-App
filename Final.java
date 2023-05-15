/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cs550;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author bhavi
 */
public class Final {
    public static Connection connection;
    public static String url;
    public static String userName;
    public static String password;

    public static Object[] fetchData(String sqlQuery) throws ClassNotFoundException, SQLException {

        Connection connection = DriverManager.getConnection(url, userName, password);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery(sqlQuery);

        return new Object[] { resultSet, preparedStatement, connection };
    }

    public static void manipulateData(String sqlQuery) throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager.getConnection(url, userName, password);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static boolean setDriver(String serverIp, String serverName, String portNumber, String userName,
            String password) {

        url = "jdbc:oracle:thin:@" + serverIp + ":" + portNumber + "/" + serverName;
        // url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection Successful.");
            Final.connection = connection;
            Final.userName = userName;
            Final.password = password;
            connection.close();

            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}

