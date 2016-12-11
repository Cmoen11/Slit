
package progressionPlan;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author martinvenaas
 */
public class ProgressionEntry implements Serializable {

    private int progressionEntryID ,planID, moduleID;
    private Date completionDate;
    
    public ProgressionEntry(
            int progressionEntryID,
            int planID, int moduleID, 
            Date completionDate) {
        
        this.progressionEntryID = progressionEntryID;
        this.planID = planID;
        this.moduleID = moduleID;
        this.completionDate = completionDate;
        
    }

    public int getProgressionEntryID() {
        return progressionEntryID;
    }

    public void setProgressionEntryID(int progressionEntryID) {
        this.progressionEntryID = progressionEntryID;
    }

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }
    
    
    
}
