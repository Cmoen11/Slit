/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Christian
 */
public class CourseInfo implements Serializable{
    int courseID;
    Date startDate, endDate;
    String courseName, courseCode;

    public CourseInfo(int courseID, Date startDate, Date endDate, String courseName, String courseCode) {
        this.courseID = courseID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public CourseInfo() {
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String toString() {
        return courseCode+" | " + (startDate.getYear() + 1900) + ": "+courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    
}
