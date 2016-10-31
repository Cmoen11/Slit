/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Christian
 */
@Entity
@Table(name = "modulefeedback")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulefeedback.findAll", query = "SELECT m FROM Modulefeedback m"),
    @NamedQuery(name = "Modulefeedback.findAllFromUserAndStatus=1", query = 
            "SELECT m FROM Modulefeedback m, Modulesubmission ms WHERE m.userID = :userID AND m.submissionID = ms.submissionID and ms.status = 1"),
    @NamedQuery(name = "Modulefeedback.findByFeedbackID", query = "SELECT m FROM Modulefeedback m WHERE m.feedbackID = :feedbackID")})
public class Modulefeedback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "feedbackID")
    private Integer feedbackID;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "content")
    private String content;
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @ManyToOne(optional = false)
    private Users userID;
    @JoinColumn(name = "submissionID", referencedColumnName = "submissionID")
    @ManyToOne(optional = false)
    private Modulesubmission submissionID;

    public Modulefeedback() {
    }

    public Modulefeedback(Integer feedbackID) {
        this.feedbackID = feedbackID;
    }

    public Integer getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(Integer feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public Modulesubmission getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(Modulesubmission submissionID) {
        this.submissionID = submissionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feedbackID != null ? feedbackID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modulefeedback)) {
            return false;
        }
        Modulefeedback other = (Modulefeedback) object;
        if ((this.feedbackID == null && other.feedbackID != null) || (this.feedbackID != null && !this.feedbackID.equals(other.feedbackID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Modulefeedback[ feedbackID=" + feedbackID + " ]";
    }
    
}
