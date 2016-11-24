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
    UserDetails getUserData();
    void test();
    UserDetails authUser(String username, String password);
    ArrayList<CourseInfo> getCourses();
    void editUser(UserDetails obj, String password);
    boolean isAdmin(int userID);
}
