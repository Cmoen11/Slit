package slit.client;

import course.CourseInfo;
import slit.Teacher.TeacherMain;
import auth.LoginAuthRemote;
import auth.UserDetails;
import com.jfoenix.controls.JFXCheckBox;
import course.CourseBeanRemote;
import java.util.ArrayList;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import slit.administrator.Controller;
import slit.administrator.MainAdmin;
import slit.student.MainStudent;
/**
 *
 * @author Christian
 */
public class LoginController {

    @FXML TextField username;
    @FXML TextField password;
    @FXML JFXCheckBox rememberMe;
    ArrayList<CourseInfo> courses;
    ArrayList<String> courseNames;


    Preferences pref;

    /**
     * if login button is pressed.
     */
    public void loginButtonClicked() {
        if (lookupLoginAuth_beanRemote()
                .authAdminAccount(username.getText(),
                        password.getText())) {
            
            // the user is an admin, check whenever the user wants to log into
            // the admin panel or not.
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Admin");
            alert.setHeaderText("Administrator");
            alert.setContentText("Vi ser at du er en administrator, "
                    + "ønsker du å logge inn på administrasjonspanelet?");
            Optional<ButtonType> result = alert.showAndWait();
            
            // handle the user input
            if (result.get() == ButtonType.OK) {
                // user selected yes. -> send user to admin panel
                new MainAdmin().runGUI(Main.primaryStage);
            } else {
                // user selected no. -> send user to normal Login
                normalLogin();
            }
        } else {
            // user is not an admin. send user to normal inlogging.
            normalLogin();
        }
    }

    private void normalLogin() {
        UserDetails user;  
        if (rememberMe.isSelected())
            saveUsernameAndPassword(username.getText(), password.getText());
        else pref.putBoolean("rememberMe", false);
        
             user = lookupLoginAuth_beanRemote().
                authUser(username.getText(), 
                password.getText());
        if (user != null) {
            try {
                
                new CourseSelectorController().runGUI(user);
                Main.primaryStage.close();
            }catch(Exception e) {
                System.out.println("Fant ikke GUI fil");
                e.printStackTrace();
            }
        } else { // username & passoword is wrong, or failed to connect to server
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Brukerfeil");
            alert.setContentText("Du har enten problemer med brukernavn, "
                    + "passord eller kurs. \n Ta kontakt "
                    + "med en Administrator ved feil");
            
            alert.setHeaderText("Innlogging feilet");
            alert.showAndWait();
        }


    }
    
    /**
     * First time user, needs to fill in some extra information.
     * @param user the userobject that is currently signed in.
     */
    private void firstTimeLoggedIn(UserDetails user) {
        FirstTimeLoggedIn obj = new FirstTimeLoggedIn();
        obj.runGUI(Main.primaryStage, user);
    }
    
    private void saveUsernameAndPassword(String username, String password) {
        pref.put("username", username);
        pref.put("password", password);
        pref.putBoolean("rememberMe", true);
    }

    /**
     * Initialize the GUI. 
     */
    public void initialize() {


        pref = Preferences.userNodeForPackage(LoginController.class);
        //username.setText(pref.get("username", "root"));

        // get all course s.
        courses = lookupLoginAuth_beanRemote().getCourses();
        courseNames = new ArrayList<>();

        
        if (pref.getBoolean("rememberMe", false)) {
            rememberMe.setSelected(true);
            username.setText(pref.get("username", ""));
            password.setText(pref.get("password", ""));
            
            courses = lookupCourseBeanRemote().getAllCourseUserIsMemberIn(username.getText());
            
        }
        else {
            courses = lookupLoginAuth_beanRemote().getCourses();
        }

    }
    
    public void sortOutCoursesByUser() {
        
        
    }
    
    
    /**
     * The bean call.
     * @return an object to call bean methods from.
     */
    public static LoginAuthRemote lookupLoginAuth_beanRemote() {
        try {
            Context c = new InitialContext();
            return (LoginAuthRemote) c.lookup("java:global/slit-bean/LoginAuth_bean");
        } catch (NamingException ne) {
            throw new RuntimeException(ne);
        }
    }
 
    private CourseBeanRemote lookupCourseBeanRemote() {
        try {
            Context c = new InitialContext();
            return (CourseBeanRemote) c.lookup("java:global/slit-bean/CourseBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    

}
