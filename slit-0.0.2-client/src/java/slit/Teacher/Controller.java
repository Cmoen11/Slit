
package slit.Teacher;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author Christian
 */
public class Controller {
    @FXML Label name;
    static String username;
    public void changeName() {
        name.setText("Velkommen, " + Controller.username);
    }
}
