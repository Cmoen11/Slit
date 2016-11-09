/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import database.Module;
import database.Modulefeedback;
import database.Modulesubmission;
import database.Users;
import java.util.ArrayList;
import java.util.Iterator;
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
    public List<ModuleSubmissionDetails> getSubmissions(int courseID) {
        int status = 0;
        List <Modulesubmission> subs = em.createNamedQuery(
                "Modulesubmission.findByStatusAndCourse")
                .setParameter("courseID", courseID)
                .setParameter("status", status)
                .getResultList();
        
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
    /**
     * Assign the selected user to the moduleSubmission
     * @param sub       the moduleSubmission that are to be applied to the user
     * @param userID    the user that are to be asigned to the submission.
     */
    @Override
    public void assignSubmissionToUser(ModuleSubmissionDetails sub, int userID) {
        // adding the user to the moduleSubmission
        Modulefeedback feedback = new Modulefeedback();
        feedback.setFeedbackID(Integer.MAX_VALUE);
        feedback.setContent(null);
        feedback.setSubmissionID(em.find(Modulesubmission.class, sub.getSubmissionID()));
        feedback.setUserID(em.find(Users.class, userID));
        
        em.persist(feedback);
        
        //set the status to 1, directly translated to " under processing".
        Modulesubmission ms = em.find(Modulesubmission.class, sub.getSubmissionID());
        ms.setStatus(1);   
        em.merge(ms);
    }
    
    /**
     * get all assigned modules that are not processed.
     * @param userID
     * @param courseID
     * @return 
     */
    @Override
    public ArrayList<ModuleSubmissionDetails> getAssignedModulesForUser(int userID, int courseID) {
        List<Modulefeedback> feedback;
        List<Modulesubmission> allAssignedSubmissions = em.createNamedQuery("Modulesubmission.findByStatus")
                .setParameter("status", 1)
                .getResultList();
        
        // delete all submissions that do not match the selected user
        for (Iterator<Modulesubmission> it = allAssignedSubmissions.iterator(); it.hasNext();) {
            Modulesubmission obj = it.next();
            Module module = em.find(Module.class, obj.getModuleID().getModuleID());
            
            for (Modulefeedback item : obj.getModulefeedbackCollection()) {
                if (item.getUserID().getUserID() != userID ||
                        module.getCourseID() != courseID)
                    it.remove();
            }
        }
        
        // prepare to send results.
        ArrayList<ModuleSubmissionDetails> output = new ArrayList<>();
        for (Modulesubmission obj: allAssignedSubmissions) {
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
    
    @Override
    public void unAssignModuleSubmission(ModuleSubmissionDetails sub) {
        Modulesubmission msub;
        msub = em.find(Modulesubmission.class, sub.getSubmissionID());
        msub.setStatus(0);
        em.merge(msub);
        int index = msub.getModulefeedbackCollection().iterator().next().getFeedbackID();
        System.out.println("unassign, feedback id = " + index);
        Modulefeedback feedback = em.find(Modulefeedback.class, index);
        em.remove(feedback);
        
    }
    
    /**
     * @param sub
     * @param details 
     */
    @Override
    public void saveTeacherFeeback(ModuleSubmissionDetails sub, SubmissionFeedbackDetails details) {
        Modulesubmission moduleSub = em.find(Modulesubmission.class, sub.getSubmissionID());
        Modulefeedback feedback = moduleSub.getModulefeedbackCollection().iterator().next();
        feedback.setContent(details.getContent());
        em.merge(feedback);
    }
    
    @Override
    public SubmissionFeedbackDetails getFeedbackDetailsFromSubmissionID(ModuleSubmissionDetails sub) {
        Modulesubmission submission = em.find(Modulesubmission.class, sub.getSubmissionID());
        Modulefeedback feedback = (Modulefeedback) em.createNamedQuery("Modulefeedback.findBySubmissionID")
                .setParameter("submissionID", submission).getSingleResult();
        
        SubmissionFeedbackDetails feedbackDetails = new SubmissionFeedbackDetails();
        feedbackDetails.setContent(feedback.getContent());
        feedbackDetails.setFeedbackID(feedback.getFeedbackID());
        feedbackDetails.setUserID(feedback.getUserID().getUserID());
        feedbackDetails.setFeedbackID(feedback.getFeedbackID());
        
        return feedbackDetails;
    }
    /**
     * decline a submission
     * @param sub
     * @param feedback 
     */
    @Override
    public void declineSubmission(ModuleSubmissionDetails sub, SubmissionFeedbackDetails feedback) {
        // update status on module
        Modulesubmission submission = em.find(Modulesubmission.class, sub.getSubmissionID());
        submission.setStatus(2); // set it to declined. 
        em.persist(submission);
        
        // update feedback
        // update feedback
        Modulefeedback updateFeedback = (Modulefeedback) 
                em.createNamedQuery("Modulefeedback.findBySubmissionID")
                        .setParameter("submissionID", submission)
                        .getSingleResult();
        
        updateFeedback.setContent(feedback.getContent());
        em.persist(updateFeedback);  
        
    }
    
    /**
     * Accept submission
     * @param sub
     * @param feedback 
     */
    @Override
    public void acceptSubmission(ModuleSubmissionDetails sub, SubmissionFeedbackDetails feedback) {
        // update status on module
        Modulesubmission submission = em.find(Modulesubmission.class, sub.getSubmissionID());
        submission.setStatus(3); // set it to accept. 
        em.persist(submission);
        
        // update feedback
        Modulefeedback updateFeedback = (Modulefeedback) 
                em.createNamedQuery("Modulefeedback.findBySubmissionID")
                        .setParameter("submissionID", submission)
                        .getSingleResult();
        
        updateFeedback.setContent(feedback.getContent());
        em.persist(updateFeedback);  
        
    }
    
}
