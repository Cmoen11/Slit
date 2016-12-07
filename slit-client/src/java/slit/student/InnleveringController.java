/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.student;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Refi
 */
public class InnleveringController {
    static Stage primaryStage;
    

public void run() throws IOException {
    Parent root = MainStudent.getRoot();
    
    primaryStage = new Stage();
    Parent root2 = FXMLLoader.load(InnleveringController.class.getResource("Innlevering.fxml"));
    primaryStage.setScene(new Scene(root2));
    primaryStage.initModality(Modality.APPLICATION_MODAL);
    primaryStage.initOwner(root.getScene().getWindow());
    primaryStage.setTitle("Innlevering");
    primaryStage.showAndWait();
    
}
    
}
