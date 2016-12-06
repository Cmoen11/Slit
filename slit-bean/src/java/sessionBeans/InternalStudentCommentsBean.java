
package sessionBeans;

import database.Interalstudentcomments;
import database.InteralstudentcommentsPK;
import database.Users;
import java.util.ArrayList;
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
        
    }

    @Override
    public ArrayList<InternalStudentComments> getAllComments(int courseID, int userID) {
        return null;
    }
    
    /**
     * create an object from enitity class, to transfer to client.
     * @param obj entity object.
     * @return 
     */
    private InternalStudentComments entityObjectToTransfer(Interalstudentcomments obj) {
        InternalStudentComments output = new InternalStudentComments(
                obj.getCreationDate(), obj.getTeacherID().getUserID(), 
                obj.getInteralstudentcommentsPK().getStudentID(),
                obj.getInteralstudentcommentsPK().getCourseID(),
                obj.getComment()
        );
        return output;
    }
    
    private Interalstudentcomments transferObjectToEntityObject(InternalStudentComments obj) {
        Interalstudentcomments newEntry = new Interalstudentcomments();
        newEntry.setComment(obj.getComment());
        newEntry.setCreationDate(obj.getCreationDate());
        newEntry.setTeacherID(em.find(Users.class, obj.getTeacherID()));

        return null;
        
    }
    
    
    
}
