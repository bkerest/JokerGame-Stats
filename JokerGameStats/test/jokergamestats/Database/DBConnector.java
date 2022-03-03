package jokergamestats.Database;

import java.sql.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

public final class DBConnector
{
    private static final String PERSISTENCE_UNIT_NAME = "JokerGameStatsPU";
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    //Driver ο οποίος θα χρησιμοποιηθεί για όλες τις διαδικασίες που θα
    //γίνουν στην ΒΔ
    private static final String DB_URL = "jdbc:derby://localhost:1527/JokerGameStats;create=true";

    //Διαπιστευτήρια ΒΔ
    private static final String USER = "eapuser";
    private static final String PASS = "eapuser";
    
}

