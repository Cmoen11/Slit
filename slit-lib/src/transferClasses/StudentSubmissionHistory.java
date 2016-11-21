
package transferClasses;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author Christian
 */
public class StudentSubmissionHistory implements Serializable{
    private String moduleName, date;
    int status;

    public StudentSubmissionHistory(String moduleName, Date date, int status) {
        this.moduleName = moduleName;
        this.date = "" + date.getDay() + "/" + date.getMonth() + "-" + date.getYear();
        this.status = status;
    }

    public StudentSubmissionHistory() {
    }
    
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}


