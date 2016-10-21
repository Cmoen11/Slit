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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Christian
 */
@Entity
@Table(name = "progressionentry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Progressionentry.findAll", query = "SELECT p FROM Progressionentry p"),
    @NamedQuery(name = "Progressionentry.findById", query = "SELECT p FROM Progressionentry p WHERE p.id = :id"),
    @NamedQuery(name = "Progressionentry.findByCompletionDate", query = "SELECT p FROM Progressionentry p WHERE p.completionDate = :completionDate")})
public class Progressionentry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "completionDate")
    @Temporal(TemporalType.DATE)
    private Date completionDate;
    @JoinColumn(name = "planID", referencedColumnName = "planID")
    @ManyToOne(optional = false)
    private Progressionplan planID;
    @JoinColumn(name = "moduleID", referencedColumnName = "moduleID")
    @ManyToOne(optional = false)
    private Module moduleID;

    public Progressionentry() {
    }

    public Progressionentry(Integer id) {
        this.id = id;
    }

    public Progressionentry(Integer id, Date completionDate) {
        this.id = id;
        this.completionDate = completionDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Progressionplan getPlanID() {
        return planID;
    }

    public void setPlanID(Progressionplan planID) {
        this.planID = planID;
    }

    public Module getModuleID() {
        return moduleID;
    }

    public void setModuleID(Module moduleID) {
        this.moduleID = moduleID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Progressionentry)) {
            return false;
        }
        Progressionentry other = (Progressionentry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Progressionentry[ id=" + id + " ]";
    }
    
}
