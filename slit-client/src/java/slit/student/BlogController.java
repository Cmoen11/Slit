/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.student;

import blog.blogBeanRemote;
import java.net.URL;
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
public class BlogController implements Initializable {
    @FXML
    private ListView<Label> archive;

    @FXML
    private TextField title;

    @FXML
    private HTMLEditor content;
    
    
    blogBeanRemote blogBean = lookupblogBeanRemote();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void deletePost(){
        
    }            
    
    public void editPost(){
    
    }
    
    public void clearEditor(){
    
    }
    
    public void saveDraft(){
    
    }
    
    public void publishPost(){
    
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
