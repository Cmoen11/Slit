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
    // find, 
    
    
    protected static final String url = "jdbc:mysql://localhost:3306/Peoples?autoReconnect=true&useSSL=false",
            dbName = "slit",
            driver = "com.mysql.jdbc.Driver",
            userName = "root",
            password = "root";

    @Override
    public boolean authUserName(String username) {
        Users user = em.find(Users.class, 1);
        
        return true;
    }

    @Override
    public boolean authAccount(String username, String password) {
        System.out.println("yoo");
        
        List<Users> user = em.createQuery(""
                + "SELECT u FROM Users u WHERE u.username = :username")
                .setParameter("username", username)
                .getResultList();
        try{
            
            System.out.println(user.get(0).getPassword());
            if (user.get(0).getPassword().equals(password))
                return true;
        }catch(Exception e) {
            System.out.println(e);    
        }
        return false;
                
    }

    @Override
    public String Md5_String(String toMD5) {
        return "Hey";
    }

    @Override
    public boolean loginAuth2() {
        Users users = new Users();
        
        return false;
    }

    @Override
    public boolean authAccount_test() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void test123123() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
    
    
    
}
