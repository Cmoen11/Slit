
package slit.Teacher;

import auth.UserDetails;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModulRemote;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import slit.client.Main;
import user_details.UserBeanRemote;
/**
 *
 * @author Christian
 */
public class Controller {
    
    private ArrayList<String> learningGoals_list = new ArrayList<>();
    
    @FXML Label name;
    @FXML HTMLEditor moduleDesc;
    @FXML TextField moduleName;
    @FXML TextField learningGoal_input;
    @FXML ListView learning_goals_view;
    
    @FXML ScrollPane scroll;
    @FXML Text welcomeMessage;
    static UserDetails user;

    public static UserDetails getUser() {
        return user;
    }

    public static void setUser(UserDetails user) {
        Controller.user = user;
    }
    
    public void changeName() {
        if (user == null) System.out.println("what the fuck?");
        UserDetails temp = lookupUserBeanRemote().getUserByID(user.getId());
        user.setFirstname(temp.getFirstname()); 
        user.setLastname(temp.getLastname());
        System.out.println(temp.getFirstname());
        welcomeMessage.setText("Hei, " + temp.getFirstname() + " " +temp.getLastname());
    }
    
    public void initialize() {
        changeName();
    }
    
    
    public void createModule() {
        try {
            int id = lookupModulRemote().createModule(
                    moduleDesc.getHtmlText(), moduleName.getText());
            for (String goal : learningGoals_list) 
                lookupModulRemote().addLearningGoal(goal, id);
            
        }catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public void logOut() {
        Main.runGUI();
    }
    private ModulRemote lookupModulRemote() {
        try {
            Context c = new InitialContext();
            return (ModulRemote) c.lookup("java:comp/env/Modul");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    public void addLearningGoal() {
        learningGoals_list.add(learningGoal_input.getText());
        ObservableList<String> observableList = FXCollections.observableList(learningGoals_list);
        learning_goals_view.setItems(observableList);
        
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
