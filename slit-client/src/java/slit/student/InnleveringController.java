/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleDetails;
import modul.ModuleRemote;

/**
 *
 * @author Martin Nenseth
 */
public class InnleveringController {
    static Stage primaryStage;
    
    @FXML private Text moduleName;
    @FXML private HTMLEditor submissionText;
    @FXML private JFXButton uploadFile;
    
    @FXML private WebView moduleDesc;
    @FXML private JFXListView moduleLearningGoals;
    
    
    static ModuleDetails moduleInfo;
    

    public void initialize() {
        getAndSetModuleName();
        getAndSetModuleDesc();
        getAndSetLearningGoals();
        //getSubmissionText(); - Must be implemented
        //uploadFile(File file);- Must be implemented

    }
    // Get and set modulename through moduleID
    private void getAndSetModuleName() {
        moduleInfo = lookupModuleBeanRemote().getModuleByID(moduleInfo.getModuleID());
        moduleName.setText(moduleInfo.getName());
    }
    
    //  Get and set the description of the module
    private void getAndSetModuleDesc() {
        WebEngine moduleDescEngine = moduleDesc.getEngine();
        moduleDescEngine.loadContent(moduleInfo.getDescription());
    }
    
   //   Get all learninggoals from the module, and add them to the listView moduleLearningGoals
    private void getAndSetLearningGoals () {
           for (String details : moduleInfo.getLearningGoals()) {
            Label learningGoal = new Label(details);
            learningGoal.setWrapText(true);
            moduleLearningGoals.getItems().add(learningGoal);
        }
        //May add condition to check wheter learningGoals from module is empty or not.
    }
    
    
    public void run(ModuleDetails module) throws IOException {
        InnleveringController.moduleInfo = module;
        
        Parent root = MainStudent.getRoot();

        primaryStage = new Stage();
        Parent root2 = FXMLLoader.load(InnleveringController.class.getResource("Innlevering.fxml"));
        System.out.println("hey");
        primaryStage.setScene(new Scene(root2));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(root.getScene().getWindow());
        primaryStage.setTitle("Innlevering");
        primaryStage.showAndWait();

    }

    private ModuleRemote lookupModuleBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ModuleRemote) c.lookup("java:global/slit-bean/ModuleBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
 
    
    
}
