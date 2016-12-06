
package transferClasses;

import java.util.Date;

/**
 *
 * @author Christian
 */
public class InternalStudentComments {
    
    Date creationDate;

    public InternalStudentComments(Date creationDate, int teacherID, int studentID, int courseID, String comment) {
        this.creationDate = creationDate;
        this.teacherID = teacherID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.comment = comment;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    int teacherID, studentID, courseID;
    String comment;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
    
    
}
