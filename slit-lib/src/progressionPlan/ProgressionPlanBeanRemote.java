/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progressionPlan;

import java.sql.Date;
import javax.ejb.Remote;

/**
 *
 * @author martinvenaas
 */
@Remote
public interface ProgressionPlanBeanRemote {

    void addProgressionPlan(int userID, int courseID);
    
    long compareDates(int planID, int moduleID);

    public void addProgressionEntry(java.util.Date completionDate, int module, int planID);
    
}


