/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface ModuleRemote {

    void addLearningGoal(String learningGoal, int id);

    List<ModuleDetails> getAllModulesForUser(int userId);

    void saveModule(ModuleDetails module, ArrayList<String> learningGoals);
    
    ModuleDetails getModuleByID(int moduleID);
    
    void newModule();

    void openSelectedModule();

    void removeModule(ModuleDetails module);

    void addLearningGoal();

    void removeLearningGoal();

}
