
package slit.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import slit.client.Main;

/**
 *
 * @author Christian
 */
public class StudentMain extends Application {
    
    public void runGUI() {
        start(Main.primaryStage);
        //Application.launch(StudentMain.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root= FXMLLoader.load(Main.class.getResource("studentClient.fxml"));
            primaryStage.setTitle("Slit");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
