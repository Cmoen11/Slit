
package slit.client;

import auth.UserDetails;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Christian
 */
public class FirstTimeLoggedIn extends Application {
    
    static UserDetails obj;
    
    /**
     * If user has entered a correct password, he will be forwarded to this class
     * that will open up the student main page.
     */
    public void runGUI(Stage primaryStage, UserDetails obj) {
        start(primaryStage);
        FirstTimeLoggedIn.obj = obj;
    }

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root= FXMLLoader.load(FirstTimeLoggedIn.class.getResource("firstTimeLoggedIn.fxml"));
            primaryStage.setTitle("Førstegangsinnlogging");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
