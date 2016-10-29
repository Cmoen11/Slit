package slit.Teacher;

import auth.UserDetails;
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
import user_details.UserBeanRemote;

/**
 * Controllerclass for TeacherAdmModuleAndHelp.fxml
 *
 * @author Christian
 */
public class ControllerAdm {

    @FXML
    private JFXListView<Label> unassignedModules;

    @FXML
    private JFXListView<Label> unassignedHelp;

    public void initialize() {
        try {
            ArrayList<ModuleSubmissionDetails> moduleSubs
                    = (ArrayList<ModuleSubmissionDetails>) lookupSubmissionBeanRemote().getSubmissions(Controller.user.getCourseID()); // need to implement course.

            ArrayList<UserDetails> allUsers = lookupUserBeanRemote().getAllUsers();

            for (ModuleSubmissionDetails subs : moduleSubs) {
                UserDetails user = null;
                for (UserDetails obj : allUsers) {
                    if (obj.getId() == subs.getUserID()) {
                        user = obj;
                        break;
                    }
                }
                if (user != null) {
                    Label lbl = new Label(
                            subs.getSubmissionID() + ": "
                            + user.getFirstname() + " "
                            + user.getLastname()
                            + " " + subs.getCreationDate().getDate()
                            + "/" + subs.getCreationDate().getMonth());
                    unassignedModules.getItems().add(lbl);
                }
            }
        } catch (Exception e) {

        }

        for (int i = 0; i < 10; i++) {
            try {
                //Label lbl = new Label("Module " + i);
                Label lbl2 = new Label("Help " + i);
                //unassignedModules.getItems().add(lbl);
                unassignedHelp.getItems().add(lbl2);
            } catch (Exception e) {

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
