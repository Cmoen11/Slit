package slit.client;

import course.CourseInfo;
import slit.Teacher.TeacherMain;
import auth.LoginAuthRemote;
import auth.UserDetails;
import com.jfoenix.controls.JFXCheckBox;
import java.util.ArrayList;
import java.util.Optional;
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
    @FXML ComboBox courses_combo;
    @FXML JFXCheckBox rememberMe;
    ArrayList<CourseInfo> courses;
    ArrayList<String> courseNames;
    
    Preferences pref;

    
    /**
     * if login button is pressed.
     */
    public void loginButtonClicked() {
        
        if (rememberMe.isSelected())
            saveUsernameAndPassword(username.getText(), password.getText());
        else pref.putBoolean("rememberMe", false);
        
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
        
        try {
            // get selecteted course
            CourseInfo selectedCourse = courses.get(
                    courses_combo.getSelectionModel().getSelectedIndex());
            
            // lookup user and retrive a userObject if username and password is
            // correct, and null if not.
            user = lookupLoginAuth_beanRemote().authUser(
                    username.getText(), password.getText(),
                    selectedCourse.getCourseID());
            
        } catch (Exception e) {
            user = null; // if an error occurred, return null.
        }
        
        // now check if the sign in were succsessful. 
        if (user != null) {
            // username & password is correct, and user is in selected course.
            
            // set the Authorisation user
            account.Authorisation.setUserData(user);
            
            if (user.getEmail().equals("@@")) {
                firstTimeLoggedIn(user);
                
            }else if (user.isTeacher()) {
                // the user is a teacher of the selected course.
                new TeacherMain().runGUI(Main.primaryStage, user);
            } else {
                //user is a student of the selected course.
                //!! TODO, add the student GUI method call here.
                new MainStudent().runGUI(Main.primaryStage, user);
            }

        } else {
            
            // username, password or course do not match the selected user.
            
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
        pref = Preferences.userNodeForPackage(Controller.class);
        
        if (pref.getBoolean("rememberMe", false)) {
            rememberMe.setSelected(true);
            username.setText(pref.get("username", ""));
            password.setText(pref.get("password", ""));
        }
        
        // get all course s.
        courses = lookupLoginAuth_beanRemote().getCourses();
        courseNames = new ArrayList<>();
        
        // add all course names into an arrayList to display in ComboBox
        courses.stream().forEach((course) -> {
            courseNames.add(course.getCourseName());
        });
        
        // set courseNames into the ComboBox.
        courses_combo.setItems(FXCollections.observableArrayList(courseNames));
        // select the first option.
        courses_combo.getSelectionModel().select(0);

    }

    /**
     * The bean call.
     * @return an object to call bean methods from.
     */
    public static LoginAuthRemote lookupLoginAuth_beanRemote() {
        try {
            Context c = new InitialContext();
            return (LoginAuthRemote) c.lookup("java:comp/env/LoginAuth_bean");
        } catch (NamingException ne) {
            throw new RuntimeException(ne);
        }
    }
    

}
