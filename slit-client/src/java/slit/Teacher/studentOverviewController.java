
package slit.Teacher;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import sessionBeans.studentOverviewRemote;

/**
 *
 * @author TorOle
 */
public class studentOverviewController {
    
    studentOverviewRemote overviewBean = lookupstudentOverviewBeanRemote();
    
    public void pressMe() {
        System.out.println(overviewBean.clickMe());
    }

    private studentOverviewRemote lookupstudentOverviewBeanRemote() {
        try {
            Context c = new InitialContext();
            return (studentOverviewRemote) c.lookup("java:comp/env/studentOverviewBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
}
