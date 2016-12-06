/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Christian
 */
@Entity
@Table(name = "interalstudentcomments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Interalstudentcomments.findAll", query = "SELECT i FROM Interalstudentcomments i"),
    @NamedQuery(name = "Interalstudentcomments.findByStudentID", query = "SELECT i FROM Interalstudentcomments i WHERE i.interalstudentcommentsPK.studentID = :studentID"),
    @NamedQuery(name = "Interalstudentcomments.findByCourseID", query = "SELECT i FROM Interalstudentcomments i WHERE i.interalstudentcommentsPK.courseID = :courseID"),
    @NamedQuery(name = "Interalstudentcomments.findByCreationDate", query = "SELECT i FROM Interalstudentcomments i WHERE i.creationDate = :creationDate")})
public class Interalstudentcomments implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InteralstudentcommentsPK interalstudentcommentsPK;
    @Column(name = "creationDate")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "studentID", referencedColumnName = "userID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;
    @JoinColumn(name = "teacherID", referencedColumnName = "userID")
    @ManyToOne
    private Users teacherID;

    public Interalstudentcomments() {
    }

    public Interalstudentcomments(InteralstudentcommentsPK interalstudentcommentsPK) {
        this.interalstudentcommentsPK = interalstudentcommentsPK;
    }

    public Interalstudentcomments(int studentID, int courseID) {
        this.interalstudentcommentsPK = new InteralstudentcommentsPK(studentID, courseID);
    }

    public InteralstudentcommentsPK getInteralstudentcommentsPK() {
        return interalstudentcommentsPK;
    }

    public void setInteralstudentcommentsPK(InteralstudentcommentsPK interalstudentcommentsPK) {
        this.interalstudentcommentsPK = interalstudentcommentsPK;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Users teacherID) {
        this.teacherID = teacherID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (interalstudentcommentsPK != null ? interalstudentcommentsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interalstudentcomments)) {
            return false;
        }
        Interalstudentcomments other = (Interalstudentcomments) object;
        if ((this.interalstudentcommentsPK == null && other.interalstudentcommentsPK != null) || (this.interalstudentcommentsPK != null && !this.interalstudentcommentsPK.equals(other.interalstudentcommentsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Interalstudentcomments[ interalstudentcommentsPK=" + interalstudentcommentsPK + " ]";
    }
    
}
