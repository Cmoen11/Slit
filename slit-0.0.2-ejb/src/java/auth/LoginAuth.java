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

/**
 *
 * @author Christian
 */
@Singleton
public class LoginAuth implements LoginAuthRemote {
    @PersistenceContext EntityManager em;
    // find, 
    
    
    protected static final String url = "jdbc:mysql://localhost:3306/",
            dbName = "slit",
            driver = "com.mysql.jdbc.Driver",
            userName = "root",
            password = "root";

    @Override
    public boolean authUserName(String username) {
        Users user = em.find(Users.class, 1);
        
//em.createNamedQuery("")
        return true;
    }

    @Override
    public boolean authAccount(String username, String password) {
        
        try {
            Connection conn = DriverManager.getConnection(
                    this.url + this.dbName, this.userName, this.password);
            
            String query = "SELECT password FROM users WHERE username = username";

            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, username);

            ResultSet results = pstmt.executeQuery();

            if (results.isBeforeFirst()) {
                results.next();
                //System.out.println(results.getString("password"));
                if(results.getString("password").equals(password)) {
                    
                    return true;
                }
            }else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginAuth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public String Md5_String(String toMD5) {
        return null;
    }

    @Override
    public boolean loginAuth2() {
        Users users = new Users();
        
        return false;
    }
    
    
    
    
}
