
package slit.Teacher;

import auth.UserDetails;
import com.sun.javafx.scene.control.skin.ScrollPaneSkin;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModulRemote;
import java.util.ArrayList;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import slit.client.Main;
import user_details.UserBeanRemote;
/**
 *
 * @author Christian
 */
public class Controller {
    
    private ArrayList<String> learningGoals_list = new ArrayList<>();
    @FXML ScrollPane scroll;
    @FXML Text welcomeMessage;
    static UserDetails user;

    public static UserDetails getUser() {
        return user;
    }

    public static void setUser(UserDetails user) {
        Controller.user = user;
    }
    
    public void changeName() {
        if (user == null) System.out.println("what the fuck?");
        UserDetails temp = lookupUserBeanRemote().getUserByID(user.getId());
        user.setFirstname(temp.getFirstname()); 
        user.setLastname(temp.getLastname());
        welcomeMessage.setText("Hei, " + temp.getFirstname() + " " +temp.getLastname());
    }
    
    public void initialize() {
        changeName();
        try {
            final ScrollPaneSkin skin = (ScrollPaneSkin) scroll.getSkin();
            final Field field = skin.getClass().getDeclaredField("vsb");
            System.out.println(field);
            field.setAccessible(true);
            final ScrollBar scrollBar = (ScrollBar) field.get(skin);
            scrollBar.setUnitIncrement(25);
            scrollBar.setBlockIncrement(50);
        }catch(Exception e) {
            System.out.println(e);
        }
    }

    
    
    public void logOut() { 
        Main.runGUI();
    }
    private ModulRemote lookupModulRemote() {
        try {
            Context c = new InitialContext();
            return (ModulRemote) c.lookup("java:comp/env/Modul");
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
    
}
