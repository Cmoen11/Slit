
package transferClasses;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Christian
 */
public class StudentSubmissionHistory implements Serializable{
    private SimpleStringProperty moduleName, date, status;
    
    public StudentSubmissionHistory(Date date, String moduleName, int status) {
        this.moduleName = new SimpleStringProperty(moduleName);
        switch(status) {
            case (0) :
                this.status = new SimpleStringProperty("Utildelt");
                break;
            case (1) :
                this.status = new SimpleStringProperty("Tildelt");
                break;
            case (2) :
                this.status = new SimpleStringProperty("Avist");
                break;
            case (3) :
                this.status= new SimpleStringProperty("Godkjent");
            default :
                System.out.println("what the fuck?");
        }
        this.date = new SimpleStringProperty(date.getDay() + "/" + date.getMonth() + "-" + date.getYear());
        
        
    }
    
    public StudentSubmissionHistory() {
        
    }

    
    
}
