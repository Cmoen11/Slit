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
 * @author Martin Nenseth
 */
public class ControllerStudentPanel {
    @FXML JFXListView news;
    @FXML JFXListView modules;
    
    ArrayList<ModuleDetails> existingModules;
    List<Post> existingNews;
    
    // PopUp GUI for modules
    private InnleveringController modulOppgave;
    
    public void initialize() {
        try {
        getModules();
        getNews();
        } 
        catch (Exception e){
            System.out.println(e);
        }
    }
    /*
    * Get all modules related to the user and the course the user is logged in on.
    * ToDo: 
    * Condition to not show modules already delivered. 
    */
    private void getModules() {
    existingModules = lookupModuleBeanRemote().
                getAllModules(getUser().getCourseID());
        for (ModuleDetails module : existingModules) {
            Label moduleLabel = new Label();
            moduleLabel.setText(module.getName());
            modules.getItems().add(moduleLabel);
        }  
    }
    /*
    * Get all news related to the course the user is logged in on.
    */
    private void getNews() {
    existingNews = lookupNewsBeanRemote().
                getPostsFromCourse(getUser().getCourseID());
        for (Post post : existingNews) {
            Label newsLabel = new Label();
            newsLabel.setText(post.getTitle());
            news.getItems().add(newsLabel);
    }
        }
    
    /*
    * Get the selected module
    * Open that module in a popup through an instance of the popup controller, and
    * calling that controller's run method.
    * getStackTrace() is currently used for debugging purposes. 
    */
    public void openModule() {
        try {
        int index = modules.getSelectionModel().getSelectedIndex();
        ModuleDetails module = existingModules.get(index);
        modulOppgave = new InnleveringController();
        modulOppgave.run(module);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
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
