
package slit.Teacher;

import auth.UserDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleDetails;
import modul.ModuleSubmissionDetails;
import modul.SubmissionBeanRemote;
import sessionBeans.studentOverviewRemote;
import transferClasses.StudentSubmissionHistory;
import user_details.UserBeanRemote;

/**
 *
 * @author TorOle
 */
public class studentOverviewController {
    
    // Formålet her er at vi fra og med de to linjene under skal kunne
    // referer til ListVeiw i Scene Builder som studentListe
    @FXML
    ListView<Label> studentList;
    
    @FXML ListView<Label> moduleSubmissionListView;
    
    // Nedenfor angir vi at bønnene kan referes til med kortere navn, 
    // nærmere best overveiwBean og userOverveiw
    studentOverviewRemote overviewBean = lookupstudentOverviewBeanRemote();
    UserBeanRemote userOverveiw = lookupUserBeanRemote();
    ArrayList<UserDetails> students;
    ArrayList<StudentSubmissionHistory> submissionsForSelectedUser;
    
    public void initialize() {
        students = userOverveiw.getAllUsers();
        for(UserDetails user : students) {
            Label l = new Label(user.getUsername() +
                    "(" + countModuleSubmissions(user.getId()) + ")");
            studentList.getItems().add(l);
                
        }
        if (!students.isEmpty()) {
            studentList.getSelectionModel().select(0);
            changeSubmissionList();
        }
        
        studentList.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends Label> observable, 
                        Label oldValue, Label newValue) -> {
            changeSubmissionList();
        });
        
        
    }
    
    private int countModuleSubmissions(int userID) {
        ArrayList<StudentSubmissionHistory> temp;
        temp = lookupSubmissionBeanRemote()
                .getSubmissionHistoryFromUser(userID,
                        Controller.getUser().getCourseID())
                .getHistory();
        
        return temp.size();
    }
    
    private void changeSubmissionList() {
      
        moduleSubmissionListView.getItems().clear();
        
        int index = studentList.getSelectionModel().getSelectedIndex();
        UserDetails user = students.get(index);
        
        
        
        submissionsForSelectedUser = lookupSubmissionBeanRemote()
                .getSubmissionHistoryFromUser(user.getId(),
                        Controller.getUser().getCourseID())
                .getHistory();
        
        for (StudentSubmissionHistory sh : submissionsForSelectedUser) {
            Label l = new Label(sh.getModuleName());
            moduleSubmissionListView.getItems().add(l);
        }
    }
    
    // Midlertidig testknapp forå se at at bean / interface fungerer som det skal
    public void pressMe() {
        System.out.println(overviewBean.clickMe());
    }
    
    
    // Knappen lister ut samtlige studenter. Foreløpig blir de listet ut i konsollen, todo - list dem i 
    // listen på siden. Lister ut brukernavnet på personene i databasen. 
    public void listAllStudents(){
        System.out.println(userOverveiw.getAllUsers());
        System.out.println(userOverveiw.getUserByID(2)); // testlinje, skal hente bruker med PK = 2
    }
    
    

    // Sørger for at controller klassen er koblet sammen med studentOverveiwBean
    private studentOverviewRemote lookupstudentOverviewBeanRemote() {
        try {
            Context c = new InitialContext();
            return (studentOverviewRemote) c.lookup("java:global/slit-bean/studentOverviewBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
    // Sørger for at controller klassen faktisk er koblet sammen med UserBeanRemote
    private UserBeanRemote lookupUserBeanRemote() {
        try {
            Context c = new InitialContext();
            return (UserBeanRemote) c.lookup("java:global/slit-bean/UserBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private SubmissionBeanRemote lookupSubmissionBeanRemote() {
        try {
            Context c = new InitialContext();
            return (SubmissionBeanRemote) c.lookup("java:global/slit-bean/SubmissionBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
}
