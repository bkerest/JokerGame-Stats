/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworld;
import static helloworld.DBConnector.connect;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author vker
 */
public class HelloWorld {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        createTableAndData();
        insertNewUser(3,"Vasilis","1234");
        insertNewUser(4,"Giorgos","5678");
        //insertNewUser(5,"Giannis","8974");
        //selectAll();
        //System.out.println(selectLogin("ARIS","2456"));
        //System.out.println(selectLogin("Vasilis","1234"));
    }
    
        private static void selectAll(){
            try {
             Connection connection = connect();
             Statement statement = connection.createStatement();
             String selectSQL = "select * from V_USER";
             ResultSet resultSet = statement.executeQuery(selectSQL);
             while(resultSet.next()){
                 System.out.println(resultSet.getString("USERNAME")+","+resultSet.getString("PASSWORD"));
             }
             statement.close();
             connection.close();
             System.out.println("Done!");
        } catch (SQLException ex) {
            Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
        
        private static String selectLogin(String username, String password){
                   try {
             Connection connection = connect();
             String selectSQL = "select ID from V_USER where USERNAME=? and PASSWORD=?";
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
            Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null, ex);
        }  
          return "";
    }
        
        private static void insertNewUser(int id, String username, String password){
        try {
             Connection connection = connect();
             String insertSQL = "INSERT INTO V_USER VALUES(?,?,?)";
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
            Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void createTableAndData(){
        try {
             Connection connection = connect();
             String createTableSQL = "CREATE TABLE V_USER"
               + "(ID INTEGER NOT NULL PRIMARY KEY,"
               + "USERNAME VARCHAR(20),"
               + "PASSWORD VARCHAR(20))";
             Statement statement = connection.createStatement();
             statement.executeUpdate(createTableSQL);
             String insertSQL = "INSERT INTO V_USER VALUES(2,'PANTELIS','P12345')";
             statement.executeUpdate(insertSQL);
             statement.close();
             connection.close();
             System.out.println("Done!");
        } catch (SQLException ex) {
            Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        }
