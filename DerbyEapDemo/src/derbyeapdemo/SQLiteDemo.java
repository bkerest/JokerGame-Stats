/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package derbyeapdemo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author talepis
 */
public class SQLiteDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createTableAndData();
        //insertNewUser(3, "Olga", "O12345");
        //selectAll();
        //System.out.println(selectLogin("ARIS", "A12345"));
    }
    
     static String selectLogin(String username, String password){
                   try {
             Connection connection = connect();
             String selectSQL = "select ID from D_USER where USERNAME=? and PASSWORD=?";
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, username);
             preparedStatement.setString(2, password);
             ResultSet resultSet = preparedStatement.executeQuery();
             String message;
             if(resultSet.next()){
                 //System.out.println("login success!");
                 message = "Login ok";
             }else{
                 message = "Login failed";
             }
             preparedStatement.close();
             connection.close();
             System.out.println("Done!");
             return message;
        } catch (SQLException ex) {
            Logger.getLogger(DerbyEapDemo.class.getName()).log(Level.SEVERE, null, ex);
        }  
          return "";
    }
    
    private static void selectAll(){
            try {
             Connection connection = connect();
             Statement statement = connection.createStatement();
             String selectSQL = "select * from D_USER";
             ResultSet resultSet = statement.executeQuery(selectSQL);
             while(resultSet.next()){
                 System.out.println(resultSet.getString("USERNAME")+","+resultSet.getString("PASSWORD"));
             }
             statement.close();
             connection.close();
             System.out.println("Done!");
        } catch (SQLException ex) {
            Logger.getLogger(DerbyEapDemo.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    private static void createTableAndData(){
        try {
             Connection connection = connect();
             String createTableSQL = "CREATE TABLE D_USER"
               + "(ID INTEGER NOT NULL PRIMARY KEY,"
               + "USERNAME VARCHAR(20),"
               + "PASSWORD VARCHAR(20))";
             Statement statement = connection.createStatement();
             statement.executeUpdate(createTableSQL);
             String insertSQL = "INSERT INTO D_USER VALUES(2,'PANTELIS','P12345')";
             statement.executeUpdate(insertSQL);
             statement.close();
             connection.close();
             System.out.println("Done!");
        } catch (SQLException ex) {
            Logger.getLogger(DerbyEapDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void insertNewUser(int id, String username, String password){
        try {
             Connection connection = connect();
             String insertSQL = "INSERT INTO D_USER VALUES(?,?,?)";
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
             preparedStatement.setInt(1, id);
             preparedStatement.setString(2, username);
             preparedStatement.setString(3, password);
             int count = preparedStatement.executeUpdate();
             if(count>0){
                 System.out.println(count+" record updated");
             }
             preparedStatement.close();
             connection.close();
             System.out.println("Done!");
        } catch (SQLException ex) {
            Logger.getLogger(DerbyEapDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Connection connect(){
        String connectionString = "jdbc:sqlite:derbyeap.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            Logger.getLogger(DerbyEapDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
}
