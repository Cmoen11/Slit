/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Courses.findByCourseEndDate", query = "SELECT c FROM Courses c WHERE c.courseEndDate = :courseEndDate"),
    @NamedQuery(name = "Courses.findByCourseCode", query = "SELECT c FROM Courses c WHERE c.courseCode = :courseCode")})
public class Courses implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseID")
    private Collection<Interalstudentcomments> interalstudentcommentsCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "courseID")
    private Integer courseID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "courseName")
    private String courseName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "courseStartDate")
    @Temporal(TemporalType.DATE)
    private Date courseStartDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "courseEndDate")
    @Temporal(TemporalType.DATE)
    private Date courseEndDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "courseCode")
    private String courseCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courses")
    private Collection<CourseMembers> courseMembersCollection;
    @OneToMany(mappedBy = "courseID")
    private Collection<Progressionplan> progressionplanCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseID")
    private Collection<Blogpost> blogpostCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseID")
    private Collection<Helprequest> helprequestCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseID")
    private Collection<Newsposts> newspostsCollection;

    public Courses() {
    }

    public Courses(Integer courseID) {
        this.courseID = courseID;
    }

    public Courses(Integer courseID, String courseName, Date courseStartDate, Date courseEndDate, String courseCode) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseCode = courseCode;
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

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @XmlTransient
    public Collection<CourseMembers> getCourseMembersCollection() {
        return courseMembersCollection;
    }

    public void setCourseMembersCollection(Collection<CourseMembers> courseMembersCollection) {
        this.courseMembersCollection = courseMembersCollection;
    }

    @XmlTransient
    public Collection<Progressionplan> getProgressionplanCollection() {
        return progressionplanCollection;
    }

    public void setProgressionplanCollection(Collection<Progressionplan> progressionplanCollection) {
        this.progressionplanCollection = progressionplanCollection;
    }

    @XmlTransient
    public Collection<Blogpost> getBlogpostCollection() {
        return blogpostCollection;
    }

    public void setBlogpostCollection(Collection<Blogpost> blogpostCollection) {
        this.blogpostCollection = blogpostCollection;
    }

    @XmlTransient
    public Collection<Helprequest> getHelprequestCollection() {
        return helprequestCollection;
    }

    public void setHelprequestCollection(Collection<Helprequest> helprequestCollection) {
        this.helprequestCollection = helprequestCollection;
    }

    @XmlTransient
    public Collection<Newsposts> getNewspostsCollection() {
        return newspostsCollection;
    }

    public void setNewspostsCollection(Collection<Newsposts> newspostsCollection) {
        this.newspostsCollection = newspostsCollection;
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

    @XmlTransient
    public Collection<Interalstudentcomments> getInteralstudentcommentsCollection() {
        return interalstudentcommentsCollection;
    }

    public void setInteralstudentcommentsCollection(Collection<Interalstudentcomments> interalstudentcommentsCollection) {
        this.interalstudentcommentsCollection = interalstudentcommentsCollection;
    }
    
}
