
package sessionBeans;

import database.Courses;
import database.Helpreply;
import database.Helprequest;
import database.Users;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
    
    @Override
    public void assignHelpRequest(HelpRequestDetails helprequest, int userID) {
        //Helpreply reply = new Helpreply(Integer.MAX_VALUE, "", "", new Date());
        Helpreply reply = new Helpreply();
        reply.setRequestID(em.find(Helprequest.class, helprequest.getRequestID()));
        reply.setUserID(em.find(Users.class, userID));
        em.persist(reply);
        
        Helprequest request = em.find(Helprequest.class, helprequest.getRequestID());
        request.setStatus(1);
        em.merge(request);    
    }
    
    /**
     * 
     * @param userID
     * @param courseID
     * @return 
     */
    @Override
    public List<HelpRequestDetails> getAssignedHelpRequests(int userID, int courseID){
        
        // get all helprequests
        List<Helprequest> request = em.createNamedQuery("Helprequest.findAll").getResultList();
        
        // filter the request
        for (Iterator<Helprequest> it = request.iterator(); it.hasNext();) {
            Helprequest temp = it.next();
            
            // it is an assigned 'pending' request, and the request is in the
            // same course.
            if (temp.getStatus() == 1 && temp.getCourseID().getCourseID() == courseID) {
                Helpreply obj = temp.getHelpreplyCollection().iterator().next();
                
                // if the helprequest is not assigned to our user.
                if (obj.getUserID().getUserID() != userID) {
                    it.remove();    // remove it.
                }
            } else {
                it.remove();
            }
        }
        
        System.out.println(request.size());
        
        List<HelpRequestDetails> output = new ArrayList<>();
        request.stream().forEach((record) -> {
            HelpRequestDetails obj = new HelpRequestDetails();
            
            obj.setUserID(record.getUserID().getUserID());
            obj.setTitle(record.getTitle());
            obj.setStatus(record.getStatus());
            obj.setRequestID(record.getRequestID());
            obj.setCreationDate(record.getCreationDate());
            obj.setContent(record.getContent());
            obj.setCourseID(record.getCourseID().getCourseID());
            
            output.add(obj);
        } );
        
        return output;
        
    }
    
    @Override
    public void unassignHelpRequest(HelpRequestDetails helprequest) {
        Helprequest hr = em.find(Helprequest.class, helprequest.getRequestID());
        hr.setStatus(0);
        em.merge(hr);
        Helpreply helpreply;
        helpreply = (Helpreply) em.createNamedQuery("Helpreply.findByRequestID")
                .setParameter("requestID", em.find(Helprequest.class, 
                        helprequest.getRequestID())).getSingleResult();
        
        em.remove(helpreply);
        
        em.remove(hr.getHelpreplyCollection().iterator().next());
    }
}
