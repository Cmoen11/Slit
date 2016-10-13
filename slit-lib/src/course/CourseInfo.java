/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author Christian
 */
public class CourseInfo implements Serializable{
    int courseID;
    Calendar startDate, endDate;
    String courseName, courseCode;

    public CourseInfo(int courseID, Calendar startDate, Calendar endDate, String courseName, String courseCode) {
        this.courseID = courseID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String toString() {
        return courseCode+": "+courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    
}
