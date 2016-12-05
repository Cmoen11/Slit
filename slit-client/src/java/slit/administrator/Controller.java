package slit.administrator;

import account.Authorisation;
import course.CourseInfo;
import auth.LoginAuthRemote;
import auth.UserDetails;
import course.CourseBeanRemote;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;    
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;
import java.util.Optional;
import java.util.prefs.Preferences;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import user_details.UserBeanRemote;
import slit.client.*;
/**
 *
 * @author Christian
 */
public class Controller {
    
    @FXML Text welcomeText;
    
    // Edit course
    @FXML ListView existingCourses;
    @FXML TextField existingCourseName;
    @FXML ListView courseMembers;
    @FXML ComboBox existingAddSingleUserCombo;
    @FXML DatePicker existingStartDate;
    @FXML DatePicker existingEndDate;
    @FXML TextField existingCourseCode;
    @FXML CheckBox existingIsTeacher;
    
    ArrayList<CourseInfo> courses;
    ArrayList<String> courseNames;
    ArrayList<UserDetails> userdetails;
    ArrayList<UserDetails> existingUsersNotInCourse;
    
    // create Course
    @FXML TextField newCourseName;
    @FXML ListView newCourseMembers;
    @FXML ComboBox newAddSingleUserCombo;
    @FXML DatePicker newStartDate;
    @FXML DatePicker newEndDate;
    @FXML TextField newCourseCode;
    @FXML CheckBox newIsTeacher;
    @FXML Button addManyUsers;
    
    
    public void initialize() {

        UserDetails user = Authorisation.getUserData();
        if (user != null) welcomeText.setText("Hei, " + user.getFirstname() + " " + user.getLastname());
        else welcomeText.setText("Missing userdata");
        
        courses = lookupLoginAuth_beanRemote().getCourses();
        
        try {
            if (courses.size() > 0) {
                existingCourses.setItems(FXCollections.observableArrayList(courses));
                existingCourses.getSelectionModel().select(0);
                LocalDate localDate = existingStartDate.getValue();

                setExistingCourseInfo();
            }
        }catch(Exception e) {
            
        }
                
    }
    
    /**
     * Oppdater GUI med Kursinformasjon. Denne blir kalt hver gang man velger
     * et kurs fra listview.
     */
    public void setExistingCourseInfo() {
        int index = existingCourses.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            existingCourseName.setText(courses.get(index).getCourseName());
            existingCourseCode.setText(courses.get(index).getCourseCode());
        }
        
        try {
            // for setting the members of the selected course.
            userdetails = lookupCourseBeanRemote().getCourseMembers(courses.get(index).getCourseID());
            
            courseMembers.setItems(
                    FXCollections.observableArrayList(userdetails));
            
            LocalDate ld;
            // for date Pickers
            ld = new java.sql.Date(courses.get(index).getStartDate().getTime()).toLocalDate();
            existingStartDate.setValue(ld);
            ld = new java.sql.Date(courses.get(index).getEndDate().getTime()).toLocalDate();
            existingEndDate.setValue(ld);
            
        } catch (Exception e) {
            courseMembers.getItems().clear();
        }
        try {
            // for add new single user
            existingUsersNotInCourse = 
                    lookupCourseBeanRemote().getAllUsersNotInCourse(courses.get(index).getCourseID());
            existingAddSingleUserCombo.setItems(FXCollections.observableArrayList(existingUsersNotInCourse));
            new AutoCompleteComboBoxListener(existingAddSingleUserCombo);
        }catch(Exception e) {
            existingAddSingleUserCombo.getItems().clear();
        }
    }
    /**
     * Formatere dato
     * @param dateString
     * @return 
     */
    private static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
    
    public void updateCourse() {
        int index = existingCourses.getSelectionModel().getSelectedIndex();
        CourseInfo course = courses.get(index);
        course.setCourseCode(existingCourseCode.getText());
        course.setCourseName(existingCourseName.getText());
        LocalDate ld;
        Instant date;
        ld = existingStartDate.getValue();
        date = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        course.setStartDate(Date.from(date));
        
        ld = existingEndDate.getValue();
        date = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        course.setEndDate(Date.from(date));
        
        // Send changes to database
        lookupCourseBeanRemote().editCourse(course);
        
        //update client data
        existingCourses.setItems(FXCollections.observableArrayList(courses));
        
        initialize(); // update the GUI   
        existingCourses.getSelectionModel().select(index);
        
    }
    
    public void addUserToCourse() {
        int index = existingCourses.getSelectionModel().getSelectedIndex();
        //UserDetails user = (UserDetails)existingAddSingleUserCombo.getSelectionModel().getSelectedItem();
        int index_2 = existingAddSingleUserCombo.getSelectionModel().getSelectedIndex();
        
        UserDetails user = existingUsersNotInCourse.get(index_2);
        
        int isTeacher = existingIsTeacher.isSelected() ? 1 : 0;
        System.out.println("AddUserToCourse " + user.getId() +" "+ courses.get(index).getCourseID() +" "+ isTeacher);
        lookupCourseBeanRemote().addMemberToCourse(
                user.getId(), courses.get(index).getCourseID(), existingIsTeacher.isSelected() ? 1 : 0);
        setExistingCourseInfo();
        
    }
    /*
    * Kaller MainAdmin.bulkUser() som bytter scener slik at bulkUsers.fxml blir et popup vindu.
    * 
    */
    public void bulkUsers() throws IOException{
        new MainAdmin().bulkUsers();
        
    }
    
    
    /**
     * Remove user from course
     */
    public void removeUserFromCourse() {
        // get coursedata and user that are to be removed.
        CourseInfo course = (CourseInfo) existingCourses.getSelectionModel().getSelectedItem(); 
        UserDetails userObj = userdetails.get(courseMembers.getSelectionModel().getSelectedIndex());

        if (course == null || userObj == null)
            System.out.println("Fikk ikke hentet data..");
        else {
            lookupCourseBeanRemote().removeUserFromCourse(userObj.getId(), course.getCourseID());
            setExistingCourseInfo(); // update the GUI    
        }
       
    }
    /**
     * set the selected member to teacher// Existing course only!
     */
    public void setUserToTeacher() {
        CourseInfo course = (CourseInfo) existingCourses.getSelectionModel().getSelectedItem(); 
        UserDetails userObj = (UserDetails) courseMembers.getSelectionModel().getSelectedItem();

        lookupCourseBeanRemote().switchUserStudentTeacher(userObj.getId(), course.getCourseID());
        setExistingCourseInfo(); // update the GUI   
    }
    private void clearNewCourseData() {
        newCourseCode.clear();
        newCourseName.clear();
        newStartDate.getEditor().clear();
        newEndDate.getEditor().clear();
    }
    public void createCourse() {
        CourseInfo obj = new CourseInfo();
        obj.setCourseCode(newCourseCode.getText());
        obj.setCourseName(newCourseName.getText());
        
        LocalDate ld;
        Instant date;
        
        ld = newStartDate.getValue();
        date = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        obj.setStartDate(Date.from(date));
        
        ld = newEndDate.getValue();
        date = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        obj.setEndDate(Date.from(date));
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Bekreftelse");
        alert.setHeaderText("Bekreftelse");
        alert.setContentText("Helt sikker på at du ønsker å opprette kurset " + obj + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            lookupCourseBeanRemote().createCourse(obj);
            setExistingCourseInfo();
            clearNewCourseData();
        } else {
            // okay, do nothing
        }
        
        
    }
    
    public void logOut() {
        Main.runGUI();
    }
    
    // connection to beans
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
