/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jokergamestats;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author vker
 */
public class JokerGameStats {
    static EntityManagerFactory emf;
    static EntityManager em;
    private static void createEMFandEM() {
        emf = Persistence.createEntityManagerFactory(JokerGameStatsPU);
        em = emf.createEntityManager();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Test Message 1");
    }
    
}
