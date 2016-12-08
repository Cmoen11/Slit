package slit.Teacher.popups;



import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Christian
 */
public class SubmissionHistory{
    private SimpleStringProperty moduleName, date, status;
    
    public SubmissionHistory(String date, String moduleName, int status) {
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
        this.date = new SimpleStringProperty(date);
        
        
    }
    
    public SubmissionHistory() {
        
    }
    
    public final StringProperty moduleNameProperty()         {return moduleName;}
    public final StringProperty dateProperty()               {return date;}
    public final StringProperty statusProperty()             {return status;}

    public SimpleStringProperty getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName.set(moduleName);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
    
    
}
