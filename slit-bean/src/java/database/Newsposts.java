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
@Table(name = "newsposts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Newsposts.findAll", query = "SELECT n FROM Newsposts n"),
    @NamedQuery(name = "Newsposts.findByPostID", query = "SELECT n FROM Newsposts n WHERE n.postID = :postID"),
    @NamedQuery(name = "Newsposts.findByTitle", query = "SELECT n FROM Newsposts n WHERE n.title = :title"),
    @NamedQuery(name = "Newsposts.findByCreationDate", query = "SELECT n FROM Newsposts n WHERE n.creationDate = :creationDate")})
public class Newsposts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "postID")
    private Integer postID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "content")
    private String content;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creationDate")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @ManyToOne(optional = false)
    private Users userID;
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    @ManyToOne(optional = false)
    private Courses courseID;

    public Newsposts() {
    }

    public Newsposts(Integer postID) {
        this.postID = postID;
    }

    public Newsposts(Integer postID, String title, String content, Date creationDate) {
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public Courses getCourseID() {
        return courseID;
    }

    public void setCourseID(Courses courseID) {
        this.courseID = courseID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postID != null ? postID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Newsposts)) {
            return false;
        }
        Newsposts other = (Newsposts) object;
        if ((this.postID == null && other.postID != null) || (this.postID != null && !this.postID.equals(other.postID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Newsposts[ postID=" + postID + " ]";
    }
    
}
