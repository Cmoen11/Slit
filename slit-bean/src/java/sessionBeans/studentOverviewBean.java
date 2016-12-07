
package sessionBeans;

import auth.UserDetails;
import database.Users;
import java.util.ArrayList;
import java.util.List;
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
    
    
    // In progress****
    // Skal liste ut samtlige studenter i kurs
    //@Override
    //public Collection<CourseMembers> allStudentsList() {
    //    Users user = em.find(Users.class, 1);
    //    
    //    return user.getCourseMembersCollection();
    //}   
}
