/**
 * @author martinvenaas
 */

package slit.student;

import auth.UserDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleDetails;
import modul.ModuleRemote;
import progressionPlan.ProgressionEntry;
import progressionPlan.ProgressionPlanBeanRemote;
 


public class ProgressionBarController {
    
    // UI elements progressionBar
    @FXML private Button createProgressionPlanButton;
    
    // UI Elements
    @FXML private AnchorPane progressionBarPane;
    @FXML private Text progressionBarDate;
    @FXML private Circle progressionStatusColor;
    
    // UI elements makeProgressionPlan
    @FXML private AnchorPane planPane;
    @FXML private Text userNameHeading; 
    @FXML private DatePicker planEntryDatePicker;
    @FXML private ListView<Label> listOfEntries;
    @FXML private Button closePlan;
    @FXML private Button savePlan; 
    
    // Initialising and abstracting
    private ProgressionPlanBeanRemote planBean = lookupProgressionPlanBeanRemote();
    private UserDetails userId = Controller.getUser();
    private ArrayList<ModuleDetails> modules;
    private UserDetails user = Controller.getUser();
    
    // Utility functions
    private void initialize() {
        modules = lookupModuleBeanRemote().getAllModules(user.getCourseID());
        listOfEntries.getItems().clear();
        
        for (ModuleDetails m : modules) {
            Label l = new Label(m.getName());
            listOfEntries.getItems().add(l);
        }
    }
    
    private boolean doesPlanExist() {
        if (planBean.getAllProgressionEntriesByUser(user).equals(0)) {
            return false;
        } else {
            return true;
        }
        
    }
    
    // Controller Functions
    private void checkStateOfProgressionPlan() {
        if(doesPlanExist()) {
            createProgressionPlanButton.setVisible(false);
            progressionBarPane.setVisible(true);
        } else {
            createProgressionPlanButton.setVisible(true);
            progressionBarPane.setVisible(false);
        }
    }
    
    private void makeProgressionPlan() {
        if(!doesPlanExist()) {
            planBean.addProgressionPlan(user.getId(), user.getCourseID());
        }
   
    }
    
    public void openMakeProgressionPlanWindow() {
        planPane.setVisible(true);
        initialize();
    }
    
    public void closeMakeProgressionPlanWindow() {
        planPane.setVisible(true);
    }
    
    // Lookups
    private ProgressionPlanBeanRemote lookupProgressionPlanBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ProgressionPlanBeanRemote) c.lookup("java:global/slit-bean/ProgressionPlanBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ModuleRemote lookupModuleBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ModuleRemote) c.lookup("java:global/slit-bean/ModuleBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
