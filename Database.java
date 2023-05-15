/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cs550;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bhavi
 */
public class Database {
    
    public void switchPanels(JLayeredPane layeredpane, JPanel panel) {
        layeredpane.removeAll();
        layeredpane.add(panel);
        layeredpane.repaint();
        layeredpane.revalidate();
    }
    
public void showFirstFrame() {

        JFrame frame = new JFrame();
        frame.setTitle("JFrame 1");
        frame.setVisible(true);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel serverIp_label = new JLabel("Server IP (Host name): ");
        frame.getContentPane().add(serverIp_label);
        serverIp_label.setBounds(70, 30, 160, 40);

        JTextField serverIp_textField = new JTextField();
        frame.getContentPane().add(serverIp_textField);
        serverIp_textField.setBounds(250, 40, 300, 25);

        JLabel serverName_label = new JLabel("Service Name: ");
        frame.getContentPane().add(serverName_label);
        serverName_label.setBounds(70, 90, 160, 40);

        JTextField serverName_textField = new JTextField();
        frame.getContentPane().add(serverName_textField);
        serverName_textField.setBounds(250, 100, 300, 25);

        JLabel portNumber_label = new JLabel("Port Number: ");
        frame.getContentPane().add(portNumber_label);
        portNumber_label.setBounds(70, 150, 160, 40);

        JTextField portNumber_textField = new JTextField();
        frame.getContentPane().add(portNumber_textField);
        portNumber_textField.setBounds(250, 160, 300, 25);

        JLabel userName_label = new JLabel("User Name: ");
        frame.getContentPane().add(userName_label);
        userName_label.setBounds(70, 210, 160, 40);

        JTextField userName_textField = new JTextField();
        frame.getContentPane().add(userName_textField);
        userName_textField.setBounds(250, 220, 300, 25);

        JLabel password_label = new JLabel("Password: ");
        frame.getContentPane().add(password_label);
        password_label.setBounds(70, 270, 160, 40);

        JTextField password_textField = new JTextField();
        frame.getContentPane().add(password_textField);
        password_textField.setBounds(250, 280, 300, 25);

        JButton button = new JButton("Connect");
        frame.getContentPane().add(button);
        button.setBounds(310, 360, 160, 25);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String serverIp = serverIp_textField.getText();
                String serverName = serverName_textField.getText();
                String portNumber = portNumber_textField.getText();
                String userName = userName_textField.getText();
                String password = password_textField.getText();

                if (Final.setDriver(serverIp, serverName, portNumber, userName, password)) {
                    frame.dispose();
                    showSecondFrame();
                } else {
                    System.out.println("Error");
                }
            }
        });
    }

    public void showSecondFrame() {

        JFrame frame = new JFrame();
        frame.setTitle("JFrame 2");
        frame.setVisible(true);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Query Text");
        label.setBounds(60, 40, 100, 30);
        frame.getContentPane().add(label);

        JTextField textField = new JTextField();
        frame.getContentPane().add(textField);
        textField.setBounds(170, 40, 750, 30);

        JButton button = new JButton("Execute");
        frame.getContentPane().add(button);
        button.setBounds(350, 90, 80, 30);

        JPanel viewPanel = new JPanel();
        viewPanel.setBounds(30, 150, 940, 400);
        frame.add(viewPanel);

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                viewPanel.removeAll();

                String sqlQuery = textField.getText();
                try {

                    if (sqlQuery.toUpperCase().startsWith("INSERT INTO") || sqlQuery.toUpperCase().startsWith("DELETE")
                            || sqlQuery.toUpperCase().startsWith("UPDATE")) {

                        Final.manipulateData(sqlQuery);

                    } else if (sqlQuery.toUpperCase().startsWith("SELECT")) {

                        // Object[] contains { resultSet, preparedStatement, connection };
                        Object[] objects = Final.fetchData(sqlQuery);

                        ResultSet resultSet = (ResultSet) objects[0];
                        PreparedStatement preparedStatement = (PreparedStatement) objects[1];
                        Connection connection = (Connection) objects[2];

                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        int columnCount = resultSetMetaData.getColumnCount();

                        DefaultTableModel tableModel = new DefaultTableModel();
                        for (int i = 1; i <= columnCount; i++) {
                            tableModel.addColumn(resultSetMetaData.getColumnName(i));
                        }

                        while (resultSet.next()) {
                            String[] tableData = new String[columnCount];

                            for (int i = 1; i <= columnCount; i++) {
                                tableData[i - 1] = resultSet.getString(i);
                            }

                            tableModel.addRow(tableData);
                        }

                        preparedStatement.close();
                        connection.close();

                        JScrollPane scrollPane = new JScrollPane();
                        scrollPane.setBounds(0, 0, 940, 400);
                        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                        viewPanel.add(scrollPane);

                        JTable table = new JTable(tableModel);
                        scrollPane.setViewportView(table);

                    } else {

                        System.out.println("INVALID SQL QUERY");

                    }
                } 
                    catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
