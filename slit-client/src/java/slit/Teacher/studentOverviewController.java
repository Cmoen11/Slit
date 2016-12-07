
package slit.Teacher;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import sessionBeans.studentOverviewRemote;
import user_details.UserBeanRemote;

/**
 *
 * @author TorOle
 */
public class studentOverviewController {
    
    studentOverviewRemote overviewBean = lookupstudentOverviewBeanRemote();
    UserBeanRemote userOverveiw = lookupUserBeanRemote();
    
    
    public void pressMe() {
        System.out.println(overviewBean.clickMe());
    }
    
    // Knappen lister ut samtlige studenter. Foreløpig blir de listet ut i konsollen, todo - list dem i 
    // listen på siden
    public void listAllStudents(){
        System.out.println(userOverveiw.getAllUsers());
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

    private UserBeanRemote lookupUserBeanRemote() {
        try {
            Context c = new InitialContext();
            return (UserBeanRemote) c.lookup("java:comp/env/UserBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
}
