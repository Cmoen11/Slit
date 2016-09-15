package auth;

import static auth.Database.url;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
/**
 *
 * @author Christian
 */
@Singleton
public class LoginAuth implements LoginAuthRemote {
    @PersistenceContext EntityManager em;
    
    /**
     * Check if username & password match.
     * @param username
     * @param password
     * @return true if it match, false if it do not match.
     */
    @Override
    public boolean authAccount(String username, String password) {
        System.out.println("yoo");
        
        // Query the server
        List<Users> user = em.createQuery(""
                + "SELECT u FROM Users u WHERE u.username = :username")
                .setParameter("username", username)
                .getResultList();
        try{
            // check if the password match with the password given.
            if (user.get(0).getPassword().equals(password))
                return true;
        }catch(Exception e) {
            System.out.println(e);    
        }
        return false;
                
    }


    public void persist(Object object) {
        em.persist(object);
    }
    
    
    
    
}
