/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Christian
 */
@Entity
@Table(name = "courses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Courses.findAll", query = "SELECT c FROM Courses c"),
    @NamedQuery(name = "Courses.findByCourseID", query = "SELECT c FROM Courses c WHERE c.courseID = :courseID"),
    @NamedQuery(name = "Courses.findByCourseName", query = "SELECT c FROM Courses c WHERE c.courseName = :courseName"),
    @NamedQuery(name = "Courses.findByCourseStartDate", query = "SELECT c FROM Courses c WHERE c.courseStartDate = :courseStartDate"),
    @NamedQuery(name = "Courses.findByCourseEndDate", query = "SELECT c FROM Courses c WHERE c.courseEndDate = :courseEndDate")})
public class Courses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "courseID")
    private Integer courseID;
    @Size(max = 30)
    @Column(name = "courseName")
    private String courseName;
    @Size(max = 30)
    @Column(name = "courseStartDate")
    private String courseStartDate;
    @Size(max = 30)
    @Column(name = "courseEndDate")
    private String courseEndDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courses")
    private Collection<CourseMembers> courseMembersCollection;

    public Courses() {
    }

    public Courses(Integer courseID) {
        this.courseID = courseID;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    @XmlTransient
    public Collection<CourseMembers> getCourseMembersCollection() {
        return courseMembersCollection;
    }

    public void setCourseMembersCollection(Collection<CourseMembers> courseMembersCollection) {
        this.courseMembersCollection = courseMembersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseID != null ? courseID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Courses)) {
            return false;
        }
        Courses other = (Courses) object;
        if ((this.courseID == null && other.courseID != null) || (this.courseID != null && !this.courseID.equals(other.courseID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Courses[ courseID=" + courseID + " ]";
    }
    
}
