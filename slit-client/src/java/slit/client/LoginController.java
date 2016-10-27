package slit.client;

import course.CourseInfo;
import slit.Teacher.TeacherMain;
import auth.LoginAuthRemote;
import auth.UserDetails;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import modul.ModulRemote;
import slit.administrator.MainAdmin;
import user_details.UserBeanRemote;
import account.Authorisation;
/**
 *
 * @author Christian
 */
public class LoginController {

    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    ComboBox courses_combo;
    ArrayList<CourseInfo> courses;
    ArrayList<String> courseNames;

    /**
     * if login button is pressed.
     */
    public void loginButtonClicked() {
        if (lookupLoginAuth_beanRemote().authAdminAccount(username.getText(), password.getText())) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Admin");
            alert.setHeaderText("Administrator");
            alert.setContentText("Vi ser at du er en administrator, ønsker du å logge inn på administrasjonspanelet?");
            Optional<ButtonType> result = alert.showAndWait();
            Authorisation.setUserData(lookupUserBeanRemote().getUserByUsername(username.getText()));
            if (result.get() == ButtonType.OK) {
                new MainAdmin().runGUI(Main.primaryStage);
            } else {
                normalLogin();
            }
        } else {
            Authorisation.setUserData(lookupUserBeanRemote().getUserByUsername(username.getText()));
            normalLogin();
        }
    }

    private void normalLogin() {
        UserDetails user = null;
        try {
            CourseInfo selectedCourse = courses.get(courses_combo.getSelectionModel().getSelectedIndex());
            user = lookupLoginAuth_beanRemote().authUser(username.getText(), password.getText(), selectedCourse.getCourseID());
        } catch (Exception e) {
            user = null;
        }

        if (user != null) {
            // if loginbutton is pressed & username and password is correct<
            if (user.getEmail().equals("@@")) {
                firstTimeLoggedIn(user);
            } else if (user.isTeacher()) {
                new TeacherMain().runGUI(Main.primaryStage, username.getText());     // launch teacher panel
                System.out.println(user.getUsername() + " " + user.getCourseID() + " " + user.isTeacher());
            } else {
                // call student gui.
                System.out.println("Logged in as Student");
                System.out.println(user.getUsername() + " " + user.getCourseID() + " " + user.isTeacher());
            }

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Brukerfeil");
            alert.setContentText("Du har enten problemer med brukernavn, passord eller kurs. \n Ta kontakt med en Administrator ved feil");
            alert.setHeaderText("Innlogging feilet");
            alert.showAndWait();
        }
    }

    private void firstTimeLoggedIn(UserDetails user) {
        FirstTimeLoggedIn obj = new FirstTimeLoggedIn();
        obj.runGUI(Main.primaryStage, user);
    }

    public void adminloginButtonClicked() {
        if (!lookupLoginAuth_beanRemote().authAdminAccount(username.getText(), password.getText())) {
            System.out.println("Brukernavn og passord er feil, eller bruker er ikke admin.");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Brukerfeil");
            alert.setContentText("Brukernavn og passord er feil, eller bruker er ikke en Administrator.");
            alert.setHeaderText("Innlogging feilet");
            alert.showAndWait();
        } else {
            new MainAdmin().runGUI(Main.primaryStage);
        }
    }

    public void initialize() {
        courses = lookupLoginAuth_beanRemote().getCourses();
        courseNames = new ArrayList<>();
        for (CourseInfo course : courses) {
            courseNames.add(course.getCourseName());
        }
        courses_combo.setItems(FXCollections.observableArrayList(courseNames));
        courses_combo.getSelectionModel().select(0);

    }

    public void click() {
    }

    
    
    
    public static LoginAuthRemote lookupLoginAuth_beanRemote() {
        try {
            Context c = new InitialContext();
            return (LoginAuthRemote) c.lookup("java:comp/env/LoginAuth_bean");
        } catch (NamingException ne) {
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

    private ModulRemote lookupModul_beanRemote() {
        try {
            Context c = new InitialContext();
            return (ModulRemote) c.lookup("java:comp/env/Modul_bean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
