
package slit.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Chrristian Moen
 */
public class Main extends Application 
{    


    static Stage primaryStage;
    public static void runGUI() {
        new Main().start(primaryStage);
    }

    public static void main(String[] args) {
        // create dummy users
        //loginAuth_bean.CreateDummyUsers();
        
        // load main GUI
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            setUserAgentStylesheet(STYLESHEET_MODENA);
            Parent root= FXMLLoader.load(Main.class.getResource("login.fxml"));
            primaryStage.setTitle("Slit");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            Main.primaryStage = primaryStage;
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
