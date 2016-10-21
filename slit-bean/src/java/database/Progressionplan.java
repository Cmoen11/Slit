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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Christian
 */
@Entity
@Table(name = "progressionplan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Progressionplan.findAll", query = "SELECT p FROM Progressionplan p"),
    @NamedQuery(name = "Progressionplan.findByPlanID", query = "SELECT p FROM Progressionplan p WHERE p.planID = :planID")})
public class Progressionplan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "planID")
    private Integer planID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planID")
    private Collection<Progressionentry> progressionentryCollection;
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @ManyToOne(optional = false)
    private Users userID;
    @JoinColumn(name = "courseID", referencedColumnName = "courseID")
    @ManyToOne
    private Courses courseID;

    public Progressionplan() {
    }

    public Progressionplan(Integer planID) {
        this.planID = planID;
    }

    public Integer getPlanID() {
        return planID;
    }

    public void setPlanID(Integer planID) {
        this.planID = planID;
    }

    @XmlTransient
    public Collection<Progressionentry> getProgressionentryCollection() {
        return progressionentryCollection;
    }

    public void setProgressionentryCollection(Collection<Progressionentry> progressionentryCollection) {
        this.progressionentryCollection = progressionentryCollection;
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
        hash += (planID != null ? planID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Progressionplan)) {
            return false;
        }
        Progressionplan other = (Progressionplan) object;
        if ((this.planID == null && other.planID != null) || (this.planID != null && !this.planID.equals(other.planID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Progressionplan[ planID=" + planID + " ]";
    }
    
}
