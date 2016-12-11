/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.student;

import auth.UserDetails;
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
import modul.ModuleSubmissionDetails;
import modul.SubmissionBeanRemote;

/**
 *
 * @author Martin Nenseth
 */
public class InnleveringController {
    static Stage primaryStage;
    
    @FXML private Text moduleName;
    @FXML private HTMLEditor submissionText; // Must be set to have max 255 characters OR expand column constraint in db.
    @FXML private JFXButton uploadFile;
    
    @FXML private WebView moduleDesc;
    @FXML private JFXListView moduleLearningGoals;
    
    
    static ModuleDetails moduleInfo;
    
    /*
    * Gets and sets info of the selected module.
    * TODO:
    * getSubmissionText(); - Must be implemented
    * uploadFile(File file);- Must be implemented 
    */
    public void initialize() {
        getAndSetModuleName();
        getAndSetModuleDesc();
        getAndSetLearningGoals();
        
    }
    
    /*
    *Get and set modulename through moduleID
    */
    private void getAndSetModuleName() {
        moduleInfo = lookupModuleBeanRemote().getModuleByID(moduleInfo.getModuleID());
        moduleName.setText(moduleInfo.getName());
    }
    
    /*
    *Get and set the description of the module
    */
    private void getAndSetModuleDesc() {
        WebEngine moduleDescEngine = moduleDesc.getEngine();
        moduleDescEngine.loadContent(moduleInfo.getDescription());
    }
    
    /* 
    * Get all learninggoals from the module, and add them to the listView moduleLearningGoals
    * Fit the learningGoal into a label properly.
    */
    private void getAndSetLearningGoals () {
           for (String goal : moduleInfo.getLearningGoals()) {
            Label learningGoal = new Label(goal);
            learningGoal.setWrapText(true);
            moduleLearningGoals.getItems().add(learningGoal);
        }
    }
    
    /*
    * Create a new Stage with a new scene that loads the correct .fxml file(GUI)
    * Block users ability to interact with other windows than this.
    * Set Stage owner to studentPanel.
    * @param: The selected module from studentPanel. 
    */
    public void run(ModuleDetails module) throws IOException {
        InnleveringController.moduleInfo = module;
        
        Parent root = MainStudent.getRoot();

        primaryStage = new Stage();
        Parent root2 = FXMLLoader.load(InnleveringController.class.getResource("Innlevering.fxml"));
        primaryStage.setScene(new Scene(root2));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(root.getScene().getWindow());
        primaryStage.setTitle("Innlevering");
        primaryStage.showAndWait();

    }
    /*
    * Create a submission DTO
    * Append details of the sumbission into the pojo
    * Persist the object to the database
    * Close this window and return to studentPanel.
    * TODO: 
    * Implement setFile(File file)
    * Implement alertbox that confirms the users action
    * Submission Type should get its value from the respective module that stores it.
    */
    public void deliverSubmission() {
        ModuleSubmissionDetails submission = new ModuleSubmissionDetails();
        submission.setModuleID(moduleInfo.getModuleID());
        submission.setUserID(Controller.getUser().getId());
        submission.setContent(submissionText.getHtmlText());
        submission.setStatus(0);
        submission.setType("random");
        
        lookupSubmissionBeanRemote().createSubmission(submission);
        
        primaryStage.close();
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
