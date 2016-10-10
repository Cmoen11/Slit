
package slit.client;

import course.CourseInfo;
import slit.Teacher.TeacherMain;
import auth.LoginAuthRemote;
import auth.UserDetails;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import slit.administrator.MainAdmin;

/**
 *
 * @author Christian
 */
public class Controller {
    @FXML TextField username;
    @FXML TextField password;
    @FXML ComboBox courses_combo;
    ArrayList<CourseInfo> courses;
    ArrayList<String> courseNames;
    /**
     * if login button is pressed. 
     */
    public void loginButtonClicked() {
        UserDetails user = null;
        try{
            CourseInfo selectedCourse = courses.get(courses_combo.getSelectionModel().getSelectedIndex());
            user = lookupLoginAuth_beanRemote().authUser(username.getText(), password.getText(), selectedCourse.getCourseID());
        }catch(Exception e) {
            user = null;
        }
        
        if(user != null) {
            // if loginbutton is pressed & username and password is correct<
            System.out.println("Logged in as " + username.getText());
            if (user.isTeacher()) {
                new TeacherMain().runGUI(Main.primaryStage, username.getText());     // launch student panel
                System.out.println(user.getUsername() +" "+ user.getCourseID() +" "+ user.isTeacher());
            }
                
            else {
                // call student gui.
                System.out.println("Logged in as Student");
                System.out.println(user.getUsername() +" "+ user.getCourseID() +" "+ user.isTeacher());
            }
                
        }
            
        else {
            System.out.println("nah..");
        }
        
    }
    
    
    public void adminloginButtonClicked() {
        if (!lookupLoginAuth_beanRemote().authAdminAccount(username.getText(), password.getText())) {
            System.out.println("Brukernavn og passord er feil, eller bruker er ikke admin.");
        } else {
            new MainAdmin().runGUI(Main.primaryStage);
        }
    }
    
    public void initialize() {
        courses = lookupLoginAuth_beanRemote().getCourses();
        courseNames = new ArrayList<>();
        for (CourseInfo course : courses)
            courseNames.add(course.getCourseName());
        courses_combo.setItems(FXCollections.observableArrayList(courseNames));
        
    }
    
    public void click()  {
        System.out.println("Hey");
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
