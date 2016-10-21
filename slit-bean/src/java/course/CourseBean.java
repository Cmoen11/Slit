
package course;

import auth.UserDetails;
import database.CourseMembers;
import database.CourseMembersPK;
import database.Courses;
import database.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

/**
 *
 * @author Christian
 */
@Stateless
public class CourseBean implements CourseBeanRemote {
    @PersistenceContext()
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
                        + "SELECT u FROM Users u, CourseMembers cm WHERE cm.courseMembersPK.courseID = :courseID AND cm.courseMembersPK.userID = u.userID AND cm.isTeacher = 0")
                        .setParameter("courseID", courseID)
                        .getResultList();
        
        List<Users> teachers;
        teachers = em.createQuery(""
                        + "SELECT u FROM Users u, CourseMembers cm WHERE cm.courseMembersPK.courseID = :courseID AND cm.courseMembersPK.userID = u.userID AND cm.isTeacher = 1")
                        .setParameter("courseID", courseID)
                        .getResultList();
        
        HashSet<UserDetails> output = new HashSet<>();
        for (Users obj : students) output.add(new UserDetails(
                obj.getUserID(), obj.getUsername(), obj.getEmail(),
                courseID, 0));
        for (Users obj : teachers) output.add(new UserDetails(
                obj.getUserID(), obj.getUsername(), obj.getEmail(),
                courseID, 1));
        
        ArrayList<UserDetails> userDetails = new ArrayList<>();
        for (UserDetails obj : output)
            userDetails.add(obj);
        
        return userDetails;
    }

    @Override
    public void addMemberToCourse(int userID, int courseID, int teacher) {
            CourseMembers newRecord = new CourseMembers();
            newRecord.setCourseMembersPK(new CourseMembersPK(courseID, userID));
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
        temp1 = em.createQuery("SELECT u FROM Users u").getResultList();
        for (Iterator<Users> it = temp1.iterator(); it.hasNext();) {
            Collection<CourseMembers> courses = it.next().getCourseMembersCollection(); 
            if (courses != null && !courses.isEmpty())
                for (CourseMembers course : courses) {
                    if (course.getCourseMembersPK().getCourseID() == courseID){
                        it.remove();
                        break;
                    }
                }
            
        }
        
        
        ArrayList<UserDetails> output = new ArrayList<>();
        
        for (Users user : temp1) {
            output.add(new UserDetails(
                    user.getUserID(),
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
    
    /**
     * Fjern et medlem av et kurs.
     * @param userID
     * @param courseID 
     */
    @Override
    public void removeUserFromCourse(int userID, int courseID) {
        CourseMembers cm = em.find(CourseMembers.class, new CourseMembersPK(courseID, userID));
        em.remove(cm);
        em.flush();
    }
    
    /**
     * Endre på om brukeren skal være en student eller foreleser
     * @param userID
     * @param courseID 
     */
    @Override
    public void switchUserStudentTeacher(int userID, int courseID) {
        CourseMembers cm = em.find(CourseMembers.class, new CourseMembersPK(courseID, userID));
        if (cm.getIsTeacher() == 1) cm.setIsTeacher(0);
        else                        cm.setIsTeacher(1);
        
        System.out.println(cm.getIsTeacher());
        em.merge(cm);
        em.flush();
    }

    /**
     * Opprett et nytt kurs fra et CourseInfo objekt
     * @param newCourse
     */
    @Override
    public void createCourse(CourseInfo newCourse) {
        Courses course = new Courses();
        course.setCourseCode(newCourse.getCourseCode());
        course.setCourseEndDate(newCourse.getEndDate());
        course.setCourseStartDate(newCourse.getStartDate());
        course.setCourseName(course.getCourseName());
        
        em.persist(course);
    }
    
    
    
}
