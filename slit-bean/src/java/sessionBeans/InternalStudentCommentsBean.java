
package sessionBeans;

import database.Courses;
import database.Interalstudentcomments;
import database.Users;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import transferClasses.InternalStudentComments;

/**
 *
 * @author Christian
 */
@Stateless
public class InternalStudentCommentsBean implements InternalStudentCommentsBeanRemote {
    @PersistenceContext()
    EntityManager em;
    
    
    @Override
    public void addComment(InternalStudentComments obj) {
        Interalstudentcomments newEntry = transferObjectToEntityObject(obj);
        em.persist(newEntry);
    }
    
    /**
     * Retrive all comments for a specific user and course. 
     * @param courseID
     * @param userID
     * @return an ArrayList of all the comments.
     */
    @Override
    public ArrayList<InternalStudentComments> getAllComments(int courseID, int userID) {
        ArrayList<InternalStudentComments> output = new ArrayList<>();
        
        // retrive all the comments requested
        List<Interalstudentcomments> entityObjects = 
                em.createNamedQuery("Interalstudentcomments.findByUserIDandCourseID")
                        .setParameter("studentID", em.find(Users.class, userID))
                        .setParameter("courseID", em.find(Courses.class, courseID))
                    .getResultList();
        
        // add it to our output for sending data back to client.
        entityObjects.stream().forEach((x) -> { 
            output.add(entityObjectToTransfer(x));
        });
        
        
        return output;
    }
    
    /**
     * create an object from enitity class, to transfer to client.
     * @param obj entity object.
     * @return transfer object type InternalStudentComments
     */
    private InternalStudentComments entityObjectToTransfer(Interalstudentcomments obj) {
        InternalStudentComments output = new InternalStudentComments(
                obj.getCreationDate(), obj.getTeacherID().getUserID(), 
                obj.getStudentID().getUserID(), obj.getCourseID().getCourseID(),
                obj.getComment()
        );
        return output;
    }
    
    /**
     * create an entity object from an transfer object.
     * @param obj transfer object
     * @return entity object type Interalstudentcomments
     */
    private Interalstudentcomments transferObjectToEntityObject(InternalStudentComments obj) {
        Interalstudentcomments newEntry = new Interalstudentcomments();
        newEntry.setComment(obj.getComment());
        newEntry.setCreationDate(obj.getCreationDate());
        newEntry.setTeacherID(em.find(Users.class, obj.getTeacherID()));
        newEntry.setStudentID(em.find(Users.class, obj.getStudentID()));
        newEntry.setCourseID(em.find(Courses.class, obj.getCourseID()));
        
        return newEntry;
        
    }
    
    
    
}
