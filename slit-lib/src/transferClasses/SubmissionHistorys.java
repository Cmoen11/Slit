
package transferClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Christian
 */
public class SubmissionHistorys implements Serializable{
    
    int userID;
    int courseID;
    ArrayList<StudentSubmissionHistory> history;

    public SubmissionHistorys(int userID, int courseID) {
        this.userID = userID;
        this.courseID = courseID;
        this.history = new ArrayList<>();
    }
    
    public void addHistorySubmission(StudentSubmissionHistory obj) {
        history.add(obj);
    }
    
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public ArrayList<StudentSubmissionHistory> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<StudentSubmissionHistory> history) {
        this.history = history;
    }
    
    
}
