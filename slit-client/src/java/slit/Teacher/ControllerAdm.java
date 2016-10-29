
package slit.Teacher;
import com.jfoenix.controls.JFXListView;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleSubmissionDetails;
import modul.SubmissionBeanRemote;

/**
 * Controllerclass for TeacherAdmModuleAndHelp.fxml
 * @author Christian
 */
public class ControllerAdm {
    @FXML
    private JFXListView<Label> unassignedModules;
    
    @FXML
    private JFXListView<Label> unassignedHelp;
    
    public void initialize() {
        
        ArrayList<ModuleSubmissionDetails> moduleSubs = 
                (ArrayList<ModuleSubmissionDetails>) 
                lookupSubmissionBeanRemote().getSubmissions();
        
        for (ModuleSubmissionDetails subs : moduleSubs) {
            Label lbl = new Label("Test " + subs.getCreationDate().getDate());
            unassignedModules.getItems().add(lbl);
        }
        
        
        for (int i=0; i < 10; i++) {
            try {
                //Label lbl = new Label("Module " + i);
                Label lbl2 = new Label("Help " + i);
                //unassignedModules.getItems().add(lbl);
                unassignedHelp.getItems().add(lbl2);
            }catch(Exception e) {
                
            }
        }
        unassignedModules.setExpanded(true);
        unassignedHelp.setExpanded(false);
    }

    private SubmissionBeanRemote lookupSubmissionBeanRemote() {
        try {
            Context c = new InitialContext();
            return (SubmissionBeanRemote) c.lookup("java:comp/env/SubmissionBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
}
