package slit.administrator;

import course.CourseInfo;
import auth.LoginAuthRemote;
import auth.UserDetails;
import course.CourseBeanRemote;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Calendar;
import java.util.Date;
import javafx.scene.control.CheckBox;
import javafx.util.StringConverter;
import user_details.UserBeanRemote;
/**
 *
 * @author Christian
 */
public class Controller {

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
    public void initialize() {
        courses = lookupLoginAuth_beanRemote().getCourses();

        existingCourses.setItems(FXCollections.observableArrayList(courses));
        existingCourses.getSelectionModel().select(0);
        existingStartDate.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
        setExistingCourseInfo();

    }
    
    /**
     * Oppdater GUI med Kursinformasjon. Denne blir kalt hver gang man velger
     * et kurs fra listview.
     */
    public void setExistingCourseInfo() {
        int index = existingCourses.getSelectionModel().getSelectedIndex();
        existingCourseName.setText(courses.get(index).getCourseName());
        existingCourseCode.setText(courses.get(index).getCourseCode());
        try {
            // for setting the members of the selected course.
            userdetails = lookupCourseBeanRemote().getCourseMembers(courses.get(index).getCourseID());
            
            courseMembers.setItems(
                    FXCollections.observableArrayList(userdetails));
            
            // for add new single user
            existingUsersNotInCourse = 
                    lookupCourseBeanRemote().getAllUsersNotInCourse(courses.get(index).getCourseID());
            existingAddSingleUserCombo.setItems(FXCollections.observableArrayList(existingUsersNotInCourse));
            new AutoCompleteComboBoxListener(existingAddSingleUserCombo);
            
            // for date Pickers
            //existingStartDate.setValue(LOCAL_DATE("01-10-2016"));
            //Date date = new Date(existingStartDate.getValue().toEpochDay());
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm");
            //String date_S = date.format(formatter);
            //System.out.println(date.getTime());
            
        } catch (Exception e) {
            courseMembers.getItems().clear();
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
        
        // Send changes to database
        lookupCourseBeanRemote().editCourse(course);
        
        //update client data
        existingCourses.setItems(FXCollections.observableArrayList(courses));
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
