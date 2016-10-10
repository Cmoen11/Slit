
package slit.administrator;

import course.CourseInfo;
import auth.LoginAuthRemote;
import course.CourseBeanRemote;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Christian
 */
public class Controller {
    
    // Edit course
    @FXML ListView existingCourses;
    @FXML TextField existingCourseName;
    @FXML ListView courseMembers;
    
    
    ArrayList<CourseInfo> courses; 
    ArrayList<String> courseNames; 
    
    public void initialize() {
        courses = lookupLoginAuth_beanRemote().getCourses();
        courseNames = new ArrayList<>();
        for (CourseInfo course : courses)
            courseNames.add(course.getCoruseName());
        
        existingCourses.setItems(FXCollections.observableArrayList(courseNames));
        existingCourses.getSelectionModel().select(0);
        setExistingCourseInfo();
    }
    
    public void setExistingCourseInfo() {
        int index = existingCourses.getSelectionModel().getSelectedIndex();
        existingCourseName.setText(courses.get(index).getCoruseName());
        try {
            courseMembers.setItems(
                    FXCollections.observableArrayList(
                            lookupCourseBeanRemote().getCourseMembers(courses.get(index).getCourseID())));
        }catch(Exception e) {
            courseMembers.getItems().clear();
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

    private CourseBeanRemote lookupCourseBeanRemote() {
        try {
            Context c = new InitialContext();
            return (CourseBeanRemote) c.lookup("java:comp/env/CourseBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
