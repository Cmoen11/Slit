package slit.Teacher;

import auth.UserDetails;
import blog.Post;
import com.jfoenix.controls.JFXListView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleSubmissionDetails;
import modul.SubmissionBeanRemote;
import sessionBeans.HelpRequestBeanRemote;
import sessionBeans.NewsBeanRemote;
import transferClasses.HelpRequestDetails;
import user_details.UserBeanRemote;

/**
 * Controllerclass for TeacherAdmModuleAndHelp.fxml
 *
 * @author Christian
 */
public class ControllerAdm {

    @FXML private JFXListView<Label> unassignedModules;
    @FXML private JFXListView<Label> unassignedHelp;
    @FXML private HTMLEditor newsContent;
    @FXML private TextField newsTitle;
    @FXML private JFXListView<Label> existingNews;
    @FXML private JFXListView<Label> assignedSubmissions;
            
    private ArrayList<Post> posts;  // to store our newsPosts
    private ArrayList<UserDetails> allUsers;
    private ArrayList<ModuleSubmissionDetails> moduleSubs;
    private List<HelpRequestDetails> unassignedHelpRequests;
    private ArrayList<ModuleSubmissionDetails> assignedSubs;
    
    public void initialize() {
        try {
            clearListViews();
            getAllUsers();
            fillUpSubmissions();
            addSubmissionsToListView();
            getPostsAndAddThem();
            getAllUnassignedHelpRequests();
            getAllAssignedModuleSubmission();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * create a new news post for the course.
     */
    public void createPost() {
        if (newsTitle.getText().isEmpty() || 
                newsContent.getHtmlText()
                        .equals("<html><head></head><body contenteditable=\"true\"></body></html>")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Tittel eller innhold er tomt.");
            alert.setHeaderText("Nyhetsfeil");
            alert.showAndWait();
        } else {
            Post post = new Post(
                    newsTitle.getText(),
                    newsContent.getHtmlText(),
                    new Date(),
                    Controller.user.getId(),
                    Controller.user.getCourseID(),
                    -1
            );
            //public Post(String title, String content, Date creationDate, int userID, int courseID, int postID) {
            lookupNewsBeanRemote().createPost(post);
            initialize();
            newsTitle.clear();
            newsContent.setHtmlText("<html><head></head><body contenteditable=\"true\"></body></html>");
        }
    }
    
    /**
     * remove the selected news post.
     */
    public void removePost() {
        int index = existingNews.getSelectionModel().getSelectedIndex();
        Post post = posts.get(index);
        lookupNewsBeanRemote().removePost(post);
        initialize();
    }
    
    /**
     * if delete buttom is pressed at the new post form. clear all data .
     */
    public void clearNewPostData() {
        newsContent.setHtmlText(
                "<html><head></head><body "
                        + "contenteditable=\"true\"></body></html>");
        newsTitle.clear();
    }
    
    /**
     * When the assign button on unassigned modules is beeing pressed. 
     * assign the selected module to the inlogged user.
     */
    public void assignModuleToMe() {
        // get selected unassiged module.
        int index = unassignedModules.getSelectionModel().getSelectedIndex();
        ModuleSubmissionDetails submittedModule = moduleSubs.get(index);
        
        // send the assign data to the database.
        // also; set the status to 1 || directly translated to "under processing"
        lookupSubmissionBeanRemote()
                .assignSubmissionToUser(
                        submittedModule, Controller.getUser().getId());

        // update the GUI.
        initialize();
    }
    
    
    
    
    /**
     * clear all listviews, (for updating the gui with new information).
     */
    private void clearListViews() {
        existingNews.getItems().clear();
        unassignedHelp.getItems().clear();
        unassignedModules.getItems().clear();
        assignedSubmissions.getItems().clear();
    }
    
    /**
     * get submissions
     */
    private void fillUpSubmissions() {
            moduleSubs = (ArrayList<ModuleSubmissionDetails>) 
                    lookupSubmissionBeanRemote()
                            .getSubmissions(Controller.user.getCourseID()); 
    }
    
    /**
     *  Fills up the users table
     */
    private void getAllUsers(){
        allUsers = lookupUserBeanRemote().getAllUsers();
    }
    
    /**
     * add submission to listview
     */
    private void addSubmissionsToListView() {
        for (ModuleSubmissionDetails subs : moduleSubs) {
                UserDetails user = null;
                for (UserDetails obj : allUsers) {
                    if (obj.getId() == subs.getUserID()) {
                        user = obj;
                        break;
                    }
                }
                
                if (user != null) {
                    Label lbl = new Label(
                            subs.getSubmissionID() + ": "
                            + user.getFirstname() + " "
                            + user.getLastname()
                            + " " + subs.getCreationDate().getDate()
                            + "/" + subs.getCreationDate().getMonth());
                    unassignedModules.getItems().add(lbl);
                }
            }
    }
    
    /**
     * get posts
     */
    private void getPostsAndAddThem() {
        posts = (ArrayList<Post>) lookupNewsBeanRemote().getPostsFromCourse(Controller.getUser().getId());
        for (Post obj : posts) {
            UserDetails author = null;
            for (UserDetails user : allUsers) {
                if (user.getId() == obj.getUserID()) {
                    author = user;
                    break;
                    }
                }

                Label postTitle = new Label(
                        obj.getTitle() + ", av " + author.getFirstname());
                existingNews.getItems().add(postTitle);
            }
    }
    
    /**
     * Get all unassigned help requests.
     * @throws Exception 
     */
    private void getAllUnassignedHelpRequests() throws Exception {
        unassignedHelpRequests = lookupHelpRequestBeanRemote()
                .getAllUnassignedHelpRequests(
                        Controller.getUser().getCourseID());
        
        System.out.println("Kurs id" + Controller.getUser().getCourseID());
        
        //unassignedHelp
        System.out.println(unassignedHelpRequests.size());
        for(HelpRequestDetails obj : unassignedHelpRequests) {
            
            Label requestTitle = null;
            for (UserDetails user : allUsers) {
                if (user.getId() == obj.getUserID()) {
                    requestTitle = new Label(obj.getRequestID() + ": " + user.getFirstname() 
                            + " " + user.getLastname());
                    break;
                }
            }
            
            if (requestTitle != null)
                unassignedHelp.getItems().add(requestTitle);
            else 
                throw new Exception(
                        "fant ikke bruker med brukerID'en oppgitt.");
        }        
        
    }
    
    private void getAllAssignedModuleSubmission(){
        assignedSubs = lookupSubmissionBeanRemote()
                .getAssignedModulesForUser(Controller.getUser().getId());
        
        //assignedSubmissions
        if (!assignedSubs.isEmpty()) {
            for (ModuleSubmissionDetails subs : assignedSubs) {
                UserDetails user = null;
                for (UserDetails obj : allUsers) {
                    if (obj.getId() == subs.getUserID()) {
                        user = obj;
                        break;
                    }
                }
                
                if (user != null) {
                    Label lbl = new Label(
                            subs.getSubmissionID() + ": "
                            + user.getFirstname() + " "
                            + user.getLastname()
                            + " " + subs.getCreationDate().getDate()
                            + "/" + subs.getCreationDate().getMonth());
                    assignedSubmissions.getItems().add(lbl);
                }
            }
        }
        
    }
    
    
    private SubmissionBeanRemote lookupSubmissionBeanRemote() {
        try {
            Context c = new InitialContext();
            return (SubmissionBeanRemote) c.lookup("java:comp/env/SubmissionBean");
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
    private NewsBeanRemote lookupNewsBeanRemote() {
        try {
            Context c = new InitialContext();
            return (NewsBeanRemote) c.lookup("java:comp/env/NewsBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private HelpRequestBeanRemote lookupHelpRequestBeanRemote() {
        try {
            Context c = new InitialContext();
            return (HelpRequestBeanRemote) c.lookup("java:comp/env/HelpRequestBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
