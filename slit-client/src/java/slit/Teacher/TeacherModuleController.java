/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.Teacher;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import modul.ModuleRemote;
import modul.ModuleDetails;

/**
 *
 * @author Erlend
 */
public class TeacherModuleController {

    // List, edit, add and pick modules or learninggoals.
    private ArrayList<String> learningGoalsList = new ArrayList<>();
    @FXML
    ListView<String> learningGoals;
    @FXML
    ListView<Label> modules;
    @FXML
    TextField moduleTitle;
    @FXML
    TextField learningGoal;
    @FXML
    HTMLEditor moduleSpecifications;

    ArrayList<ModuleDetails> existingModules;

    public void newModuleButton() {
        learningGoals.getItems().clear();
        moduleTitle.clear();
        learningGoal.clear();
        moduleSpecifications
                .setHtmlText(
                        "<html><head></head><body contenteditable=\"true\">"
                        + "</body></html>");

        boolean alreadyAdded = false;
        for (Label label : modules.getItems()) {
            if (label.getText().equals("<Ny modul>")) {
                alreadyAdded = true;
            }
        }
        if (!alreadyAdded) {
            modules.getItems().add(new Label("<Ny modul>"));
        }
        ModuleDetails modul = new ModuleDetails();
        modul.setCourseID(Controller.getUser().getCourseID());
        modul.setDescription("");
        modul.setModuleType("");
        modul.setName("");
        existingModules.add(modul);
        modules.getSelectionModel().select(modules.getItems().size() - 1);
    }

    public void initialize() {
        modules.getItems().clear();
        moduleTitle.clear();
        learningGoal.clear();
        learningGoals.getItems().clear();
        moduleSpecifications.setHtmlText(
                        "<html><head></head><body contenteditable=\"true\">"
                        + "</body></html>");
       existingModules = lookupModuleBeanRemote()
                .getAllModules(Controller.getUser().getCourseID());
        for (ModuleDetails module : existingModules) {
            Label label = new Label();
            label.setText(module.getName());
            modules.getItems().add(label);
        }
    }

    public void autoSave() {
        ModuleDetails module = existingModules.get(
                modules.getItems().indexOf(
                        modules.getSelectionModel()
                                .getSelectedItem()));

        module.setName(moduleTitle.getText());
        existingModules.set(existingModules.indexOf(module), module);
    }

    public void openSelectedModule() {
        moduleTitle.clear();
        int index = modules.getSelectionModel().getSelectedIndex();
        ModuleDetails module = existingModules.get(index);
        moduleTitle.setText(module.getName());
        moduleSpecifications.setHtmlText(module.getDescription());
        //Moduletype
        for (String i : module.getLearningGoals()) {
            learningGoals.getItems().add(i);
        }
    }

    // check if module name is <Ny modul> DO NOT CREATE IT.
    public void saveModuleButton() {
        boolean ifExists = false;
        for (Label tittel : modules.getItems()) {
            if (tittel.getText().equalsIgnoreCase(moduleTitle.getText())
                    || moduleTitle.getText().equalsIgnoreCase("<Ny modul>")) {
                Alert alertBox = new Alert(Alert.AlertType.ERROR);
                alertBox.setTitle("FEIL");
                alertBox.setHeaderText("Ugyldig navn");
                alertBox.setContentText("Skriv inn et navn som ikke finnes fra før");
                alertBox.showAndWait();
                ifExists = true;
                break;
            }
        }
        if (!ifExists) {
            for (String i : learningGoals.getItems()) {
                learningGoalsList.add(i);
            }
            ModuleDetails saveHighlightedModule = new ModuleDetails();
            saveHighlightedModule.setCourseID(Controller.getUser().getCourseID());
            saveHighlightedModule.setName(moduleTitle.getText());
            saveHighlightedModule.setDescription(moduleSpecifications.getHtmlText());
            saveHighlightedModule.setModuleType("Modul type");
            lookupModuleBeanRemote().saveModule(saveHighlightedModule, learningGoalsList);
        }
    }

    public void removeModuleButton() {
        int index = modules.getSelectionModel().getSelectedIndex();
        ModuleDetails module = existingModules.get(index);
        lookupModuleBeanRemote().removeModule(module);
    }

    public void addLearningGoalButton() {
        learningGoals.getItems().add(learningGoal.getText());
    }
    
    public void removeLearningGoalButton() {
        int index = learningGoals.getSelectionModel().getSelectedIndex();
        learningGoals.getItems().remove(index);
    }

    private ModuleRemote lookupModuleBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ModuleRemote) c.lookup("java:comp/env/ModuleBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    

}
