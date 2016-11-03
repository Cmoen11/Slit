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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Christian
 */
@Entity
@Table(name = "helpreply")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Helpreply.findAll", query = "SELECT h FROM Helpreply h"),
    @NamedQuery(name = "Helpreply.findByReplyID", query = "SELECT h FROM Helpreply h WHERE h.replyID = :replyID"),
    @NamedQuery(name = "Helpreply.findByTitle", query = "SELECT h FROM Helpreply h WHERE h.title = :title"),
    @NamedQuery(name = "Helpreply.findByContent", query = "SELECT h FROM Helpreply h WHERE h.content = :content"),
    @NamedQuery(name = "Helpreply.findByCreationDate", query = "SELECT h FROM Helpreply h WHERE h.creationDate = :creationDate")})
public class Helpreply implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "replyID")
    private Integer replyID;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "content")
    private String content;
    @Basic(optional = false)
    @Column(name = "creationDate")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @ManyToOne(optional = false)
    private Users userID;
    @JoinColumn(name = "requestID", referencedColumnName = "requestID")
    @ManyToOne(optional = false)
    private Helprequest requestID;

    public Helpreply() {
    }

    public Helpreply(Integer replyID) {
        this.replyID = replyID;
    }

    public Helpreply(Integer replyID, String title, String content, Date creationDate) {
        this.replyID = replyID;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
    }

    public Integer getReplyID() {
        return replyID;
    }

    public void setReplyID(Integer replyID) {
        this.replyID = replyID;
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

    public Helprequest getRequestID() {
        return requestID;
    }

    public void setRequestID(Helprequest requestID) {
        this.requestID = requestID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (replyID != null ? replyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Helpreply)) {
            return false;
        }
        Helpreply other = (Helpreply) object;
        if ((this.replyID == null && other.replyID != null) || (this.replyID != null && !this.replyID.equals(other.replyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Helpreply[ replyID=" + replyID + " ]";
    }
    
}
