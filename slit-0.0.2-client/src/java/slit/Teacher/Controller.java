
package slit.Teacher;

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

/**
 *
 * @author Christian
 */
public class Controller {
    @FXML Label name;
    @FXML TextArea moduleDesc;
    @FXML TextField moduleName;
    
    static String username;
    public void changeName() {
        name.setText("Velkommen, " + Controller.username);
    }
    
    public void createModule() {
        try {
        lookupModulRemote().createModule(moduleDesc.getText(), moduleName.getText());
        }catch(Exception e) {
            System.out.println(e);
        }
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
    
    
}
