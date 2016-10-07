/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "modules")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modules.findAll", query = "SELECT m FROM Modules m"),
    @NamedQuery(name = "Modules.findById", query = "SELECT m FROM Modules m WHERE m.id = :id"),
    @NamedQuery(name = "Modules.findByModuleDesc", query = "SELECT m FROM Modules m WHERE m.moduleDesc = :moduleDesc"),
    @NamedQuery(name = "Modules.findByModuleName", query = "SELECT m FROM Modules m WHERE m.moduleName = :moduleName")})
public class Modules implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "module_desc")
    private String moduleDesc;
    @Size(max = 255)
    @Column(name = "module_name")
    private String moduleName;
    @OneToMany(mappedBy = "modulId")
    private List<Learninggoals> learninggoalsList;

    public Modules() {
    }
    


    public Modules(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @XmlTransient
    public List<Learninggoals> getLearninggoalsList() {
        return learninggoalsList;
    }

    public void setLearninggoalsList(List<Learninggoals> learninggoalsList) {
        this.learninggoalsList = learninggoalsList;
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
        if (!(object instanceof Modules)) {
            return false;
        }
        Modules other = (Modules) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Modules[ id=" + id + " ]";
    }
    
}
