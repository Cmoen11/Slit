package auth;

import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface LoginAuthRemote {

    boolean authUserName(String username);

    boolean authAccount(String username, String password);

    String Md5_String(String toMD5);

    boolean loginAuth2();

    boolean authAccount_test();

    void test123123();
    
}
