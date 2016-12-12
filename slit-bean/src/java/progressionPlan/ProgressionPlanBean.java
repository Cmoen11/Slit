/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progressionPlan;

import auth.UserDetails;
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
    public void addProgressionEntry(ProgressionEntry entry) {
        em.persist(transferObjectToEntityObject(entry));
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
    public ArrayList<ProgressionEntry> getAllProgressionEntriesByUser(UserDetails user) {          
        ArrayList<ProgressionEntry> output = new ArrayList<>();
        
        for (Progressionentry entry : em.find(Progressionplan.class, user.getId()).getProgressionentryCollection()) {
            output.add(entityObjectToTransferObject(entry));
        }
        
        return output; 
    }

    private Progressionentry transferObjectToEntityObject(ProgressionEntry entry) {
        Progressionentry output = new Progressionentry();
        output.setCompletionDate(entry.getCompletionDate());
        output.setId(entry.getProgressionEntryID());
        output.setModuleID(em.find(Module.class, entry.getModuleID()));
        output.setPlanID(em.find(Progressionplan.class, entry.getPlanID()));
        
        return output; 
    }
    
    private ProgressionEntry entityObjectToTransferObject (Progressionentry entry) {
        ProgressionEntry output = new ProgressionEntry(); 
        output.setModuleID(entry.getModuleID().getModuleID());
        output.setPlanID(entry.getPlanID().getPlanID());
        output.setProgressionEntryID(entry.getId());
        output.setCompletionDate(entry.getCompletionDate());
        
        return output;
    }
    
}