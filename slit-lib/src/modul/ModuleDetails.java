/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import java.io.Serializable;

/**
 *
 * @author Dahl
 */
public class ModuleDetails implements Serializable{
    
    private int moduleID, courseID;
    private String name, description, moduleType;

    public ModuleDetails(int moduleID, int courseID, String name, String description, String moduleType) {
        this.moduleID = moduleID;
        this.courseID = courseID;
        this.name = name;
        this.description = description;
        this.moduleType = moduleType;
    }

    public ModuleDetails() {
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }
    
    
    
    
}
