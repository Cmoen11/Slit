/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.student;

import blog.Post;
import com.jfoenix.controls.JFXListView;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleDetails;
import modul.ModuleRemote;
import sessionBeans.NewsBeanRemote;
import static slit.student.Controller.getUser;

/**
 *
 * @author Martin Nenseth
 */
public class ControllerStudentPanel {
    @FXML JFXListView news;
    @FXML JFXListView modules;
    
    ArrayList<ModuleDetails> existingModules;
    List<Post> existingNews;
    
    
    public void initialize() {
        try {
        getModules();
        getNews();
        } 
        catch (Exception e){
            System.out.println(e);
        }
    }
    
    private void getModules() {
    existingModules = lookupModuleBeanRemote().
                getAllModules(getUser().getCourseID());
        for (ModuleDetails module : existingModules) {
            Label moduleLabel = new Label();
            moduleLabel.setText(module.getName());
            modules.getItems().add(moduleLabel);
        }  
    }
    
    private void getNews() {
    existingNews = lookupNewsBeanRemote().
                getPostsFromCourse(getUser().getCourseID());
        for (Post post : existingNews) {
            Label newsLabel = new Label();
            newsLabel.setText(post.getTitle());
            news.getItems().add(newsLabel);
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
    
    
    private ModuleRemote lookupModuleBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ModuleRemote) c.lookup("java:global/slit-bean/ModuleBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
