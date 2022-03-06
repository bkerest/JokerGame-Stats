package Tools;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import org.apache.derby.drda.NetworkServerControl;

/**
 * @author Vasilis Kerestetzis
 * @author Giorgos Kiopektzis
 * @author Fani Kontou
 * @author Giannis Sykaras
 */

public final class DbConnectHelper
{
    private static final String PERSISTENCE_UNIT_NAME = "JokerGameStatsPU";
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    //DRIVER Ο ΟΠΟΙΟΣ ΘΑ ΧΡΗΣΙΜΟΠΟΙΗΘΕΙ ΓΙΑ ΟΛΕΣ ΤΙΣ ΔΑΙΔΙΚΑΣΙΕΣ ΠΟΥ ΘΑ 
    //ΓΙΝΟΥΝ ΣΤΗ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";  
    private static final String DB_URL = "jdbc:derby://localhost:1527/jokergame;create=true";

    //ΔΙΑΠΙΣΤΕΥΤΗΡΙΑ ΒΑΣΗΣ ΔΕΔΟΜΕΝΩΝ
    private static final String USER = "joker";
    private static final String PASS = "joker";

    
    
    public DbConnectHelper() {
          
        try {
            //Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnectHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex); 
        }
    }

       
    //ΔΑΙΔΙΚΑΣΙΑ ΣΥΝΔΕΣΗΣ ΜΕ ΤΗΝ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ 
    public static void connect()
    {

            try 
            {
                //ΔΗΜΙΟΥΡΓΙΑ ΤΟΥ Entity Manager ΓΙΑ ΤΗ ΣΥΝΔΕΣΗ ΤΗΣ ΕΦΑΡΜΟΓΗΣ ΜΕ ΤΗΝ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ.
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                em = emf.createEntityManager(); 
            } 
            catch(Exception ex) 
            {
                System.out.println(ex); 
                //ΜΗΝΥΜΑ ΣΦΑΛΜΑΤΟΣ ΣΥΝΔΕΣΗΣ ΜΕ ΤΗΝ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ
                JOptionPane.showMessageDialog(null, "Αποτυχία σύνδεσης με τη Βάση Δεδομένων. Έλέγξτε αν ο Server της JavaDB είναι φορτωμένος.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }

    }

    /*Getters-Setters*/
    public static EntityManagerFactory getEmf() {
        return emf;
    }

    public static EntityManager getEm() {
        return em;
    }
    
   
    /**
     * ΣΥΝΑΡΤΗΣΗ ΕΠΙΣΤΡΟΦΗΣ TRU-FALSE ΟΤΑΝ ΚΑΝΕΙ ΚΑΘΑΡΙΣΜΟ ΟΛΩΝ ΤΩΝ ΠΙΝΑΚΩΝ ΤΗΣ ΒΔ
     * @return 
     */
    private boolean ClearAllTablesOfDB() {
        //ΜΕΤΑΒΛΗΤΗ ΤΥΠΟΥ Connection ΣΤΗΝ ΟΠΟΙΑ ΚΑΤΑΧΩΡΕΙΤΕ ΤΟ CONNECTION STRING
        Connection conn = null;
        //ΜΕΤΑΒΛΗΤΗ Statement ΣΤΗΝ ΟΠΟΙΑ ΚΑΤΑΧΩΡΕΙΤΕ ΤΟ SQL STATEMENT
        Statement stmt = null;
        //ΑΡΧΙΚΟΠΟΙΗΣΗ ΜΕΤΒΛΗΤΗΣ ΚΑΤΑΧΩΡΗΣΗΣ ΕΡΩΤΗΜΑΤΟΣ
        String sql = "";
        
        try{
           
            //ΕΚΤΕΛΕΣΗ ΕΡΩΤΗΜΑΤΩΝ
            //ΑΡΧΙΚΟΠΟΙΗΣΗ SQL STATEMENT
            stmt = conn.createStatement();

            //ΔΙΑΓΡΑΦΗ ΔΕΔΟΜΕΝΩΝ ΠΙΝΑΚΑ DRAWS
            sql = "\n DELETE FROM DRAWS \n";
            
            //ΕΚΤΕΛΕΣΗ ΤΟΥ SQL STATEMENT
            stmt.executeUpdate(sql);
            
            //ΔΙΑΓΡΑΦΗ ΔΕΔΟΜΕΝΩΝ ΠΙΝΑΚΑ PRIZECATEGORIES
            sql = "\n DELETE FROM PRIZECATEGORIES \n";
            
            //ΕΚΤΕΛΕΣΗ ΤΟΥ SQL STATEMENT
            stmt.executeUpdate(sql);
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        finally{
           //ΚΛΕΙΝΕΙ ΟΛΑ ΤΑ ΑΝΟΙΧΤΑ STATEMENT ΚΑΙ CONNECTION ΠΟΥ ΕΧΟΥΝ ΓΙΝΕΙ ΣΤΗ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ
            try{
               //ΕΛΕΓΧΟΣ ΑΝ ΤΟ STATEMENT ΔΕΝ ΕΙΝΑΙ NULL ΚΑΙ ΚΛΕΙΣΙΜΟ TOY CONNECTION
              if(stmt!=null)
                 conn.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
            try{
               //ΕΛΕΓΧΟΣ ΑΝ ΤΟ CONNECTION ΔΕΝ ΕΙΝΑΙ NULL ΚΑΙ ΚΛΕΙΣΙΜΟ ΑΥΤΟΥ
               if(conn!=null)
                  conn.close();
            }catch(SQLException ex){
                //ex.printStackTrace();
                System.out.println(ex.getMessage());
            }//ΤΕΛΟΣ finally try
        }//ΤΕΛΟΣ try
        
        
        
        //
        return true;
    }

  
    
    
    /**
     * ΔΙΑΔΙΚΑΣΙΑ ΑΡΧΙΚΟΠΟΙΗΣΗΣ ΒΑΣΗΣ ΔΕΔΟΜΕΝΩΝ (ΔΗΜΙΟΥΡΓΙΑΣ ΒΑΣΗΣ ΚΑΙ ΠΙΝΑΚΩΝ)
     * @return 
     */
    public boolean InitializeDB () {
        
        boolean err=false;
        //ΜΕΤΑΒΛΗΤΗ ΤΥΠΟΥ Connection ΣΤΗΝ ΟΠΟΙΑ ΚΑΤΑΧΩΡΕΙΤΕ ΤΟ CONNECTION STRING
        Connection conn = null;
        //ΜΕΤΑΒΛΗΤΗ Statement ΣΤΗΝ ΟΠΟΙΑ ΚΑΤΑΧΩΡΕΙΤΕ ΤΟ SQL STATEMENT
        Statement stmt = null;
        
        try{
           
           //Register JDBC driver
           //Class.forName("org.apache.derby.jdbc.ClientDriver");
            
           //ΔΗΜΙΟΥΡΓΙΑ ΣΥΝΔΕΣΗΣ ΣΤΗ  ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ ΜΕΣΩ ΤΟΥ DRIVER ΚΑΙ ΤΩΝ ΔΙΑΠΙΣΤΕΥΤΗΡΙΩΝ
           System.out.println("Πραγματοποιείται σύνδεση στη βάση δεδομένων...");
           //ΚΑΤΑΧΩΡΗΣΗ ΣΤΗ ΜΕΤΑΒΛΗΤΗ conn ΤΥΠΟΥ Connection 
           conn = DriverManager.getConnection(DB_URL, USER, PASS);
           System.out.println("Συνδέθηκε στη βάση δεδομένων...");
           
            //ΕΚΤΕΛΕΣΗ ΕΡΩΤΗΜΑΤΩΝ
            System.out.println("Δημιουργία ...");
            stmt = conn.createStatement();

            //ΔΗΜΙΟΥΡΓΙΑ ΠΙΝΑΚΑ MOVIE  (ΣΕ ΠΕΡΙΠΤΩΣΗ ΣΦΑΛΜΑΤΟΣ BIGINT ΣΤΟ ID)
            String sql ="\n CREATE TABLE Draws (\n" +
                        "  DrawID       integer NOT NULL, \n" +
                        "  WinningNo1   varchar(10) NOT NULL, \n" +
                        "  WinningNo2   varchar(10) NOT NULL, \n" +
                        "  WinningNo3   varchar(10) NOT NULL, \n" +
                        "  WinningNo4   varchar(10) NOT NULL, \n" +
                        "  WinningNo5   varchar(10) NOT NULL, \n" +
                        "  WinningBonus varchar(20) NOT NULL, \n" +
                        "  CONSTRAINT game_id \n" +
                        "  PRIMARY KEY (DrawID))"+
                        "\n ";
            
            //ΕΚΤΕΛΕΣΗ ΤΟΥ SQL STATEMENT
            stmt.executeUpdate(sql);
           
            //ΔΗΜΙΟΥΡΓΙΑ ΠΙΝΑΚΑ GENRE
            sql=    "\n CREATE TABLE Prizecategories (\n" +
                    "  PID        integer GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1), \n" +
                    "  CategoryID integer NOT NULL, \n" +
                    "  DrawID_FK  integer NOT NULL, \n" +
                    "  Divedent   float(20) NOT NULL, \n" +
                    "  Winners    integer NOT NULL, \n" +
                    "  CONSTRAINT draw_id \n" +
                    "  PRIMARY KEY (PID))"+
                    "\n";
           
            //ΕΚΤΕΛΕΣΗ ΤΟΥ SQL STATEMENT
            stmt.executeUpdate(sql);
           
            //ΕΝΗΜΕΡΩΣΗ ΤΟΥ FK
            sql="\n ALTER TABLE Prizecategories ADD CONSTRAINT FKPrizecateg29072 FOREIGN KEY (DrawID_FK) REFERENCES Draws (DrawID) \n ";
           
            //ΕΚΤΕΛΕΣΗ ΤΟΥ SQL STATEMENT
            stmt.executeUpdate(sql);
           
            err=false;
            
           
        }catch(SQLException ex){ //
            //Handle errors for JDBC
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            //ΕΛΕΓΧΟΣ ΑΝ ΤΟ ΣΦΑΛΜΑ ΠΡΟΚΛΗΘΗΚΕ ΕΠΕΙΔΗ Η ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ ΥΠΑΡΧΕΙ ΗΔΗ
            if(ex.getSQLState().toUpperCase().equals("X0Y32".toUpperCase())) {
                err=false;
                System.out.println("Η ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ ΥΠΑΡΧΕΙ ΗΔΗ");
                
                //ΜΗΝΥΜΑ ΟΤΙ Η ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ ΥΠΑΡΧΕΙ ΗΔΗ
                //JOptionPane.showMessageDialog(new JFrame(),
                //"Έγινε προσπάθεια δημιουργίας του σχήματος της βάσης δεδομένων αλλά υπάρχει ήδη.", "Δημιουργία σχήματος ΒΔ.", JOptionPane.INFORMATION_MESSAGE);
       
            }
            //ΣΦΑΛΜΑ ΑΠΟΤΥΧΙΑΣ ΣΥΝΔΕΣΗΣ
            else if (ex.getSQLState().toUpperCase().equals("08001".toUpperCase())) {
                 //ΜΗΝΥΜΑ ΣΦΑΛΜΑΤΟΣ ΣΥΝΔΕΣΗΣ ΜΕ ΤΗΝ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ
                JOptionPane.showMessageDialog(null, "Αποτυχία σύνδεσης με τη Βάση Δεδομένων. Έλέγξτε αν ο Server της JavaDB είναι φορτωμένος.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                //ΚΑΤΑΧΩΡΗΣΗ ΣΤΗ ΜΕΤΑΒΛΗΤΗ ΣΦΑΛΜΑΤΟΣ ΓΙΑ ΣΦΑΛΜΑ
                err= true;
            }
            else {
                //ΚΑΤΑΧΩΡΗΣΗ ΣΤΗ ΜΕΤΑΒΛΗΤΗ ΣΦΑΛΜΑΤΟΣ ΓΙΑ ΣΦΑΛΜΑ
                err= true;
            }
            
        }catch(Exception ex){
           //Handle errors for Class.forName
           //ex.printStackTrace();
           System.out.println(ex.getMessage());
           //ΕΠΙΣΤΡΟΦΗ ΟΤΙ ΟΛΟΚΛΗΡΩΘΗΚΕ ΧΩΡΙΣ ΣΦΑΛΜΑΤΑ Η ΔΙΑΔΙΚΑΣΙΑ
           err= true;
        }finally{
           //ΚΛΕΙΝΕΙ ΟΛΑ ΤΑ ΑΝΟΙΧΤΑ STATEMENT ΚΑΙ CONNECTION ΠΟΥ ΕΧΟΥΝ ΓΙΝΕΙ ΣΤΗ ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ
            try{
               //ΕΛΕΓΧΟΣ ΑΝ ΤΟ STATEMENT ΔΕΝ ΕΙΝΑΙ NULL ΚΑΙ ΚΛΕΙΣΙΜΟ TOY CONNECTION
              if(stmt!=null)
                 conn.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
            try{
               //ΕΛΕΓΧΟΣ ΑΝ ΤΟ CONNECTION ΔΕΝ ΕΙΝΑΙ NULL ΚΑΙ ΚΛΕΙΣΙΜΟ ΑΥΤΟΥ
               if(conn!=null)
                  conn.close();
            }catch(SQLException ex){
                //ex.printStackTrace();
                System.out.println(ex.getMessage());
            }//ΤΕΛΟΣ finally try
        }//ΤΕΛΟΣ try
        
        if (!err) {
            System.out.println("ΟΛΟΚΛΗΡΩΘΗΚΕ Η ΔΗΜΙΟΥΡΓΙΑ ΤΟΥ ΣΧΗΜΑΤΟΣ ΤΗΣ ΒΑΣΗΣ ΔΕΔΟΜΕΝΩΝ!");
        }else {
            System.out.println("Η ΔΗΜΙΟΥΡΓΙΑ ΤΟΥ ΣΧΗΜΑΤΟΣ ΤΗΣ ΒΑΣΗΣ ΔΕΔΟΜΕΝΩΝ ΑΠΕΤΥΧΕ!");
        }

        //ΕΠΙΣΤΡΟΦΗ ΟΤΙ ΟΛΟΚΛΗΡΩΘΗΚΕ ΧΩΡΙΣ ΣΦΑΛΜΑΤΑ Η ΔΙΑΔΙΚΑΣΙΑ
        return err;
    }
    
}
