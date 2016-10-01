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
 * associative entity
 * @author Christian
 */
@Entity
public class CourseClass implements Serializable {

    @Id
    private Long CourseClassID;

    private static final long serialVersionUID = 1L;
    
    private Integer userID;
    private Long courseID;
    private int isTeacher; // can be either 1 og 0, 1 for teacher, 0 for student

    public CourseClass() {
        
    }
    
    public CourseClass(int userID, long courseID, boolean isTeacher) {
        this.userID = userID;
        this.courseID = courseID;
        if (isTeacher) this.isTeacher = 1; else this.isTeacher = 0;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public int getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(int isTeacher) {
        this.isTeacher = isTeacher;
    }
 




    public Long getCourseClassID() {
        return CourseClassID;
    }

    public void setCourseClassID(Long CourseClassID) {
        this.CourseClassID = CourseClassID;
    }
    
}
