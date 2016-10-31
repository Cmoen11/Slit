
package sessionBeans;

import database.Courses;
import database.Helprequest;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import transferClasses.HelpRequestDetails;

/**
 *
 * @author Christian
 */
@Stateless
public class HelpRequestBean implements HelpRequestBeanRemote {
    @PersistenceContext()
    EntityManager em;
    
    @Override
    public List<HelpRequestDetails> getAllUnassignedHelpRequests(int courseID) {
        Query query = em.createNamedQuery(
                "Helprequest.findByCourseIDAndStatus=0", Helprequest.class);
        
        query.setParameter("courseID", em.find(Courses.class, courseID));
        List<Helprequest> requests = query.getResultList();
        
        List<HelpRequestDetails> output = new ArrayList<>();
        requests.stream().forEach((record) -> {
            HelpRequestDetails obj = new HelpRequestDetails();
            
            obj.setUserID(record.getUserID().getUserID());
            obj.setTitle(record.getTitle());
            obj.setStatus(record.getStatus());
            obj.setRequestID(record.getRequestID());
            obj.setCreationDate(record.getCreationDate());
            obj.setContent(record.getContent());
            obj.setCourseID(record.getCourseID().getCourseID());
            
            output.add(obj);
        });
        
        return output;
    }
    
    
    
    
}
