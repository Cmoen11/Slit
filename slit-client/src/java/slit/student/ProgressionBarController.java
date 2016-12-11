/**
 * @author martinvenaas
 */

package slit.student;

import auth.UserDetails;
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
import progressionPlan.ProgressionPlanBeanRemote;
 


public class ProgressionBarController {
    
    @FXML
    private ListView<Label> listOfEntries;
    
    @FXML
    private Text userNameHeading; 
    
    @FXML
    private Button savePlan; 
    
    @FXML
    private DatePicker planEntryDatePicker;
    
    @FXML
    private Button createProgressionPlanButton;
    
    @FXML
    private Text progressionBarDate;
    
    @FXML
    private Circle progressionStatusColor;
    
    @FXML
    private AnchorPane progressionBarPane; 
            
    ProgressionPlanBeanRemote planBean = lookupProgressionPlanBeanRemote();
    
    private UserDetails userId = Controller.getUser();
    
    
    //Utility functions
    private boolean doesPlanExist() {
        if (planBean.getAllProgressionEntriesByUser(Controller.getUser()).equals(0)) {
            return false;
        } else {
            return true;
        }
    }
    
    //Controller Functions
    public void progressionPlanButtonAction(ActionEvent event) throws Exception {               
        
        if (!doesPlanExist()) {
            createProgressionPlanButton.setVisible(true);
            progressionBarPane.setVisible(false);           
        } else {
            createProgressionPlanButton.setVisible(false);
            progressionBarPane.setVisible(true);
        }
        
    }
    
    private ProgressionPlanBeanRemote lookupProgressionPlanBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ProgressionPlanBeanRemote) c.lookup("java:global/slit/ProgressionPlanBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
        
    }
   
}
