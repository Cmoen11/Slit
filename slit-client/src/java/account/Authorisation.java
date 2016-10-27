package account;




import auth.UserDetails;

/**
 * This class will hold the account data.
 * @author Christian
 */
public class Authorisation {
    private static UserDetails userData;

    public static UserDetails getUserData() {
        return userData;
    }

    public static void setUserData(UserDetails userData) {
        Authorisation.userData = userData;
    }
    
    
    
    
}
