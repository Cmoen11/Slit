package slit.Teacher;

import auth.UserDetails;
import blog.Post;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.util.Duration;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleSubmissionDetails;
import modul.SubmissionBeanRemote;
import org.controlsfx.control.Notifications;
import sessionBeans.HelpRequestBeanRemote;
import sessionBeans.NewsBeanRemote;
import slit.Teacher.popups.FacilitateController;
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
    @FXML private JFXListView<Label> assignedHelp;

    private ArrayList<Post> posts;  // to store our newsPosts
    private ArrayList<UserDetails> allUsers;
    private ArrayList<ModuleSubmissionDetails> moduleSubs;
    private List<HelpRequestDetails> unassignedHelpRequests;
    private ArrayList<ModuleSubmissionDetails> assignedSubs;
    private List<HelpRequestDetails> assignedHelpRequest;
    
    // pupup for module submission.
    private FacilitateController moduleSubmission;
    
    
    public void initialize() {
        try {
            clearListViews();
            getAllUsers();
            fillUpSubmissions();
            addSubmissionsToListView();
            getPostsAndAddThem();
            getAllUnassignedHelpRequests();
            getAllAssignedModuleSubmission();
            getAllAssignedHelpRequests();

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
            Parent root = TeacherMain.getRoot();
            Notifications notification = Notifications.create()
                    .title("Nyhetsposten er opprettet!")
                    .text("Posten med navn " + post.getTitle() + " er opprettet!")
                    .graphic(null)
                    .hideAfter(Duration.seconds(4))
                    .position(Pos.BOTTOM_RIGHT)
                    .owner(root.getScene().getWindow())
                    .darkStyle();
                
        notification.showConfirm();
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
     * Uassign the selected moduleSubmission
     */
    public void unassignModule() {
        try {
            int index =  assignedSubmissions.getSelectionModel().getSelectedIndex();
            ModuleSubmissionDetails submission = assignedSubs.get(index);
            lookupSubmissionBeanRemote().unAssignModuleSubmission(submission);
        }catch (ArrayIndexOutOfBoundsException e) {
            Parent root = TeacherMain.getRoot();
            Notifications notification = Notifications.create()
                .title("Vennligst velg en innlevering")
                .text("Du har ikke valgt en innlevering. "
                        + "Vennligst velg den innleveringen du ønsker å sende tilbake til køen..")
                .graphic(null)
                .hideAfter(Duration.seconds(4))
                .position(Pos.BOTTOM_RIGHT)
                .owner(root);
                
        notification.showError();
        }
        
        initialize();
    }
    
    /**
     * Assign selected help request.
     */
    public void assignHelpRequest() {
        int index = unassignedHelp.getSelectionModel().getSelectedIndex();
        HelpRequestDetails request = unassignedHelpRequests.get(index);
        lookupHelpRequestBeanRemote().assignHelpRequest(request, Controller.getUser().getId());
        
        initialize();
        
    }
    
    /**
     * Unassign the selected helprequest
     */
    public void unassignHelpRequest() {
        int index = assignedHelp.getSelectionModel().getSelectedIndex();
        HelpRequestDetails request =  assignedHelpRequest.get(index);
        
        lookupHelpRequestBeanRemote().unassignHelpRequest(request);
        initialize();
        
    }
    
    /**
     * Open slected modulesubmission.
     */
    public void openModuleSubmission() {
        
        try {
            int index =  assignedSubmissions.getSelectionModel().getSelectedIndex();
            ModuleSubmissionDetails submission = assignedSubs.get(index);
            moduleSubmission = new FacilitateController();
            moduleSubmission.displayPopup(submission);
            initialize(); 
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Assign and open the selected modulesubmission
     */
    public void assignAndOpenModuleSubmission() {
        
        int index = unassignedModules.getSelectionModel().getSelectedIndex();
        ModuleSubmissionDetails submittedModule = moduleSubs.get(index);
        // assign the submission
        
        // also; set the status to 1 || directly translated to "under processing"
        lookupSubmissionBeanRemote()
                .assignSubmissionToUser(
                        submittedModule, Controller.getUser().getId());

        
         
        // open the submission
        moduleSubmission = new FacilitateController();
        try {
            moduleSubmission.displayPopup(submittedModule);
            // update the GUI.
            initialize();
        } catch (IOException ex) {
            Logger.getLogger(ControllerAdm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * clear all listviews, (for updating the gui with new information).
     */
    private void clearListViews() {
        existingNews.getItems().clear();
        unassignedHelp.getItems().clear();
        unassignedModules.getItems().clear();
        assignedSubmissions.getItems().clear();
        assignedHelp.getItems().clear();
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
    
    /**
     * get all assigned modules from database.
     */
    private void getAllAssignedModuleSubmission(){
        assignedSubs = lookupSubmissionBeanRemote()
                .getAssignedModulesForUser(Controller.getUser().getId(), 
                        Controller.getUser().getCourseID());
        
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
    
    /**
     * get all assigned help requests from database.
     */
    private void getAllAssignedHelpRequests() {
        assignedHelpRequest = lookupHelpRequestBeanRemote()
                .getAssignedHelpRequests(
                        Controller.getUser().getId(),
                        Controller.getUser().getCourseID());
        
        for (HelpRequestDetails requests : assignedHelpRequest) {
            UserDetails user = null;
                for (UserDetails obj : allUsers) {
                    if (obj.getId() == requests.getUserID()) {
                        user = obj;
                        break;
                    }
                }
                
            if (user != null) {
                Label lbl = new Label(
                    requests.getRequestID() + ": "
                        + user.getFirstname() + " "
                        + user.getLastname()
                        + " " + requests.getCreationDate().getDate()
                        + "/" + requests.getCreationDate().getMonth());
                assignedHelp.getItems().add(lbl);
            }
        }
    }
        
    
    
    /*
        all bean lookups.
    */
    private SubmissionBeanRemote lookupSubmissionBeanRemote() {
        try {
            Context c = new InitialContext();
            return (SubmissionBeanRemote) c.lookup("java:global/slit-bean/SubmissionBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private UserBeanRemote lookupUserBeanRemote() {
        try {
            Context c = new InitialContext();
            return (UserBeanRemote) c.lookup("java:global/slit-bean/UserBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private NewsBeanRemote lookupNewsBeanRemote() {
        try {
            Context c = new InitialContext();
            return (NewsBeanRemote) c.lookup("java:global/slit-bean/NewsBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    private HelpRequestBeanRemote lookupHelpRequestBeanRemote() {
        try {
            Context c = new InitialContext();
            return (HelpRequestBeanRemote) c.lookup("java:global/slit-bean/HelpRequestBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
