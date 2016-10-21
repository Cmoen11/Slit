/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "file")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "File.findAll", query = "SELECT f FROM File f"),
    @NamedQuery(name = "File.findByFileID", query = "SELECT f FROM File f WHERE f.fileID = :fileID"),
    @NamedQuery(name = "File.findByFilename", query = "SELECT f FROM File f WHERE f.filename = :filename"),
    @NamedQuery(name = "File.findByUserID", query = "SELECT f FROM File f WHERE f.userID = :userID"),
    @NamedQuery(name = "File.findByFileType", query = "SELECT f FROM File f WHERE f.fileType = :fileType")})
public class File implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "fileID")
    private Integer fileID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 0)
    @Column(name = "filename")
    private String filename;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "fileContent")
    private String fileContent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userID")
    private int userID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "fileType")
    private String fileType;
    @OneToMany(mappedBy = "fileID")
    private Collection<Modulesubmission> modulesubmissionCollection;

    public File() {
    }

    public File(Integer fileID) {
        this.fileID = fileID;
    }

    public File(Integer fileID, String filename, String fileContent, int userID, String fileType) {
        this.fileID = fileID;
        this.filename = filename;
        this.fileContent = fileContent;
        this.userID = userID;
        this.fileType = fileType;
    }

    public Integer getFileID() {
        return fileID;
    }

    public void setFileID(Integer fileID) {
        this.fileID = fileID;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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
        hash += (fileID != null ? fileID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof File)) {
            return false;
        }
        File other = (File) object;
        if ((this.fileID == null && other.fileID != null) || (this.fileID != null && !this.fileID.equals(other.fileID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.File[ fileID=" + fileID + " ]";
    }
    
}
