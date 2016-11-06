/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import java.util.List;
import javax.ejb.Remote;
import transferClasses.HelpRequestDetails;

/**
 *
 * @author Christian
 */
@Remote
public interface HelpRequestBeanRemote {

    List<HelpRequestDetails> getAllUnassignedHelpRequests(int courseID);
    void assignHelpRequest(HelpRequestDetails helprequest, int userID);
    List<HelpRequestDetails> getAssignedHelpRequests(int userID, int courseID);
    public void unassignHelpRequest(HelpRequestDetails helprequest);
    
}
