/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import java.io.Serializable;

/**
 *
 * @author Christian
 */
public class CourseInfo implements Serializable{
    int courseID;
    String startDate, endDate;
    String coruseName;

    public CourseInfo(int courseID, String startDate, String endDate, String coruseName) {
        this.courseID = courseID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coruseName = coruseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCoruseName() {
        return coruseName;
    }

    public void setCoruseName(String coruseName) {
        this.coruseName = coruseName;
    }
    
    
}
