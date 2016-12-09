
package sessionBeans;

import database.Users;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author TorOle
 */
@Stateless
public class studentOverviewBean implements studentOverviewRemote {
    
    @PersistenceContext()
    EntityManager em;
    
    
    
    // Overkjører metoden clickMe i studentOverveiwRemote klassen
    // med samme navn. Har i oppgave å finne student hvor primærnøkkel
    // er 1. Når denne / hvis denne finnes, returner brukernavn. 
    @Override
    public String clickMe() {
        Users user = em.find(Users.class, 1);
        
        return user.getUsername();
    }

}
