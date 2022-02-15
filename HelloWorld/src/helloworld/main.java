/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworld;
import java.sql.*;
import static helloworld.DBConnector.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author vker
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //createTable();
        insertNewUser(1,"Vasilis","1234");
        //selectAll();
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
