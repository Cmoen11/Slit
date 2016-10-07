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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "learninggoals")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Learninggoals.findAll", query = "SELECT l FROM Learninggoals l"),
    @NamedQuery(name = "Learninggoals.findById", query = "SELECT l FROM Learninggoals l WHERE l.id = :id"),
    @NamedQuery(name = "Learninggoals.findByLearningGoal", query = "SELECT l FROM Learninggoals l WHERE l.learningGoal = :learningGoal")})
public class Learninggoals implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "learning_goal")
    private String learningGoal;
    @JoinColumn(name = "MODUL_ID", referencedColumnName = "ID")
    @ManyToOne
    private Modules modulId;

    public Learninggoals() {
    }

    public Learninggoals(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLearningGoal() {
        return learningGoal;
    }

    public void setLearningGoal(String learningGoal) {
        this.learningGoal = learningGoal;
    }

    public Modules getModulId() {
        return modulId;
    }

    public void setModulId(Modules modulId) {
        this.modulId = modulId;
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
        if (!(object instanceof Learninggoals)) {
            return false;
        }
        Learninggoals other = (Learninggoals) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Learninggoals[ id=" + id + " ]";
    }
    
}
