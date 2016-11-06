

package modul;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dahl
 */
public class ModuleDetails implements Serializable{
    
    private int moduleID, courseID;
    private String name, description, moduleType;
    
    List<String> learningGoals;
    
    public ModuleDetails(int moduleID,
            int courseID,
            String name,
            String description,
            String moduleType) {
        
        this();
        
        this.moduleID = moduleID;
        this.courseID = courseID;
        this.name = name;
        this.description = description;
        this.moduleType = moduleType;
    }

    public ModuleDetails() {
        learningGoals = new ArrayList<>();
    }

    public List<String> getLearningGoals() {
        return learningGoals;
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
