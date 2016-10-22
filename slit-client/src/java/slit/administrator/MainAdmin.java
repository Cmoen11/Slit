
package slit.administrator;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import slit.Teacher.Controller;
import slit.Teacher.TeacherMain;
import slit.client.Main;

/**
 *
 * @author Christian
 */
public class MainAdmin extends Application {

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
            Parent root= FXMLLoader.load(MainAdmin.class.getResource("AdminPanel.fxml"));
            primaryStage.setTitle("Slit Administrasjons Panel");
            primaryStage.setScene(new Scene(root));
            primaryStage.centerOnScreen();
            primaryStage.show();
            primaryStage.setResizable(false);
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
