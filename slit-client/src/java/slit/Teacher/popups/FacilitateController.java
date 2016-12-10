package slit.Teacher.popups;

import auth.UserDetails;
import blog.Post;
import blog.blogBeanRemote;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleDetails;
import modul.ModuleRemote;
import modul.ModuleSubmissionDetails;
import modul.SubmissionBeanRemote;
import modul.SubmissionFeedbackDetails;
import org.controlsfx.control.Notifications;
import sessionBeans.InternalStudentCommentsBeanRemote;
import slit.Teacher.TeacherMain;
import slit.Teacher.Controller;
import transferClasses.InternalStudentComments;
import transferClasses.StudentSubmissionHistory;
import user_details.UserBeanRemote;

/**
 *
 * @author Christian
 */
public class FacilitateController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private WebView selectedBlogPost;
    @FXML private HTMLEditor answerSubmission;
    @FXML private WebView moduleDesc;
    @FXML private JFXListView<Label> allBlogPosts;
    @FXML private ListView<Label> moduleLearningGoals;
    @FXML private WebView moduleSubmission; 
    @FXML private Text studentName;
    @FXML private JFXButton downloadAssignedFile;
    @FXML private Text fileName;
    @FXML private TextField newInternalComment;
    @FXML private JFXListView<Label> internalCommentsView;
    @FXML private TableColumn<SubmissionHistory, String> historyStatus;
    @FXML private TableColumn<SubmissionHistory, String> historyType;
    @FXML private TableColumn<SubmissionHistory, String> historyDate;
    @FXML private TableView<SubmissionHistory> submissionHistory;

    static ModuleSubmissionDetails submission;
    static Stage primaryStage;
    ModuleDetails moduleInfo;
    UserDetails user;
    SubmissionFeedbackDetails feedback;
    ArrayList<InternalStudentComments> internalComments;
    ArrayList<Post> blogPosts; 
    
    /**
     * This method is called whenever someone will open a modulesubmission frame.
     * @param submission    selected submission to be opened.
     * @throws IOException 
     */
    public void displayPopup(ModuleSubmissionDetails submission) throws IOException {
        
        FacilitateController.submission = submission;
        
        Parent root = TeacherMain.getRoot();
        
        primaryStage = new Stage();
        Parent root2 = FXMLLoader.load(FacilitateController.class.getResource("FacilitateModuleSubmission.fxml"));
        primaryStage.setScene(new Scene(root2));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(root.getScene().getWindow());
        primaryStage.setTitle("Modulgodkjenning");
        primaryStage.showAndWait();
    }
    
    @FXML
    void initialize() {
        getModuleInfo();
        settingUserObject();
        getBlogPosts();
        
        WebEngine webEngine = moduleSubmission.getEngine();
        webEngine.loadContent(submission.getContent());
        getFeedback();
        
        // disable the Download button if there is no file assigned
        if (submission.getFile() == null) downloadAssignedFile.setDisable(true);
        else                              downloadAssignedFile.setDisable(false);
        
        historyType.setCellValueFactory(new PropertyValueFactory<>("moduleName"));
        historyDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        historyStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        submissionHistory();
        getInternalComments();
        

    }

    /**
     * get the submitters user object.
     */
    private void settingUserObject() {
        user = lookupUserBeanRemote().getUserByID(submission.getUserID());
        user.setCourseID(moduleInfo.getCourseID());
        studentName.setText(user.getFirstname() + " " + user.getLastname());
    }
    
    /**
     * get the module info for the selected submission.
     */
    private void getModuleInfo() {
        moduleInfo = lookupModuleBeanRemote().getModuleByID(submission.getModuleID());
        WebEngine moduleDescEngine = moduleDesc.getEngine();
        moduleDescEngine.loadContent("<h3>"+moduleInfo.getName()+"</h3>" +
                moduleInfo.getDescription());
        
         for (String details : moduleInfo.getLearningGoals()) {
            Label item = new Label(details);
            item.setWrapText(true);
            moduleLearningGoals.getItems().add(item);
        }
        
        if(moduleInfo.getLearningGoals().isEmpty()) {
            System.out.println("hallo heehe");
        }
    }
    
    /**
     * If there is any feedback, get it and apply it to Gui.
     */
    private void getFeedback() {
        feedback = lookupSubmissionBeanRemote().getFeedbackDetailsFromSubmissionID(submission);
        answerSubmission.setHtmlText(feedback.getContent());
    }
    
    /**
     * get all blog posts
     */
    private void getBlogPosts(){
        blogPosts = lookupblogBeanRemote().getPostFromUserAndCourse(user);
        
        allBlogPosts.getItems().clear();
        for (Post p : blogPosts){
            Label l = new Label(p.getTitle());
            allBlogPosts.getItems().add(l);
        }
    }
    
    /**
     * get all submission the user has submitted before and place it into the 
     * gui.
     */
    private void submissionHistory() {
         ArrayList<StudentSubmissionHistory> items = 
        lookupSubmissionBeanRemote()
                .getSubmissionHistoryFromUser(submission.getUserID(),
                        moduleInfo.getCourseID())
                .getHistory();
        ObservableList<SubmissionHistory> history = FXCollections.observableArrayList(new ArrayList<>());
        for (StudentSubmissionHistory item : items) {
            SubmissionHistory historyItem = 
                    new SubmissionHistory(item.getDate(),
                            item.getModuleName(),
                            item.getStatus());
            history.add(historyItem);
        }
        
        submissionHistory.setItems(history);
    }
    
    /**
     * get internal comments for the selected student.
     */
    private void getInternalComments() {
        internalComments = lookupInternalStudentCommentsBeanRemote()
                .getAllComments(user.getId(), moduleInfo.getCourseID());
        internalCommentsView.getItems().clear();
        for (InternalStudentComments c : internalComments) {
            
            Label l = new Label(c.getComment());
            internalCommentsView.getItems().add(l);
        }
    }
    
    /**
     * add an internal comment for the selected user.
     */
    public void addInternalComment() {
        InternalStudentComments obj = new InternalStudentComments(
                new Date(), Controller.getUser().getId(), user.getId(),
                moduleInfo.getCourseID(), newInternalComment.getText()
        );
        lookupInternalStudentCommentsBeanRemote().addComment(obj);
        
        // update the gui with the new comment.
        getInternalComments();
        newInternalComment.clear();
    }
    
    /**
     * Open the selected blog posts
     */
    public void openBlogPost() {
        // TO DO
    }
    /**
     * Save current state of the process of the submission
     */
    public void saveAndClose() {
        
        SubmissionFeedbackDetails details = new SubmissionFeedbackDetails();
        details.setContent(answerSubmission.getHtmlText());
        lookupSubmissionBeanRemote().saveTeacherFeeback(FacilitateController.submission, details);
        Parent root = TeacherMain.getRoot();
        Notifications notification = Notifications.create()
                .title("Dine endringer er lagret")
                .text("Besvarelsen er endret til senere!")
                .graphic(null)
                .hideAfter(Duration.seconds(4))
                .position(Pos.BOTTOM_RIGHT)
                .owner(root.getScene().getWindow())
                .darkStyle();
                
        notification.showConfirm();
        
        primaryStage.close();
    }
    /**
     * Accept the submssion
     */
    public void acceptSubmission() {
        SubmissionFeedbackDetails details = new SubmissionFeedbackDetails();
        details.setContent(answerSubmission.getHtmlText());
        lookupSubmissionBeanRemote().acceptSubmission(FacilitateController.submission, details);
        Parent root = TeacherMain.getRoot();
        Notifications notification = Notifications.create()
                .title("Besvarelsen er godkjent")
                .text("Besvarelsen er satt til godkjent.")
                .graphic(null)
                .hideAfter(Duration.seconds(4))
                .position(Pos.BOTTOM_RIGHT)
                .owner(root.getScene().getWindow())
                .darkStyle();
                
        notification.showConfirm();
        primaryStage.close();
    }
    /**
     * Decline the submission
     */
    public void declineSubmission() {
        SubmissionFeedbackDetails details = new SubmissionFeedbackDetails();
        details.setContent(answerSubmission.getHtmlText());
        lookupSubmissionBeanRemote().declineSubmission(FacilitateController.submission, details);
        Parent root = TeacherMain.getRoot();
        Notifications notification = Notifications.create()
                .title("Besvarelsen er avist")
                .text("Besvarelsen er avist.")
                .graphic(null)
                .hideAfter(Duration.seconds(4))
                .position(Pos.BOTTOM_RIGHT)
                .owner(root.getScene().getWindow())
                .darkStyle();
                
        notification.showConfirm();
        primaryStage.close();
    }
    
    // Server connection.
    private UserBeanRemote lookupUserBeanRemote() {
        try {
            Context c = new InitialContext();
            return (UserBeanRemote) c.lookup("java:global/slit-bean/UserBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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
    private InternalStudentCommentsBeanRemote lookupInternalStudentCommentsBeanRemote() {
        try {
            Context c = new InitialContext();
            return (InternalStudentCommentsBeanRemote) c.lookup("java:global/slit-bean/InternalStudentCommentsBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private blogBeanRemote lookupblogBeanRemote() {
        try {
            Context c = new InitialContext();
            return (blogBeanRemote) c.lookup("java:global/slit-bean/blogBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    
}
