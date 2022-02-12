package helloworld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {    
    
    //Δήλωση των στοιχείων για τη σύνδεση με τη ΒΔ
        public static Connection connect(){
        String connectionString = "jdbc:derby:HelloWorld;create=true";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            Logger.getLogger(HelloWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
    
}
