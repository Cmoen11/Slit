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

    @Override
    public int createModule(String name, String desc) {
        Module modul = new Module();
        modul.setName(name);
        modul.setDescription(desc);
        em.persist(modul);      // add modul to database.

        return em.find(Module.class, modul).getModuleID();
    }

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

    // Saves changes done to the chosen module
    @Override
    public void saveModule(ModuleDetails module) {
        Module saveNewModule = new Module();
        saveNewModule.setName(module.getName());
        saveNewModule.setDescription(module.getDescription());
        saveNewModule.setCourseID(module.getCourseID());
        
        // Lacks learninggoals
    }

    // Opens the current highlighted module
    @Override
    public void openModule() {

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
