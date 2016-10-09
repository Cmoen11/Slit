/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth;

import java.io.Serializable;

/**
 *
 * @author Christian
 */
public class CourseInfo implements Serializable{
    int courseID, startDate, endDate;
    String coruseName;

    public CourseInfo(int courseID, int startDate, int endDate, String coruseName) {
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

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public String getCoruseName() {
        return coruseName;
    }

    public void setCoruseName(String coruseName) {
        this.coruseName = coruseName;
    }
    
    
}
