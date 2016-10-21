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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "helprequest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Helprequest.findAll", query = "SELECT h FROM Helprequest h"),
    @NamedQuery(name = "Helprequest.findByRequestID", query = "SELECT h FROM Helprequest h WHERE h.requestID = :requestID"),
    @NamedQuery(name = "Helprequest.findByTitle", query = "SELECT h FROM Helprequest h WHERE h.title = :title"),
    @NamedQuery(name = "Helprequest.findByCreationDate", query = "SELECT h FROM Helprequest h WHERE h.creationDate = :creationDate"),
    @NamedQuery(name = "Helprequest.findByStatus", query = "SELECT h FROM Helprequest h WHERE h.status = :status")})
public class Helprequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "requestID")
    private Integer requestID;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestID")
    private Collection<Helpreply> helpreplyCollection;
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @ManyToOne(optional = false)
    private Users userID;
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    @ManyToOne(optional = false)
    private Courses courseID;

    public Helprequest() {
    }

    public Helprequest(Integer requestID) {
        this.requestID = requestID;
    }

    public Helprequest(Integer requestID, String title, String content, Date creationDate, int status) {
        this.requestID = requestID;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.status = status;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Helpreply> getHelpreplyCollection() {
        return helpreplyCollection;
    }

    public void setHelpreplyCollection(Collection<Helpreply> helpreplyCollection) {
        this.helpreplyCollection = helpreplyCollection;
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
        hash += (requestID != null ? requestID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Helprequest)) {
            return false;
        }
        Helprequest other = (Helprequest) object;
        if ((this.requestID == null && other.requestID != null) || (this.requestID != null && !this.requestID.equals(other.requestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Helprequest[ requestID=" + requestID + " ]";
    }
    
}
