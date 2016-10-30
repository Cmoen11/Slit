/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface ModuleRemote {

    int createModule(String name, String desc);

    void addLearningGoal(String learningGoal, int id);

    List<ModuleDetails> getAllModulesForUser(int userId);

    void newModule();

    void saveModule();

    void openModule();

    void removeModule();

    void addLearningGoal();

    void removeLearningGoal();

}
