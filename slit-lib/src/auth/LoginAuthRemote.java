package auth;

import java.util.HashMap;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface LoginAuthRemote {
    boolean authAccount(String username, String password);

    HashMap<String, String> getUserData();

    void test();

}
