/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface SubmissionBeanRemote {
    List<ModuleSubmissionDetails> getSubmissions(int courseID);
    void assignSubmissionToUser(ModuleSubmissionDetails sub, int userID);

    ArrayList<ModuleSubmissionDetails> getAssignedModulesForUser(int userID, int courseID);
    void unAssignModuleSubmission(ModuleSubmissionDetails sub);
    void saveTeacherFeeback(ModuleSubmissionDetails sub, SubmissionFeedbackDetails details);
    SubmissionFeedbackDetails getFeedbackDetailsFromSubmissionID(ModuleSubmissionDetails sub);
    void declineSubmission(ModuleSubmissionDetails sub, SubmissionFeedbackDetails feedback);
    void acceptSubmission(ModuleSubmissionDetails sub, SubmissionFeedbackDetails feedback);
}
