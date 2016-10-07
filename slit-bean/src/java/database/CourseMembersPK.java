/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Christian
 */
@Embeddable
public class CourseMembersPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "courseID")
    private int courseID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userID")
    private int userID;

    public CourseMembersPK() {
    }

    public CourseMembersPK(int courseID, int userID) {
        this.courseID = courseID;
        this.userID = userID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) courseID;
        hash += (int) userID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseMembersPK)) {
            return false;
        }
        CourseMembersPK other = (CourseMembersPK) object;
        if (this.courseID != other.courseID) {
            return false;
        }
        if (this.userID != other.userID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.CourseMembersPK[ courseID=" + courseID + ", userID=" + userID + " ]";
    }
    
}
