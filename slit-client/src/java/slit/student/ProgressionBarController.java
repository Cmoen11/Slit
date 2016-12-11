/**
 * @author martinvenaas
 */

package slit.student;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import progressionPlan.ProgressionPlanBeanRemote;
 


public class ProgressionBarController {

    
    private int existingPlanCheck() {
        
    }
    
    private ProgressionPlanBeanRemote lookupProgressionPlanBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ProgressionPlanBeanRemote) c.lookup("java:comp/env/ProgressionPlanBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
        
    }
   
}
