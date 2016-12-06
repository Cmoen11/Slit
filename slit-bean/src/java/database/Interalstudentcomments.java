/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Interalstudentcomments.findByCreationDate", query = "SELECT i FROM Interalstudentcomments i WHERE i.creationDate = :creationDate"),
    @NamedQuery(name = "Interalstudentcomments.findByUserIDandCourseID", query = "SELECT i FROM Interalstudentcomments i WHERE i.courseID = :courseID and i.studentID = :studentID"),
    @NamedQuery(name = "Interalstudentcomments.findByCommentID", query = "SELECT i FROM Interalstudentcomments i WHERE i.commentID = :commentID")})
public class Interalstudentcomments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creationDate")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "comment")
    private String comment;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "commentID")
    private Integer commentID;
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    @ManyToOne(optional = false)
    private Courses courseID;
    @JoinColumn(name = "studentID", referencedColumnName = "userID")
    @ManyToOne(optional = false)
    private Users studentID;
    @JoinColumn(name = "teacherID", referencedColumnName = "userID")
    @ManyToOne(optional = false)
    private Users teacherID;

    public Interalstudentcomments() {
    }

    public Interalstudentcomments(Integer commentID) {
        this.commentID = commentID;
    }

    public Interalstudentcomments(Integer commentID, Date creationDate, String comment) {
        this.commentID = commentID;
        this.creationDate = creationDate;
        this.comment = comment;
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

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public Courses getCourseID() {
        return courseID;
    }

    public void setCourseID(Courses courseID) {
        this.courseID = courseID;
    }

    public Users getStudentID() {
        return studentID;
    }

    public void setStudentID(Users studentID) {
        this.studentID = studentID;
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
        hash += (commentID != null ? commentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interalstudentcomments)) {
            return false;
        }
        Interalstudentcomments other = (Interalstudentcomments) object;
        if ((this.commentID == null && other.commentID != null) || (this.commentID != null && !this.commentID.equals(other.commentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Interalstudentcomments[ commentID=" + commentID + " ]";
    }
    
}
