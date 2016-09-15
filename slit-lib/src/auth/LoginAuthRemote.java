package auth;

import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface LoginAuthRemote {
    boolean authAccount(String username, String password);
}
