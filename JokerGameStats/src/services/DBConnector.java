package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @Fani
 * @Giannis
 * @Giorgos
 * @Vasilis
 */

public class DBConnector {    
    
    //Δήλωση των στοιχείων για τη σύνδεση με τη ΒΔ
    private final String DB_URL = "jdbc:derby://localhost:1527/JokerStats;create=true";
    private final String USER = "Joker";
    private final String PASS = "joker";
    
    //αντικείμενο για τη σύνδεση με τη ΒΔ
    Connection conn = null;
    
    /*
    μέθοδος για την εκκίνηση του Derby Network Server και τη σύνδεση με τη ΒΔ
    */
    public void connectToDB () {           
        /*
        Σταμάτημα και εκκίνηση του Network Server,
        πρώτα σταματάει για να τερματιστούν τυχόν ανοιχτές συνδέσεις
        */
        try {
            //δηλώνουμε το relative path του αρχείου
            String batFile = "resources/bin";
            //Ξεκινάμε νια νέα διεργασία
            Process process = Runtime.getRuntime().exec("cmd /c stopNetworkServer.bat", null, new File(batFile));
            //περιμένουμε να ολοκληρωθεί πριν ξεκινήσει η επόμενη
            process.waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        //εκκίνηση του derby network server        
        try {
            //ίδια λογική με παραπάνω
            String batFile = "resources/bin";
            /*
            η παράμετρος 'start /b' σημαίνει ότι το cmd θα εκκινήσει στο 
            παρασκήνιο χωρίς να είναι ορατό
            */            
            Process process = Runtime.getRuntime().exec("cmd /c start /b startNetworkServer.bat", null, new File(batFile));
            process.waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //σύνδεση με τη ΒΔ
        try {
            //με την παράμετρο 'create=true' κατασκευάζει μια νέα 
            //ΒΔ αν δεν βρει υπάρχουσα
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch(Exception e){
            //αναδυόμενο παράθυρο ενημέρωσης για σφάλμα
           JOptionPane.showMessageDialog(null, "ERROR while connecting to DB!!!");
        }
    }
    
    //μέθοδος για την κατασκευή του σχήματος της βάσης
    public void createTables() {        
        /*
        Διαβάζουμε το αρχείο με τις εντολές sql, το οποίο καταλαμβάνει
        πλέον μία γραμμή ανά εντολή.        
        */
        String sqlFile ="resources/init-table-script_V3.sql";
        BufferedReader br = null;
        String line = "";
        String sqlSplitBy = ";"; //Χρήση διαχωριστικού
        /*
        Πίνακας για προσωρινή αποθήκευση των strings που θα χωριστούν
        σε κάθε γραμμή. Στην πράξη, μόνο στην 1η θέση του πίνακα θα 
        εισάγονται δεδομένα, γιατί υπάρχει μόνο ένα ";" ανά γραμμή
        */
        String[] sql = new String[5];
        ArrayList<String> sqlScripts = new ArrayList<>();
        //χωρίζουμε το αρχείο σε επιμέρους εντολές 
        //και τις αποθηκεύουμε σε ένα ArrayList 
        try {
            br = new BufferedReader(new FileReader(sqlFile));
            //όσο υπάρχουν νέες γραμμές συνεχίζει
            while((line = br.readLine())!= null ) {
                //σπάσιμο σε substrings
                sql = line.split(sqlSplitBy);
                //και αποθήκευση στο arraylist
                sqlScripts.add(sql[0]);
            }            
        } catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
        } finally {
        //τελικά κλείνουμε το buffered reader
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //εκτελούμε μία μία τις εντολές για την κατασκευή του σχήματος της ΒΔ
        for(String s:sqlScripts) {
            System.out.println(s);
            try {
                //το κάνουμε μέ χρήση statements
                conn.createStatement().execute(s);
            } catch (SQLException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }
    
}
