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
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserID", query = "SELECT u FROM Users u WHERE u.userID = :userID"),
    @NamedQuery(name = "Users.findByFirstname", query = "SELECT u FROM Users u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "Users.findByLastname", query = "SELECT u FROM Users u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByIsAdmin", query = "SELECT u FROM Users u WHERE u.isAdmin = :isAdmin")})
public class Users implements Serializable {

    @Column(name = "isAdmin")
    private Integer isAdmin;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "userID")
    private Integer userID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "lastname")
    private String lastname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Modulefeedback> modulefeedbackCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Blog> blogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Collection<CourseMembers> courseMembersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Helpreply> helpreplyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Progressionplan> progressionplanCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Helprequest> helprequestCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Modulesubmission> modulesubmissionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Newsposts> newspostsCollection;

    public Users() {
    }

    public Users(Integer userID) {
        this.userID = userID;
    }

    public Users(Integer userID, String firstname, String lastname, String username, String password, String email, int isAdmin) {
        this.userID = userID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @XmlTransient
    public Collection<Modulefeedback> getModulefeedbackCollection() {
        return modulefeedbackCollection;
    }

    public void setModulefeedbackCollection(Collection<Modulefeedback> modulefeedbackCollection) {
        this.modulefeedbackCollection = modulefeedbackCollection;
    }

    @XmlTransient
    public Collection<Blog> getBlogCollection() {
        return blogCollection;
    }

    public void setBlogCollection(Collection<Blog> blogCollection) {
        this.blogCollection = blogCollection;
    }

    @XmlTransient
    public Collection<CourseMembers> getCourseMembersCollection() {
        return courseMembersCollection;
    }

    public void setCourseMembersCollection(Collection<CourseMembers> courseMembersCollection) {
        this.courseMembersCollection = courseMembersCollection;
    }

    @XmlTransient
    public Collection<Helpreply> getHelpreplyCollection() {
        return helpreplyCollection;
    }

    public void setHelpreplyCollection(Collection<Helpreply> helpreplyCollection) {
        this.helpreplyCollection = helpreplyCollection;
    }

    @XmlTransient
    public Collection<Progressionplan> getProgressionplanCollection() {
        return progressionplanCollection;
    }

    public void setProgressionplanCollection(Collection<Progressionplan> progressionplanCollection) {
        this.progressionplanCollection = progressionplanCollection;
    }

    @XmlTransient
    public Collection<Helprequest> getHelprequestCollection() {
        return helprequestCollection;
    }

    public void setHelprequestCollection(Collection<Helprequest> helprequestCollection) {
        this.helprequestCollection = helprequestCollection;
    }

    @XmlTransient
    public Collection<Modulesubmission> getModulesubmissionCollection() {
        return modulesubmissionCollection;
    }

    public void setModulesubmissionCollection(Collection<Modulesubmission> modulesubmissionCollection) {
        this.modulesubmissionCollection = modulesubmissionCollection;
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
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Users[ userID=" + userID + " ]";
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
    
}
