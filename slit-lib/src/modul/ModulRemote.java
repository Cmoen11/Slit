/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import java.sql.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface ModulRemote {

    int createModule(String name, String desc);
    void addLearningGoal(String learningGoal, int id);
    
     List<ModuleDetails> getAllModulesForUser(int userId);
    
    public void asd();

    String testTrykk(String test);

    String editButton(String temp);

    void addModuleSubmission(int userID, int moduleID, Date creationDate, int status, String content, String type, int fileID);
}
