/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.Teacher.popups;

import auth.UserDetails;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleSubmissionDetails;
import slit.Teacher.TeacherMain;
import user_details.UserBeanRemote;

/**
 *
 * @author Christian
 */
public class FacilitateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private WebView selectedBlogPost;

    @FXML
    private HTMLEditor answerSubmission;

    @FXML
    private WebView moduleDesc;

    @FXML
    private JFXListView<?> allBlogPosts;

    @FXML
    private JFXListView<?> moduleLearningGoals;

    @FXML
    private WebView moduleSubmission;
    
    @FXML private Text studentName;
    
    static ModuleSubmissionDetails submission;
    
    @FXML
    void initialize() {
        WebEngine webEngine = moduleSubmission.getEngine();
        webEngine.loadContent("<img src=\"http://i63.tinypic.com/mrfmkz.png\" border=\"0\" alt=\"\">");
        if (submission == null) System.out.println("what?");
        
        UserDetails user = lookupUserBeanRemote().getUserByID(submission.getUserID());
        studentName.setText(user.getFirstname() + " " + user.getLastname());
        
        
    }
    public void displayPopup(ModuleSubmissionDetails submission) throws IOException {
        
        this.submission = submission;
        
        Parent root = TeacherMain.getRoot();
        
        Stage primaryStage = new Stage();
        Parent root2 = FXMLLoader.load(FacilitateController.class.getResource("FacilitateModuleSubmission.fxml"));
        primaryStage.setScene(new Scene(root2));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(root.getScene().getWindow());
        primaryStage.setTitle("Modulgodkjenning");
        primaryStage.showAndWait();
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
