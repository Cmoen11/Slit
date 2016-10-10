package auth;

import course.CourseInfo;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface LoginAuthRemote {
    boolean authAdminAccount(String username, String password);

    HashMap<String, String> getUserData();

    void test();

    UserDetails authUser(String username, String password, int coruseID);

    ArrayList<CourseInfo> getCourses();

}
