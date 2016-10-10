
package course;

import auth.UserDetails;
import database.CourseMembers;
import database.CourseMembersPK;
import database.Courses;
import database.Users;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Christian
 */
@Stateless
public class CourseBean implements CourseBeanRemote {
    @PersistenceContext
    EntityManager em;
    
    /**
     * Hent ut alle kursene
     * @return Liste med alle kursene.
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
    
    /**
     * Hent alle medlemmene i valgt kurs.
     * @param courseID
     * @return Liste med alle brukernavnene i valgt kurs.
     */
    @Override
    public ArrayList<String> getCourseMembers(int courseID) {
        List<Users> temp1;
        temp1 = em.createQuery(""
                        + "SELECT u FROM Users u, CourseMembers cm WHERE cm.courseMembersPK.courseID = :courseID AND cm.courseMembersPK.userID = u.id")
                        .setParameter("courseID", courseID)
                        .getResultList();
        
        System.out.println(temp1.size());
        ArrayList<String> members = new ArrayList<>();
        for (Users obj : temp1)
            members.add(obj.getUsername());
        
        if (!members.isEmpty())    
            return members;
        return null;
    }

    @Override
    public void addMemberToCourse(int userID, int courseID, int teacher) {
        CourseMembers obj = em.find(CourseMembers.class, new CourseMembersPK(userID, courseID));
        if (obj != null){
            CourseMembers newRecord = new CourseMembers();
            newRecord.setCourseMembersPK(new CourseMembersPK(userID, courseID));
            newRecord.setIsTeacher(teacher);
            em.persist(newRecord);
        }
    }
    /**
     * Henter ut alle brukerene som ikke er medlem av kurset. 
     * @param courseID  KursID på det kurset som skal bli filtrert ut
     * @return ArrayList av brukerene.
     */
    @Override
    public ArrayList<UserDetails> getAllUsersNotInCourse(int courseID) {
        List<Users> temp1;
        temp1 = em.createQuery("SELECT u FROM Users u, CourseMembers cm "
                + "WHERE u.id = cm.courseMembersPK.userID "
                + "AND cm.courseMembersPK.courseID != :courseID")
                .setParameter("courseID", courseID).getResultList();
        ArrayList<UserDetails> output = new ArrayList<>();
        
        for (Users user : temp1) {
            output.add(new UserDetails(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    -1,
                    0
            ));
        }
        return output;
    }
    
    
}
