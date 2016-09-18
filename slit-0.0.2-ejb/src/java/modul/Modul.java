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
public class Modul implements ModulRemote {
    @PersistenceContext EntityManager em;

    @Override
    public boolean createModule(String name, String desc) {
        Modules modul = new Modules(name, desc);
        System.out.println("Object created!");
        em.getTransaction().begin();
        em.persist(modul);
        em.getTransaction().commit();
        System.out.println("added!");
        return false;
    }
    
    
    
}
