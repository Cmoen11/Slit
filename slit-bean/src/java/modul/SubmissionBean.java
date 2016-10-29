/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import database.Modulesubmission;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Christian
 */
@Stateless
public class SubmissionBean implements SubmissionBeanRemote {
    @PersistenceContext EntityManager em;
    
    @Override
    public List<ModuleSubmissionDetails> getSubmissions() {
        int status = 0;
        List <Modulesubmission> subs = em.createNamedQuery("Modulesubmission.findAll").getResultList();
        
        List<ModuleSubmissionDetails> output = new ArrayList<>();
        for (Modulesubmission obj : subs) {
            output.add(
                    new ModuleSubmissionDetails(
                        obj.getSubmissionID(), obj.getStatus(), obj.getModuleID().getModuleID(),
                        obj.getUserID().getUserID(), obj.getType(), obj.getContent(),
                            obj.getCreationDate()
                    )
            );
                    
        }
        
        return output;
    }
    
}
