
package slit.Teacher;
import com.jfoenix.controls.JFXListView;
    import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
        for (int i=0; i < 10; i++) {
            try {
                Label lbl = new Label("Module " + i);
                Label lbl2 = new Label("Help " + i);
                unassignedModules.getItems().add(lbl);
                unassignedHelp.getItems().add(lbl2);
            }catch(Exception e) {
                
            }
        }
        unassignedModules.setExpanded(true);
        unassignedHelp.setExpanded(false);
    }
}
