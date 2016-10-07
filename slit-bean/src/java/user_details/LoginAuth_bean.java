package user_details;

<<<<<<< HEAD
import user_details.Users;
=======
import database.Users;
>>>>>>> master
import auth.LoginAuthRemote;
import java.util.HashMap;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
/**
 *
 * @author Christian
 */
@Stateful
public class LoginAuth_bean implements LoginAuthRemote {
    @PersistenceContext EntityManager em;
    private HashMap<String, String> userData = new HashMap<>();
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
            if (user.get(0).getPassword().equals(password)) {
                userData.put("isLoggedIn", "True");
                userData.put("username", user.get(0).getUsername());
                return true;
            }
                
        }catch(Exception e) {  
        }
        return false;
                
    }

    @Override
    public void CreateDummyUsers() {
        Users user = new Users("user1", "test", "test@slit.no");
        em.persist(user);
    }
    
    
    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public HashMap<String, String> getUserData() {
        return userData;
    }

    @Override
    public void test() {
    }

    


    
    
    
    
    
    
}
