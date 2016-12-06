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
public class InteralstudentcommentsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "studentID")
    private int studentID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "courseID")
    private int courseID;

    public InteralstudentcommentsPK() {
    }

    public InteralstudentcommentsPK(int studentID, int courseID) {
        this.studentID = studentID;
        this.courseID = courseID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) studentID;
        hash += (int) courseID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InteralstudentcommentsPK)) {
            return false;
        }
        InteralstudentcommentsPK other = (InteralstudentcommentsPK) object;
        if (this.studentID != other.studentID) {
            return false;
        }
        if (this.courseID != other.courseID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.InteralstudentcommentsPK[ studentID=" + studentID + ", courseID=" + courseID + " ]";
    }
    
}
