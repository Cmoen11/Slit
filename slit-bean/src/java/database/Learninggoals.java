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
    @NamedQuery(name = "Learninggoals.findByDesc", query = "SELECT l FROM Learninggoals l WHERE l.desc = :desc")})
public class Learninggoals implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String desc;
    @JoinColumn(name = "moduleID", referencedColumnName = "moduleID")
    @ManyToOne(optional = false)
    private Module moduleID;

    public Learninggoals() {
    }

    public Learninggoals(Integer id) {
        this.id = id;
    }

    public Learninggoals(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
