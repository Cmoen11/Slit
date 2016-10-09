
package course;

import auth.CourseInfo;
import database.Courses;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Christian
 */
@Stateless
public class CourseBean implements CourseBeanRemote {
    EntityManager em;
    
    @Override
    public ArrayList<CourseInfo> getCourses() {
        List<Courses> courses = null;
        ArrayList<CourseInfo> output = new ArrayList<>();
        TypedQuery<Courses> temp1 = em.createNamedQuery("Courses.findAll", Courses.class);
        courses = temp1.getResultList();
        
        for (Courses course : courses) {
            output.add(new CourseInfo(course.getCourseID(), course.getCourseStartDate(), course.getCourseEndDate(), course.getCourseName()));
        }
        
        return output;
    }
    // to do
    @Override
    public ArrayList<String> getCourseMembers(int courseID) {
        em.find(Courses.class, courseID);
        return null;
    }

}
