package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {    
    
    //Δήλωση των στοιχείων για τη σύνδεση με τη ΒΔ
        public static Connection connect(){
        String connectionString = "jdbc:derby:JokerStats;create=true";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
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
