package modul;

import database.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Christian and Erlend
 */
@Stateless
public class ModuleBean implements ModuleRemote {

    @PersistenceContext
    EntityManager em;

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
    
    @Override
    public ArrayList<ModuleDetails> getAllModules(int courseID) {
        List<Module> allModules = em.createNamedQuery("Module.findByCourseID")
                .setParameter("courseID", courseID)
                .getResultList();
        ArrayList<ModuleDetails> moduleList = new ArrayList<>(); 
        for (Module i : allModules) {
            moduleList.add(moduleToModuleDetails(i));
        }
        
        return moduleList;
    }
    
    // Saves changes done to the chosen module
    @Override
    public void saveModule(ModuleDetails module, ArrayList<String> learningGoals) {
        Module saveNewModule = new Module();
        saveNewModule.setModuleID(Integer.SIZE);
        saveNewModule.setName(module.getName());
        saveNewModule.setDescription(module.getDescription());
        saveNewModule.setCourseID(module.getCourseID());
        for (String i : learningGoals) {
            Learninggoals test = new Learninggoals();
            test.setDesc(i);
            test.setId(Integer.SIZE);
            if(saveNewModule.getLearninggoalsCollection() == null) {
                saveNewModule.setLearninggoalsCollection(new ArrayList<Learninggoals>());
            }
                saveNewModule.getLearninggoalsCollection().add(test);
        }
        em.persist(saveNewModule);
    }

    // Removes the current highlighted module
    @Override
    public void removeModule(ModuleDetails module) {
        em.remove(em.find(Module.class, module.getModuleID()));
    }

}
