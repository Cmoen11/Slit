
package slit.Teacher;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import slit.client.Main;
import slit.client.Main;

/**
 *
 * @author Christian
 */
public class TeacherMain extends Application {
    
    /**
     * If user has entered a correct password, he will be forwarded to this class
     * that will open up the student main page.
     */
    public void runGUI(Stage primaryStage) {
        start(primaryStage);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root= FXMLLoader.load(TeacherMain.class.getResource("TeacherClient.fxml"));
            primaryStage.setTitle("Slit");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
