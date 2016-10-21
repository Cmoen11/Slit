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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Christian
 */
@Entity
@Table(name = "blog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Blog.findAll", query = "SELECT b FROM Blog b"),
    @NamedQuery(name = "Blog.findByBlogID", query = "SELECT b FROM Blog b WHERE b.blogID = :blogID"),
    @NamedQuery(name = "Blog.findByCreationDate", query = "SELECT b FROM Blog b WHERE b.creationDate = :creationDate")})
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "blogID")
    private Integer blogID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creationDate")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @ManyToOne(optional = false)
    private Users userID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blogID")
    private Collection<Blogpost> blogpostCollection;

    public Blog() {
    }

    public Blog(Integer blogID) {
        this.blogID = blogID;
    }

    public Blog(Integer blogID, Date creationDate) {
        this.blogID = blogID;
        this.creationDate = creationDate;
    }

    public Integer getBlogID() {
        return blogID;
    }

    public void setBlogID(Integer blogID) {
        this.blogID = blogID;
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

    @XmlTransient
    public Collection<Blogpost> getBlogpostCollection() {
        return blogpostCollection;
    }

    public void setBlogpostCollection(Collection<Blogpost> blogpostCollection) {
        this.blogpostCollection = blogpostCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blogID != null ? blogID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Blog)) {
            return false;
        }
        Blog other = (Blog) object;
        if ((this.blogID == null && other.blogID != null) || (this.blogID != null && !this.blogID.equals(other.blogID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Blog[ blogID=" + blogID + " ]";
    }
    
}
