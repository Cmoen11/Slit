/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Christian
 */
public class ModuleSubmissionDetails implements Serializable{
    private int submissionID, status, moduleID, userID;
    private String content, type;
    private Date creationDate;
    
    public ModuleSubmissionDetails() {
        
    }
    
    public ModuleSubmissionDetails(int submissionID, int status, int moduleID, int userID, String type, String content, Date creationDate ) {
        this.submissionID = submissionID;
        this.status = status;
        this.moduleID = moduleID;
        this.userID = userID;
        this.type = type;
        this.content = content;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(int submissionID) {
        this.submissionID = submissionID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
    
}
