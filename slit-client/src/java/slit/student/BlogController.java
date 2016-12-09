
package slit.student;

import blog.Post;
import blog.blogBeanRemote;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * FXML Controller class
 *
 * @author Merethe
 */
public class BlogController{
    @FXML
    private ListView<Label> archive;

    @FXML
    private TextField title;

    @FXML
    private HTMLEditor content;
    
    
    blogBeanRemote blogBean = lookupblogBeanRemote();
    ArrayList <Post> archivedPost;
   
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        clearEditor();
        archivedPost = blogBean.getPostFromUserAndCourse(Controller.getUser());
        archive.getItems().clear();
        for(Post p : archivedPost) {
            Label l = new Label(p.getTitle());
            archive.getItems().add(l);
        }
    }    
      public void publishPost(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bekreftelse");
        alert.setHeaderText("Bekreftelse");
        alert.setContentText("Helt sikker på at du ønsker å publisere innlegget?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Post post = new Post();
            post.setContent(content.getHtmlText());
            post.setTitle(title.getText());
            post.setUserID(Controller.getUser().getId());
            post.setCourseID(Controller.getUser().getCourseID());

            blogBean.createPost(post);

            initialize();
        } else {
            // okay, do nothing
        }
        
    }
    
    public void deletePost(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bekreftelse");
        alert.setHeaderText("Bekreftelse");
        alert.setContentText("Helt sikker på at du ønsker å slette innlegget?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        int index = archive.getSelectionModel().getSelectedIndex();
            Post post = archivedPost.get(index);

            blogBean.deleteBlogPost(post);
            initialize();
        }
    }            
    
    public void editPost(){
        int index = archive.getSelectionModel().getSelectedIndex();
        Post post = archivedPost.get(index);
        
        title.setText(post.getTitle());
        content.setHtmlText(post.getContent());
       
    }

public void updatePost(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bekreftelse");
        alert.setHeaderText("Bekreftelse");
        alert.setContentText("Helt sikker på at du ønsker å endre innlegget?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int index = archive.getSelectionModel().getSelectedIndex();
            Post post = archivedPost.get(index);

            post.setContent(content.getHtmlText());
            post.setTitle(title.getText());
            blogBean.updatePost(post);
            initialize();
        }
    }
    
    /**
     * Clears HTML-editor
     */
    public void clearEditor(){
        title.setText("");
        content.setHtmlText("");
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
