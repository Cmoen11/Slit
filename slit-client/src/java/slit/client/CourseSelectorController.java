
package slit.client;

import auth.LoginAuthRemote;
import auth.UserDetails;
import com.jfoenix.controls.JFXListView;
import course.CourseBeanRemote;
import course.CourseInfo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import slit.Teacher.TeacherMain;
import slit.student.MainStudent;

/**
 * FXML Controller class
 *
 * @author Christian
 */
public class CourseSelectorController implements Initializable {
    
    @FXML JFXListView<Label> courseList;
    @FXML Accordion accordion;
    @FXML TitledPane courseOverview;
    @FXML Text welcomeText;
    @FXML Button adminButton;
    
    static UserDetails user;
    ArrayList<CourseInfo> courses;
    boolean isAdmin;
    Stage primaryStage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        adminButton.setVisible(
                lookupLoginAuth_beanRemote().isAdmin(user.getId()));
        
        // get all courses the user is member off
        courses = new ArrayList<>();
        courses.addAll(lookupCourseBeanRemote()
                .getAllCourseUserIsMemberIn(user.getUsername()));
        
        // add members to the courseInfo
        for (CourseInfo course : courses) {
            Label courseTitle = new Label(course.getCourseName());
            courseList.getItems().add(courseTitle);
        }
        
        courseList.getSelectionModel().select(0);
        
        accordion.setExpandedPane(courseOverview);
        
        welcomeText.setText("Hei, " + user.getFirstname() + " " + user.getLastname());
    }
    
    public void runGUI(UserDetails user) throws IOException {
        CourseSelectorController.user = user;
        
        primaryStage = new Stage();
        Parent root2 = FXMLLoader.load(CourseSelectorController
                .class.getResource("courseSelector.fxml"));
        
        primaryStage.setScene(new Scene(root2));
        //primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Innlogging");
        primaryStage.show();
    }
    
    
    
    public void enterCourse(){
        
        Label courseTitle = courseList.getSelectionModel().getSelectedItem();
        
        CourseInfo course;
        for (CourseInfo c : courses) {
            if (c.getCourseName().equals(courseTitle.getText())) {
                course = c;
                user.setCourseID(course.getCourseID());
                break;
            }
        }

        user.setTeacher(lookupCourseBeanRemote()
                .isTeacher(user.getId(), user.getCourseID()));
        
        System.out.println(user.isTeacher());
        if (user.isTeacher())
            new TeacherMain().runGUI(Main.primaryStage, user);
        else
            new MainStudent().runGUI(Main.primaryStage, user);
        
        primaryStage.close();
    }
    
    private CourseBeanRemote lookupCourseBeanRemote() {
        try {
            Context c = new InitialContext();
            return (CourseBeanRemote) c.lookup("java:comp/env/CourseBean");
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
