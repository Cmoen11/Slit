/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.student;

import blog.Post;
import blog.blogBeanRemote;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
 * @author Christian
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
        clearFields();
        archivedPost = blogBean.getPostFromUserAndCourse(Controller.getUser());
        archive.getItems().clear();
        for(Post p : archivedPost) {
            Label l = new Label(p.getTitle());
            archive.getItems().add(l);
        }
    }    
    
    public void deletePost(){
        int index = archive.getSelectionModel().getSelectedIndex();
        Post post = archivedPost.get(index);
        
        blogBean.deleteBlogPost(post);
        initialize();
    }            
    
    public void editPost(){
        int index = archive.getSelectionModel().getSelectedIndex();
        Post post = archivedPost.get(index);
        
        title.setText(post.getTitle());
        content.setHtmlText(post.getContent());
       
    }
    
    public void clearEditor(){
        clearFields();
    }
    
    public void updatePost(){
        int index = archive.getSelectionModel().getSelectedIndex();
        Post post = archivedPost.get(index);
        
        post.setContent(content.getHtmlText());
        post.setTitle(title.getText());
        blogBean.updatePost(post);
        initialize();
    }
    
    
    public void publishPost(){

        
        Post post = new Post();
        post.setContent(content.getHtmlText());
        post.setTitle(title.getText());
        post.setUserID(Controller.getUser().getId());
        post.setCourseID(Controller.getUser().getCourseID());

        blogBean.createPost(post);
        
        initialize();
    }
    
    private void clearFields() {
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
