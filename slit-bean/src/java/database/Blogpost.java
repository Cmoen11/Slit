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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * @author christian
 */
@Entity
@Table(name = "blogpost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Blogpost.findAll", query = "SELECT b FROM Blogpost b")
    , @NamedQuery(name = "Blogpost.findByPostID", query = "SELECT b FROM Blogpost b WHERE b.postID = :postID")
    , @NamedQuery(name = "Blogpost.findByContent", query = "SELECT b FROM Blogpost b WHERE b.content = :content")
    , @NamedQuery(name = "Blogpost.findByCreationDate", query = "SELECT b FROM Blogpost b WHERE b.creationDate = :creationDate")
    , @NamedQuery(name = "Blogpost.findByTitle", query = "SELECT b FROM Blogpost b WHERE b.title = :title")})
public class Blogpost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "postID")
    private Integer postID;
    @Size(max = 255)
    @Column(name = "content")
    private String content;
    @Column(name = "creationDate")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Size(max = 255)
    @Column(name = "title")
    private String title;
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    @ManyToOne
    private Courses courseID;
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @ManyToOne
    private Users userID;

    public Blogpost() {
    }

    public Blogpost(Integer postID) {
        this.postID = postID;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Courses getCourseID() {
        return courseID;
    }

    public void setCourseID(Courses courseID) {
        this.courseID = courseID;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
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
        if (!(object instanceof Blogpost)) {
            return false;
        }
        Blogpost other = (Blogpost) object;
        if ((this.postID == null && other.postID != null) || (this.postID != null && !this.postID.equals(other.postID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Blogpost[ postID=" + postID + " ]";
    }
    
}
