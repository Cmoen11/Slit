/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import database.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Christian
 */
@Stateless
public class Modul_bean implements ModulRemote {
    @PersistenceContext EntityManager em;

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
    public void asd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
