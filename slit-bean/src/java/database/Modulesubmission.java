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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Christian
 */
@Entity
@Table(name = "modulesubmission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulesubmission.findAll", query = "SELECT m FROM Modulesubmission m"),
    @NamedQuery(name = "Modulesubmission.findBySubmissionID", query = "SELECT m FROM Modulesubmission m WHERE m.submissionID = :submissionID"),
    @NamedQuery(name = "Modulesubmission.findByCreationDate", query = "SELECT m FROM Modulesubmission m WHERE m.creationDate = :creationDate"),
    @NamedQuery(name = "Modulesubmission.findByStatus", query = "SELECT m FROM Modulesubmission m WHERE m.status = :status"),
    @NamedQuery(name = "Modulesubmission.findByContent", query = "SELECT m FROM Modulesubmission m WHERE m.content = :content"),
    @NamedQuery(name = "Modulesubmission.findByType", query = "SELECT m FROM Modulesubmission m WHERE m.type = :type")})
public class Modulesubmission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "submissionID")
    private Integer submissionID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creationDate")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @Size(max = 255)
    @Column(name = "content")
    private String content;
    @Size(max = 255)
    @Column(name = "type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submissionID")
    private Collection<Modulefeedback> modulefeedbackCollection;
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @ManyToOne(optional = false)
    private Users userID;
    @JoinColumn(name = "moduleID", referencedColumnName = "moduleID")
    @ManyToOne(optional = false)
    private Module moduleID;
    @JoinColumn(name = "fileID", referencedColumnName = "fileID")
    @ManyToOne
    private File fileID;

    public Modulesubmission() {
    }

    public Modulesubmission(Integer submissionID) {
        this.submissionID = submissionID;
    }

    public Modulesubmission(Integer submissionID, Date creationDate, int status) {
        this.submissionID = submissionID;
        this.creationDate = creationDate;
        this.status = status;
    }

    public Integer getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(Integer submissionID) {
        this.submissionID = submissionID;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Modulefeedback> getModulefeedbackCollection() {
        return modulefeedbackCollection;
    }

    public void setModulefeedbackCollection(Collection<Modulefeedback> modulefeedbackCollection) {
        this.modulefeedbackCollection = modulefeedbackCollection;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public Module getModuleID() {
        return moduleID;
    }

    public void setModuleID(Module moduleID) {
        this.moduleID = moduleID;
    }

    public File getFileID() {
        return fileID;
    }

    public void setFileID(File fileID) {
        this.fileID = fileID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (submissionID != null ? submissionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modulesubmission)) {
            return false;
        }
        Modulesubmission other = (Modulesubmission) object;
        if ((this.submissionID == null && other.submissionID != null) || (this.submissionID != null && !this.submissionID.equals(other.submissionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Modulesubmission[ submissionID=" + submissionID + " ]";
    }
    
}
