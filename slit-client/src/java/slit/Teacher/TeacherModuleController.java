/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.Teacher;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleRemote;

/**
 *
 * @author Erlend
 */
public class TeacherModuleController {

    // List, edit, add and pick modules or learninggoals.
    private ArrayList<String> learningGoalsList = new ArrayList<>();
    @FXML
    ListView learningGoals;
    @FXML
    ListView modules;
    @FXML
    TextField moduleTitle;
    @FXML
    TextField learningGoal;
    @FXML
    HTMLEditor moduleSpecifications;

    public void newModuleButton() {
        lookupModulebeanRemote().newModule();
    }

    public void saveModuleButton() {
        lookupModulebeanRemote().saveModule();
    }

    public void openModuleButton() {
        lookupModulebeanRemote().openModule();
    }

    public void removeModuleButton() {
        lookupModulebeanRemote().removeModule();
    }

    public void addLearningGoalButton() {
        lookupModulebeanRemote().addLearningGoal();
    }

    public void removeLearningGoalButton() {
        lookupModulebeanRemote().removeLearningGoal();
    }

    private ModuleRemote lookupModulebeanRemote() {
        try {
            Context c = new InitialContext();
            return (ModuleRemote) c.lookup("java:comp/env/Modul_bean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
