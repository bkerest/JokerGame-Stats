package helloworld;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.drda.NetworkServerControl;

public class DBConnector {
    NetworkServerControl();
    
    
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
        
        public static NetworkServerControl() throws Exception{
            NetworkServerControl serverControl=new NetworkServerControl();
            serverControl.start(null);
            
        }
    
}
