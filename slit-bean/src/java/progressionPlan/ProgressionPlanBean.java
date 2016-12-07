/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progressionPlan;

import database.*;
import java.util.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author martinvenaas
 */
@Stateless
public class ProgressionPlanBean implements ProgressionPlanBeanRemote {
    @PersistenceContext 
    EntityManager em;
    
    @Override
    public void addProgressionEntry(Date completionDate, int module, int planID) {
        
        Progressionentry entry = new Progressionentry();
        
        entry.setCompletionDate(completionDate);
        entry.setModuleID(em.find(Module.class, module));
        entry.setPlanID(em.find(Progressionplan.class, planID));
        
        em.persist(entry);
    }

    @Override
    public void addProgressionPlan(int userID, int courseID) {
       
        Progressionplan plan = new Progressionplan();
        
        plan.setUserID(em.find(Users.class, userID));
        plan.setCourseID(em.find(Courses.class, courseID));
        
        em.persist(plan);
    }
    
    //Compare today and progressionplan date return differene in int days
    @Override
    public long compareDates(int planID, int moduleID) {
        
        Progressionentry planDate = em.find(Progressionentry.class, planID);       
        
        Date today = new Date();
        
        long daysBetween = ChronoUnit.DAYS.between(planDate.getCompletionDate().toInstant(), today.toInstant());

        return daysBetween;
        
    }

    @Override
    public List<Progressionentry> getAllProgressionEntriesForUser(int userID) {
        List<Progressionentry> returnList = new ArrayList<Progressionentry>();
        
        Query query = em.createNamedQuery("progressionEntries.findByUser", Progressionentry.class);
        
        query.setParameter("userID", userID);
        
        List<Progressionentry> resultList = query.getResultList();
        
        for (Progressionentry entry : resultList) {
            returnList.add(null);
        };
    }
    
    

    
}