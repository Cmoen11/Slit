/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.client;


import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.ejb.EJB;
import test.CoolboyRemote;

/**
 *
 * @author evenal
 */
public class Main extends Application 
{
    
    static Stage primaryStage;
    
    @EJB
    private static CoolboyRemote coolboy;

    public static void main(String[] args) {
        System.out.println(coolboy.HelloWorld());
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root= FXMLLoader.load(Main.class.getResource("login.fxml"));
            primaryStage.setTitle("Slit");
            primaryStage.setScene(new Scene(root, 348,401));
            primaryStage.show();
            Main.primaryStage = primaryStage;
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
