/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferClasses;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Christian
 */
public class HelpRequestDetails implements Serializable{
    int requestID, courseID, userID, status;
    String content, title;
    Date creationDate;

    public HelpRequestDetails() {
    }

    public HelpRequestDetails(int requestID,
            int courseID,
            int userID,
            int status,
            String content,
            String title,
            Date creationDate) {
        this.requestID = requestID;
        this.courseID = courseID;
        this.userID = userID;
        this.content = content;
        this.title = title;
        this.creationDate = creationDate;
        this.status = status;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    
    
}
