package modul;

import database.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Christian
 */
@Stateless
public class ModuleBean implements ModuleRemote {

    @PersistenceContext
    EntityManager em;

    /**
     * add learning goals to the module.
     *
     * @param learningGoal
     * @param id
     */
    @Override
    public void addLearningGoal(String learningGoal, int id) {
        Learninggoals goal = new Learninggoals();
        goal.setModuleID(em.find(Module.class, (long) id));

        em.persist(goal);
    }

    @Override
    public List<ModuleDetails> getAllModulesForUser(int userId) {
        List<ModuleDetails> returnList = new ArrayList<ModuleDetails>();

        Query query = em.createNamedQuery("Modulesubmission.findByUser",
                Modulesubmission.class);

        query.setParameter("userId", userId);

        List<Modulesubmission> resultList = query.getResultList();

        for (Modulesubmission submission : resultList) {
            returnList.add(null);//convert subbission submission);

        }
        return returnList;
    }

    /**
     * transfer a Module object to ModuleDetails object
     * @param module
     * @return 
     */
    private ModuleDetails moduleToModuleDetails(Module module) {
        ModuleDetails moduleDetails = new ModuleDetails();
        moduleDetails.setCourseID(module.getCourseID());
        moduleDetails.setDescription(module.getDescription());
        moduleDetails.setModuleID(module.getModuleID());
        moduleDetails.setModuleType(module.getModulType());
        moduleDetails.setName(module.getName());

        return moduleDetails;
    }
    
    /**
     * Get module data by moduleID. 
     * @param moduleID
     * @return 
     */
    @Override
    public ModuleDetails getModuleByID(int moduleID) {
        Module module = (Module) 
                em.createNamedQuery("Module.findByModuleID")
                        .setParameter("moduleID", moduleID)
                        .getSingleResult();
        return moduleToModuleDetails(module);
        
    }
    
    
    // Creates a new empty module
    @Override
    public void newModule() {
        
    }
    // Saves changes done to the chosen module
    @Override
    public void saveModule(ModuleDetails module, ArrayList<String> learningGoals) {
        Module saveNewModule = new Module();
        saveNewModule.setName(module.getName());
        saveNewModule.setDescription(module.getDescription());
        saveNewModule.setCourseID(module.getCourseID());
        em.persist(saveNewModule);
        for (String i : learningGoals) {
                Learninggoals temp = new Learninggoals();
                temp.setDesc(i);
                temp.setModuleID(saveNewModule);
                em.persist(temp);
        }
    }

    // Opens the current highlighted module
    @Override
    public void openSelectedModule() {

    }

    // Removes the current highlighted module
    @Override
    public void removeModule(ModuleDetails module) {
        em.remove(em.find(Module.class, module.getModuleID()));
    }

    // Adds the content of the textfield to the learning goals
    @Override
    public void addLearningGoal() {

    }

    // Removes the current highlighted learninggoals
    @Override
    public void removeLearningGoal() {

    }

}
