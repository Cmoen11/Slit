package slit.Teacher;

import auth.UserDetails;
import blog.Post;
import blog.blogBeanRemote;
import com.jfoenix.controls.JFXListView;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import user_details.UserBeanRemote;

/**
 * FXML Controller class
 *
 * @author Christian
 */
public class StudentBlogsController {
    
    ArrayList<UserDetails> students;
    ArrayList<Post> blogPosts;
    @FXML private JFXListView<UserDetails> allStudents;
    @FXML private JFXListView<Label> blogPostsListView;
    @FXML private WebView postContent;
    @FXML private Text blogTitle;
    
    WebEngine webEngine;
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        getAllUsers();
        addStudentsToListView();
        allStudents.getSelectionModel().select(0);
        getAllBlogPostsFromUser();
        insertBlogPostsIntoListView();
        
        allStudents.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserDetails>() {

            @Override
            public void changed(ObservableValue<? extends UserDetails> observable, UserDetails oldValue, UserDetails newValue) {
                // Your action here
                studentChanged();
            }
        });
        
        blogPostsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Label>() {

            @Override
            public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
                // Your action here
                webEngine = postContent.getEngine();
                webEngine.loadContent("");
                if (blogPostsListView.getSelectionModel().getSelectedIndex() != -1)
                    showPostByIndex(blogPostsListView.getSelectionModel().getSelectedIndex());
            }
        });
        
        
        
    }    
    
    public void studentChanged() {
        getAllBlogPostsFromUser();
        insertBlogPostsIntoListView();
        if (blogPostsListView.getItems().size() > 0) {
            blogPostsListView.getSelectionModel().select(0);
            showPostByIndex(0);
        }
        
    }
    
    private void showPostByIndex(int index) {
        webEngine = postContent.getEngine();
        webEngine.loadContent(blogPosts.get(index).getContent());
    }
    
    private void insertBlogPostsIntoListView() {
        if (blogPosts == null) getAllBlogPostsFromUser();
        blogPostsListView.getItems().clear();
        
        for (Post p : blogPosts) {
            Label l = new Label(p.getTitle());
            blogPostsListView.getItems().add(l);
        }
    }
    
    private void getAllBlogPostsFromUser() {
        int index = allStudents.getSelectionModel().getSelectedIndex();
        UserDetails user = students.get(index);
        user.setCourseID(Controller.getUser().getCourseID());
        blogPosts = lookupblogBeanRemote().getPostFromUserAndCourse(user);
    }
    
    private void getAllUsers() {
        System.out.println(Controller.getUser().getCourseID());
        if (students != null) students.clear();
        students = lookupUserBeanRemote()
                .getAllStudentFromCourse(Controller.getUser().getCourseID());
    }
    
    private void addStudentsToListView() {
        allStudents.getItems().clear();
        
        if (students == null) getAllUsers();
        
        for (UserDetails u : students) {
            allStudents.getItems().add(u);
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
