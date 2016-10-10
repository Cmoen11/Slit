/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Christian
 */
@Entity
@Table(name = "course_members")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseMembers.findAll", query = "SELECT c FROM CourseMembers c"),
    @NamedQuery(name = "CourseMembers.findByCourseID", query = "SELECT c FROM CourseMembers c WHERE c.courseMembersPK.courseID = :courseID"),
    @NamedQuery(name = "CourseMembers.findByUserID", query = "SELECT c FROM CourseMembers c WHERE c.courseMembersPK.userID = :userID")})
public class CourseMembers implements Serializable {

    @Column(name = "isTeacher")
    private Integer isTeacher;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CourseMembersPK courseMembersPK;
    @JoinColumn(name = "courseID", referencedColumnName = "courseID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Courses courses;
    @JoinColumn(name = "userID", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public CourseMembers() {
    }

    public CourseMembers(CourseMembersPK courseMembersPK) {
        this.courseMembersPK = courseMembersPK;
    }

    public CourseMembers(int courseID, int userID) {
        this.courseMembersPK = new CourseMembersPK(courseID, userID);
    }

    public CourseMembersPK getCourseMembersPK() {
        return courseMembersPK;
    }

    public void setCourseMembersPK(CourseMembersPK courseMembersPK) {
        this.courseMembersPK = courseMembersPK;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseMembersPK != null ? courseMembersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseMembers)) {
            return false;
        }
        CourseMembers other = (CourseMembers) object;
        if ((this.courseMembersPK == null && other.courseMembersPK != null) || (this.courseMembersPK != null && !this.courseMembersPK.equals(other.courseMembersPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.CourseMembers[ courseMembersPK=" + courseMembersPK + " ]";
    }

    public Integer getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Integer isTeacher) {
        this.isTeacher = isTeacher;
    }
    
}
