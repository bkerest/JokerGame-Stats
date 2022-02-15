package helloworld;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {
         
    //Δήλωση των στοιχείων για τη σύνδεση με τη ΒΔ
        public static Connection connect(){

        
        String connectionString = "jdbc:derby://localhost:1527/HelloWorld;create=true";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
        
        public static void createTable(){
            try (Connection connection = connect()) {
                String createTableSQL = "CREATE TABLE V_USER"
                        + "(ID INTEGER NOT NULL PRIMARY KEY,"
                        + "USERNAME VARCHAR(20),"
                        + "PASSWORD VARCHAR(20))";
                Statement statement = connection.createStatement();
                statement.close();
             System.out.println("Done!");
            } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
        public static void selectAll(){
            try (Connection connection = connect(); Statement statement = connection.createStatement()) {
                    String selectSQL = "select * from V_USER";
                    ResultSet resultSet = statement.executeQuery(selectSQL);
             System.out.println("Done!");
            } catch (SQLException ex) {
            Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null, ex);
        }    
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
}
