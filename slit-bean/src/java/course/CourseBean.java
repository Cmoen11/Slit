
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
    public ArrayList<UserDetails> getCourseMembers(int courseID) {
        List<Users> students;
        students = em.createQuery(""
                        + "SELECT u FROM Users u, CourseMembers cm WHERE cm.courseMembersPK.courseID = :courseID AND cm.courseMembersPK.userID = u.id AND cm.isTeacher = 0")
                        .setParameter("courseID", courseID)
                        .getResultList();
        
        List<Users> teachers;
        teachers = em.createQuery(""
                        + "SELECT u FROM Users u, CourseMembers cm WHERE cm.courseMembersPK.courseID = :courseID AND cm.courseMembersPK.userID = u.id AND cm.isTeacher = 1")
                        .setParameter("courseID", courseID)
                        .getResultList();
        
        ArrayList<UserDetails> output = new ArrayList<>();
        for (Users obj : students) output.add(new UserDetails(
                obj.getId(), obj.getUsername(), obj.getEmail(),
                courseID, 0));
        for (Users obj : teachers) output.add(new UserDetails(
                obj.getId(), obj.getUsername(), obj.getEmail(),
                courseID, 1));
        
        return output;
    }

    @Override
    public void addMemberToCourse(int userID, int courseID, int teacher) {
            CourseMembers newRecord = new CourseMembers();
            newRecord.setCourseMembersPK(new CourseMembersPK(userID, courseID));
            newRecord.setIsTeacher(teacher);
            em.persist(newRecord);
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
                + "AND cm.courseMembersPK.courseID  != :courseID")
                .setParameter("courseID", courseID).getResultList();
        List<Users> temp2;
        temp2 = em.createQuery(""
                + "SELECT u FROM Users u WHERE u.courseMembersCollection IS EMPTY"  
        ).getResultList();
    
        temp1.removeAll(temp2);
        temp1.addAll(temp2);
        
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
    
    /**
     * Hent CourseInfo objektet med informasjon fra clienten.
     * @param newInfo 
     */
    @Override
    public void editCourse(CourseInfo newInfo) {
        Courses update = new Courses();
        update.setCourseCode(newInfo.getCourseCode());
        update.setCourseID(newInfo.getCourseID());
        update.setCourseName(newInfo.getCourseName());
        update.setCourseStartDate(newInfo.getStartDate());
        update.setCourseEndDate(newInfo.getEndDate());
        
        em.merge(update);
    }
    
    
}
