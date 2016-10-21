
package slit.client;

import auth.LoginAuthRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import slit.Teacher.TeacherMain;
import user_details.UserBeanRemote;

/**
 *
 * @author Christian
 */
public class ControllerFirstTime {
    @FXML TextField epost;
    @FXML PasswordField password1;
    @FXML PasswordField password2;
    
    public void saveButton() {
        if (checkPassword()) {
            FirstTimeLoggedIn.obj.setEmail(epost.getText());
            lookupUserBeanRemote().editUser(FirstTimeLoggedIn.obj, password1.getText());
            String[] args = new String[0]; // Or String[] args = {};
            if (FirstTimeLoggedIn.obj.isTeacher())
                new TeacherMain().runGUI(Main.primaryStage, FirstTimeLoggedIn.obj.getUsername());
            else {
                //student log on
            }
        }
    }
    
    private boolean checkPassword() {
        if (password1.getText().equals(password2.getText()) && password1.getText().length() >= 6) {
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Brukerfeil");
            alert.setContentText("Passord er ikke like, eller passord for kort(minst 6 tegn)");
            alert.setHeaderText("Prøv på nytt");
            alert.showAndWait();
            return false;
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

    private LoginAuthRemote lookupLoginAuth_beanRemote() {
        try {
            Context c = new InitialContext();
            return (LoginAuthRemote) c.lookup("java:comp/env/LoginAuth_bean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
