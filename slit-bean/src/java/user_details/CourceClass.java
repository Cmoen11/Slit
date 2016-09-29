/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_details;

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
public class CourceClass implements Serializable {

    @Id
    private Long courceClassID;

    private static final long serialVersionUID = 1L;
    
    private Integer userID;
    private Long courceID;
    private int isTeacher; // can be either 1 og 0, 1 for teacher, 0 for student

    public CourceClass() {
        
    }
    
    public CourceClass(int userID, long courceID, boolean isTeacher) {
        this.userID = userID;
        this.courceID = courceID;
        if (isTeacher) this.isTeacher = 1; else this.isTeacher = 0;
    }
 




    public Long getCourceClassID() {
        return courceClassID;
    }

    public void setCourceClassID(Long courceClassID) {
        this.courceClassID = courceClassID;
    }
    
}
