package jokergamestats.exceptions;

/**
 * @author Vasilis Kerestetzis
 * @author Giorgos Kiopektzis
 * @author Fani Kontou
 * @author Giannis Sykaras
 */

public class DbFailureException extends Exception{
    public DbFailureException(String message) {
          super(message);
    }
}
