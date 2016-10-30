package slit.Teacher;

import auth.UserDetails;
import blog.Post;
import com.jfoenix.controls.JFXListView;
import java.util.ArrayList;
import java.util.Date;
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
import sessionBeans.NewsBeanRemote;
import user_details.UserBeanRemote;

/**
 * Controllerclass for TeacherAdmModuleAndHelp.fxml
 *
 * @author Christian
 */
public class ControllerAdm {

    @FXML
    private JFXListView<Label> unassignedModules;
    @FXML
    private JFXListView<Label> unassignedHelp;

    @FXML
    private HTMLEditor newsContent;

    @FXML
    private TextField newsTitle;

    @FXML
    private JFXListView<Label> existingNews;
    
    ArrayList<Post> posts;
    
    public void initialize() {
        try {
            existingNews.getItems().clear();
            unassignedHelp.getItems().clear();
            unassignedModules.getItems().clear();

            ArrayList<ModuleSubmissionDetails> moduleSubs
                    = (ArrayList<ModuleSubmissionDetails>) lookupSubmissionBeanRemote().getSubmissions(Controller.user.getCourseID()); // need to implement course.

            ArrayList<UserDetails> allUsers = lookupUserBeanRemote().getAllUsers();

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

        } catch (Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < 10; i++) {
            try {
                //Label lbl = new Label("Module " + i);
                Label lbl2 = new Label("Help " + i);
                //unassignedModules.getItems().add(lbl);
                unassignedHelp.getItems().add(lbl2);
            } catch (Exception e) {

            }
        }
        unassignedModules.setExpanded(true);
        unassignedHelp.setExpanded(false);
    }

    public void createModule() {
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
    
    public void removePost() {
        int index = existingNews.getSelectionModel().getSelectedIndex();
        Post post = posts.get(index);
        lookupNewsBeanRemote().removePost(post);
        initialize();
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

}
