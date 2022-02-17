package accessories;


import java.sql.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**

 */ 

public final class DbConnect
{
    private static final String PERSISTENCE_UNIT_NAME = "jgsPU";
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    //Driver ο οποίος θα χρησιμοποιηθεί για όλες τις διαδικασίες που θα
    //γίνουν στην ΒΔ
    private static final String DB_URL = "jdbc:derby://localhost:1527/jgs;create=true";

    //Διαπιστευτήρια ΒΔ
    private static final String USER = "plh24";
    private static final String PASS = "plh24";


    //Σύνδεση με την ΒΔ
    public static void connect()
    {
            try 
            {
                //Δημιουργούμε το EntityManager για τη σύνδεση της εφαρμογής με την ΒΔ.
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                em = emf.createEntityManager(); 
            } 
            catch(Exception ex) 
            {
                System.out.println(ex); 
                // Ενημερωτικό μήνυμα σε περίπτωση που δεν έχει δημιουργηθεί η ΒΔ
                JOptionPane.showMessageDialog(null, "Database JGS not found!\nOnce the netBeans Database Server (Java DB) is started, it will attempt to automatically create it.", "Information", JOptionPane.NO_OPTION);
                JOptionPane.showMessageDialog(null, "Attention, after the end of the process, the application is required to restart manually!", "Warning", JOptionPane.WARNING_MESSAGE);
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
     * Διαδικασία αρχικοποίησης της ΒΔ(Δημιουργία πινάκων)
     * @return 
     */
    public static void InitializeDB() {
        
        boolean err=false;
        //Μεταβλητή τύπου Connection στην οποία καταχωρούμε το Connection String
        Connection conn = null;
        //Μεταβλητή τύπου Statement στην οποία καταχωρούμε το sql statement
        Statement stmt = null;
        
        try{

           //Δημιουργία σύνδεσης στη ΒΔ μέσω του driver και των διαπιστευτηρίων
           System.out.println("Creating the Database Connection...");
           
           //Καταχώρηση στη μεταβλητή conn
           conn = DriverManager.getConnection(DB_URL, USER, PASS);
           System.out.println("User Login " + USER + "...");
           
            //Εκτελούμε τα Database Queries
            System.out.println("Creating tables...");
            stmt = conn.createStatement();

            // Δημιουργία του πίνακα GAMES
            String sql1 = "\n CREATE TABLE GAMES(\n" +
                          " GAMEID INTEGER PRIMARY KEY,\n" +
                          " GAMENAME VARCHAR(3))\n";
            stmt.executeUpdate(sql1);

            // Δημιουργία του πίνακα DRAWS
            String sql2="\n CREATE TABLE DRAWS(\n" +
                        " DRAWID INTEGER PRIMARY KEY,\n" +
                        " GAMEID INTEGER\n" +
                            " CONSTRAINT GAMEID\n" +
                            " REFERENCES GAMES,\n" +
                        " DRAWTIME INTEGER,\n" +
                        " STATUS VARCHAR(20),\n" +
                        " DRAWBREAK VARCHAR(20),\n" +
                        " VISUALDRAW INTEGER,\n" +
                        " PRICEPOINTS VARCHAR(30),\n" +
                        " WINNINGLIST VARCHAR(30),\n" +
                        " WINNINGBONUS VARCHAR(20),\n" +
                        " COLUMNS INTEGER,\n" +
                        " WAGERS INTEGER,\n" +
                        " ADDON VARCHAR(20))\n ";
            stmt.executeUpdate(sql2);
           
            // Δημιουργία του πίνακα PRIZECATEGORIES
            String sql3="\n CREATE TABLE PRIZECATEGORIES(\n" +
                        " PID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),\n" +
                        " CATEGORYID INTEGER,\n" +
                        " DRAWID INTEGER\n" +
                            " CONSTRAINT DRAWID\n" +
                            " REFERENCES DRAWS,\n" +
                        " DIVIDENT FLOAT,\n" +
                        " WINNERS INTEGER,\n" +
                        " DISTRIBUT FLOAT,\n" +
                        " JACKPOT FLOAT,\n" +
                        " FIXED FLOAT,\n" +
                        " CATEGORYTYPE INTEGER,\n" +
                        " GAMETYPE VARCHAR(30))\n ";
            stmt.executeUpdate(sql3);
              
      err=false;
            
           
        }catch(SQLException ex){
            //Ελέγχουμε αν το σφάλμα προκλήθηκε επειδή η ΒΔ υπάρχει ήδη
            if(ex.getSQLState().toUpperCase().equals("X0Y32".toUpperCase())) {
                err=false;
                System.out.println("THE DATABASE EXISTS");               
            }
            //Σφάλμα αποτυχίας της σύνδεσης
            else if (ex.getSQLState().toUpperCase().equals("08001".toUpperCase())) {
                 //Μήνυμα σφάλματος της σύνδεσης με την ΒΔ
                JOptionPane.showMessageDialog(null, "Failed to connect to Database.\nCheck whether NetBeans Database Server (Java DB) is started.", "Error", JOptionPane.ERROR_MESSAGE);
                //Καταχωρούμε στη μεταβλητή το σφάλμα
                err= true;
            }
            else {
                //Καταχωρούμε στη μεταβλητή το σφάλμα
                err= true;
            }
            
        }catch(Exception ex){           
           System.out.println(ex.getMessage());
           //Επιστροφή ότι η διαδικασία ολοκληρώθηκε χωρίς σφάλματα
           err= true;
        }finally{
           //Κλείνουμε όλα τα ανοιχτά statements και connections που έχουν δημιουργηθεί στη ΒΔ
            try{
               //Ελέγχουμε αν το statement δεν είναι NULL και κλείνουμε το connection
              if(stmt!=null)
                 conn.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
            try{
               //Ελέγχουμε αν το connection δεν είναι NULL και το κλείνουμε
               if(conn!=null)
                  conn.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }//Τέλος του finally try
        }//Τέλος του try
        
        if (!err) {
            System.out.println("THE PROCESS COMPLETED SUCCESSFULLY!");
        }else {
            System.out.println("THE PROCESS FAILED!");
        }

        //Επίστρέφουμε ότι η διαδικασία ολοκληρώθηκε χωρίς σφάλματα
        //return err;
    }
    
}
