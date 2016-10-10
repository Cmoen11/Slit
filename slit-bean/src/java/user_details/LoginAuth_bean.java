package user_details;

import course.CourseInfo;
import database.Users;
import auth.LoginAuthRemote;
import auth.UserDetails;
import database.CourseMembers;
import database.Courses;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author Christian
 */
@Stateful
public class LoginAuth_bean implements LoginAuthRemote {

    @PersistenceContext
    EntityManager em;
    private HashMap<String, String> userData = new HashMap<>();

    /**
     * Check if username & password match.
     *
     * @param username
     * @param password
     * @return true if it match, false if it do not match.
     */
    @Override
    public boolean authAdminAccount(String username, String password) {
        // Query the server
        List<Users> user = em.createQuery(""
                + "SELECT u FROM Users u WHERE u.username = :username")
                .setParameter("username", username)
                .getResultList();
        try {
            // check if the password match with the password given.
            if (user.get(0).getPassword().equals(password) && user.get(0).getAdmin() == 1) {
                return true;
            }

        } catch (Exception e) {
        }
        return false;

    }

    /**
     * Sjekker om bruker er autorisert til å logge inn på valgt kurs.
     *
     * @param username
     * @param password
     * @param coruseID
     * @return UserDetails, returnerer null om brukeren ble netket tilgang.
     */
    @Override
    public UserDetails authUser(String username, String password, int courseID) {
        // Check if there is a user with the username & password that are also
        // part of the course that the user want to enter.
        List<Users> user;
        user = em.createQuery(""
                + "SELECT u FROM Users u WHERE u.username = :username")
                .setParameter("username", username)
                .getResultList();

        if (!user.isEmpty()) {  // Om vi retunerte et resultat, hent første objektet og retuner det
            Users userobj = user.get(0);
            if (userobj.getPassword().equals(password)) {
                List<CourseMembers> courseMembers;
                courseMembers = em.createQuery(""
                        + "SELECT cm FROM CourseMembers cm WHERE cm.courseMembersPK.courseID = :courseID AND cm.courseMembersPK.userID = :userID")
                        .setParameter("courseID", courseID)
                        .setParameter("userID", user.get(0).getId())
                        .getResultList();
                if (!courseMembers.isEmpty())
                    return new UserDetails(userobj.getId(), userobj.getUsername(), userobj.getEmail(), courseID, courseMembers.get(0).getIsTeacher());
            }
        }
        return null;
    }
    
    /**
     * 
     * @return Alle kursene 
     */
    @Override
    public ArrayList<CourseInfo> getCourses() {
        List<Courses> courses = null;
        ArrayList<CourseInfo> output = new ArrayList<>();
        TypedQuery<Courses> temp1 = em.createNamedQuery("Courses.findAll", Courses.class);
        courses = temp1.getResultList();
        
        for (Courses course : courses) {
            output.add(new CourseInfo(course.getCourseID(), course.getCourseStartDate(), course.getCourseEndDate(), course.getCourseName(), course.getCourseCode()));
        }
        
        return output;
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
