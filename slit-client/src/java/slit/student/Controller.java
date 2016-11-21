/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.student;

import auth.UserDetails;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleRemote;
import sessionBeans.NewsBeanRemote;
import slit.client.Main;

/**
 *
 * @author Martin
 */

public class Controller {
    @FXML JFXListView existingNews;
    @FXML JFXListView existingModules;
    
    @FXML JFXButton modulePanel;
    static UserDetails user;
    
    public static UserDetails getUser() {
        return user;
    }
      
    public static void setUser(UserDetails user){
        Controller.user = user;
    }
    
    
    
    // Set listViews equal to all modules for a user and all news in course.
    public void initialize() {
        try {
        existingModules = (JFXListView<Label>) lookupModuleBeanRemote().getAllModulesForUser(getUser().getId());
        existingNews = (JFXListView<Label>) lookupNewsBeanRemote().getPostsFromCourse(getUser().getCourseID());
        } 
        catch (Exception e){
            System.out.println(e);
        }
    }
    
    
    
    public void logOut() {
        Main.runGUI();
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
    
    
    private ModuleRemote lookupModuleBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ModuleRemote) c.lookup("java:comp/env/ModuleBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
