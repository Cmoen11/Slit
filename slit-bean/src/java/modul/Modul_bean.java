/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

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
    public boolean createModule(String name, String desc) {
        Modules modul = new Modules(name, desc);
        em.persist(name);
        return false;
    }
    
    
    
}
