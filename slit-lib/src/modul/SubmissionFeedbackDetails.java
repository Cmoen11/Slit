
package modul;

import java.io.Serializable;

/**
 *
 * @author Christian
 */
public class SubmissionFeedbackDetails implements Serializable{
    
    private int feedbackID, submissionID, userID;
    String content;
    
    
   public SubmissionFeedbackDetails() {
       
   }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(int submissionID) {
        this.submissionID = submissionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
   
}
