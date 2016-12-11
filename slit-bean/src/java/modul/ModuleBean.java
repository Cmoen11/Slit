package modul;

import database.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author Christian and Erlend
 */
@Stateless
public class ModuleBean implements ModuleRemote {

    @PersistenceContext()
    EntityManager em;

    /**
     *
     * @param userId
     * @return
     */
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
     *
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
        for (Learninggoals i : module.getLearninggoalsCollection()) {
            moduleDetails.getLearningGoals().add(i.getDesc());
        }

        return moduleDetails;
    }

    /**
     * Get module data by moduleID.
     *
     * @param moduleID
     * @return
     */
    @Override
    public ModuleDetails getModuleByID(int moduleID) {
        Module module = (Module) em.createNamedQuery("Module.findByModuleID")
                .setParameter("moduleID", moduleID)
                .getSingleResult();
        return moduleToModuleDetails(module);
    }

    /**
     * Makes a query to the database and returns
     *
     * @param courseID
     * @return
     */
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
        saveNewModule.setModulType(module.getModuleType()); 

        /*ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Module>> constraintViolations = validator.validate(saveNewModule);

        if (constraintViolations.size() > 0) {
            System.out.println("Constraint Violations occurred..");
            for (ConstraintViolation<Module> contraints : constraintViolations) {
                System.out.println(contraints.getRootBeanClass().getSimpleName()
                        + "." + contraints.getPropertyPath() + " " + contraints.getMessage());
            }
        }*/
        em.persist(saveNewModule);
        for (String i : learningGoals) {
            Learninggoals learninggoal = new Learninggoals();
            learninggoal.setDesc(i);
            learninggoal.setId(Integer.SIZE);
            learninggoal.setModuleID(saveNewModule);
            if (saveNewModule.getLearninggoalsCollection() == null) {
                saveNewModule.setLearninggoalsCollection(new ArrayList<Learninggoals>());
            }
            saveNewModule.getLearninggoalsCollection().add(learninggoal);
            em.persist(learninggoal);
        }
    }

    /**
     * Removes the current highlighted module
     *
     * @param module
     */
    @Override
    public void removeModule(ModuleDetails module) {
        em.remove(em.find(Module.class, module.getModuleID()));
    }

}
