
package modul;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Christian
 */
@Entity
public class Modules implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String module_name;
    private String module_description;
    
    public Modules(){
        this(null,null);
    }
    
    public Modules(String name, String desc) {
        this.module_name = name;
        this.module_description = desc;
    }
    
    public int getId() {
        return id;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modules)) {
            return false;
        }
        Modules other = (Modules) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modul.Modules[ id=" + id + " ]";
    }
    
}
