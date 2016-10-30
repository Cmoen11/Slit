
package slit.Teacher;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.web.HTMLEditor;
import modul.ModuleRemote;
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
    static String username;
    public void changeName() {
        name.setText("Velkommen, " + Controller.username);
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
    
    private ModuleRemote lookupModulRemote() {
        try {
            Context c = new InitialContext();
            return (ModuleRemote) c.lookup("java:comp/env/Modul");
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
    
}
