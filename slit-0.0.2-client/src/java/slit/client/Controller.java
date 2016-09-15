
package slit.client;

import auth.LoginAuthRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Christian
 */
public class Controller {
    @FXML TextField username;
    @FXML TextField password;
    
    /**
     * if login button is pressed. 
     */
    public void loginButtonClicked() {
        System.out.println("what the fuck");
        if(lookupLoginAuthRemote().authAccount(username.getText(), password.getText())) {
            // if loginbutton is pressed & username and password is correct<
            System.out.println("works");
            new StudentMain().runGUI();     // launch student panel
        }
            
        else {
            System.out.println("nah..");
        }
        
    }

    private LoginAuthRemote lookupLoginAuthRemote() {
        try {
            Context c = new InitialContext();
            
            return (LoginAuthRemote) c.lookup("java:comp/env/LoginAuth");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
