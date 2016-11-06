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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Christian
 */
@Entity
@Table(name = "module")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m"),
    @NamedQuery(name = "Module.findByModuleID", query = "SELECT m FROM Module m WHERE m.moduleID = :moduleID"),
    @NamedQuery(name = "Module.findByName", query = "SELECT m FROM Module m WHERE m.name = :name"),
    @NamedQuery(name = "Module.findByDescription", query = "SELECT m FROM Module m WHERE m.description = :description"),
    @NamedQuery(name = "Module.findByModulType", query = "SELECT m FROM Module m WHERE m.modulType = :modulType"),
    @NamedQuery(name = "Module.findByCourseID", query = "SELECT m FROM Module m WHERE m.courseID = :courseID")})
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "moduleID")
    private Integer moduleID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "modul_type")
    private String modulType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "courseID")
    private int courseID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moduleID")
    private Collection<Learninggoals> learninggoalsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moduleID")
    private Collection<Progressionentry> progressionentryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "moduleID")
    private Collection<Modulesubmission> modulesubmissionCollection;

    public Module() {
    }

    public Module(Integer moduleID) {
        this.moduleID = moduleID;
    }

    public Module(Integer moduleID, String name, String description, 
            String modulType, int courseID) {
        this.moduleID = moduleID;
        this.name = name;
        this.description = description;
        this.modulType = modulType;
        this.courseID = courseID;
    }

    public Integer getModuleID() {
        return moduleID;
    }

    public void setModuleID(Integer moduleID) {
        this.moduleID = moduleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModulType() {
        return modulType;
    }

    public void setModulType(String modulType) {
        this.modulType = modulType;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @XmlTransient
    public Collection<Learninggoals> getLearninggoalsCollection() {
        return learninggoalsCollection;
    }

    public void setLearninggoalsCollection(Collection<Learninggoals> learninggoalsCollection) {
        this.learninggoalsCollection = learninggoalsCollection;
    }

    @XmlTransient
    public Collection<Progressionentry> getProgressionentryCollection() {
        return progressionentryCollection;
    }

    public void setProgressionentryCollection(Collection<Progressionentry> progressionentryCollection) {
        this.progressionentryCollection = progressionentryCollection;
    }

    @XmlTransient
    public Collection<Modulesubmission> getModulesubmissionCollection() {
        return modulesubmissionCollection;
    }

    public void setModulesubmissionCollection(Collection<Modulesubmission> modulesubmissionCollection) {
        this.modulesubmissionCollection = modulesubmissionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moduleID != null ? moduleID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.moduleID == null && other.moduleID != null) || (this.moduleID != null && !this.moduleID.equals(other.moduleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Module[ moduleID=" + moduleID + " ]";
    }
    
}
