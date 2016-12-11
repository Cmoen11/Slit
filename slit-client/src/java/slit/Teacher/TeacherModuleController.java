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
import javafx.scene.control.RadioButton;
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
    
    @FXML ListView<String> learningGoals;
    @FXML ListView<Label> modules;
    @FXML TextField moduleTitle;
    @FXML TextField learningGoal;
    @FXML HTMLEditor moduleSpecifications;
    @FXML RadioButton javaType;
    @FXML RadioButton writtenType;
    @FXML RadioButton bothType;
    
            
    ArrayList<ModuleDetails> existingModules;
    String moduleType;
    int index;
    ModuleDetails tempModule = new ModuleDetails();

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
        openSelectedModule();
    }

    public void initialize() {
        modules.getItems().clear();
        clearWindows();
        addModuleType();
        existingModules = lookupModuleBeanRemote()
                .getAllModules(Controller.getUser().getCourseID());
        for (ModuleDetails module : existingModules) {
            Label label = new Label();
            label.setText(module.getName());
            modules.getItems().add(label);
        }
        // Opens the module at position zero when called if no module is picked   
        /*if (!existingModules.isEmpty()) {
            modules.getSelectionModel().select(0); 
            openSelectedModule();
        }*/
    }

    public void autoSave() {
        if (modules.getItems().get(index).getText().equalsIgnoreCase("<Ny Modul>")) {
            tempModule.setName(moduleTitle.getText());
            tempModule.setDescription(moduleSpecifications.getHtmlText());
            tempModule.getLearningGoals().clear();
            tempModule.setModuleType(moduleType);
            for (String i : learningGoals.getItems()) {
                tempModule.getLearningGoals().add(i);
            }
        }
    }

    // Opens the selected module. If a new module is selected, it shows the
    // temporarily stored information of the newly created module.
    public void openSelectedModule() {
        autoSave();
        clearWindows();
        index = modules.getSelectionModel().getSelectedIndex();

        if (modules.getItems().get(index).getText().equalsIgnoreCase("<Ny Modul>")) {
            moduleTitle.setText(tempModule.getName());
            moduleSpecifications.setHtmlText(tempModule.getDescription());
            if (tempModule.getModuleType().equalsIgnoreCase("Java")) {
                javaType.setSelected(true);
            } else if (tempModule.getModuleType().equalsIgnoreCase("Skriftlig")) {
                writtenType.setSelected(true);
            } else if (tempModule.getModuleType().equalsIgnoreCase("Skriftlig og java")) {
                bothType.setSelected(true);
            }
            for (String i : tempModule.getLearningGoals()) {
                learningGoals.getItems().add(i);
            }

        } else if (index != -1) {
            ModuleDetails module = existingModules.get(index);
            moduleTitle.setText(module.getName());
            moduleSpecifications.setHtmlText(module.getDescription());
            if (module.getModuleType().equalsIgnoreCase("Java")) {
                javaType.setSelected(true);
            } else if (module.getModuleType().equalsIgnoreCase("Skriftlig")) {
                writtenType.setSelected(true);
            } else if (module.getModuleType().equalsIgnoreCase("Skriftlig og java")) {
                bothType.setSelected(true);
            }
            for (String i : module.getLearningGoals()) {
                learningGoals.getItems().add(i);
            }
        }
    }

    // check if module name is <Ny modul> DO NOT CREATE IT.
    public void saveModuleButton() {
        index = modules.getSelectionModel().getSelectedIndex();
        Boolean ifExists = false;
        if (moduleTitle.getText().equalsIgnoreCase("<Ny modul>")
                || existingModules.get(index).getName()
                .equalsIgnoreCase(moduleTitle.getText())) {
            Alert alertBox = new Alert(Alert.AlertType.ERROR);
            alertBox.setTitle("Navn konflikt");
            alertBox.setHeaderText("Ugyldig navn");
            alertBox.setContentText("Skriv inn et navn som ikke finnes fra før");
            alertBox.showAndWait();
            ifExists = true;
        }
        if (!ifExists) {
            learningGoalsList.clear();
            for (String i : learningGoals.getItems()) {
                learningGoalsList.add(i);
            }
            ModuleDetails saveHighlightedModule = new ModuleDetails();
            saveHighlightedModule.setCourseID(Controller.getUser().getCourseID());
            saveHighlightedModule.setName(moduleTitle.getText());
            saveHighlightedModule.setDescription(moduleSpecifications.getHtmlText());
            saveHighlightedModule.setModuleType(moduleType);
            lookupModuleBeanRemote().saveModule(saveHighlightedModule, learningGoalsList);
        }
        initialize();
    }

    public void removeModuleButton() {
        index = modules.getSelectionModel().getSelectedIndex();
        if (modules.getItems().get(index).getText().equalsIgnoreCase("<Ny Modul>")) {
            modules.getItems().remove(index);
        } else {
            ModuleDetails module = existingModules.get(index);
            lookupModuleBeanRemote().removeModule(module);
        }
        initialize();
    }

    public void addLearningGoalButton() {
        if (!learningGoal.getText().equalsIgnoreCase("")) {
            learningGoals.getItems().add(learningGoal.getText());
            learningGoal.clear();
            autoSave();
        }
    }

    public void removeLearningGoalButton() {
        int learningGoalIndex = learningGoals.getSelectionModel().getSelectedIndex();
        learningGoals.getItems().remove(learningGoalIndex);
        autoSave();
    }
    
    public void addModuleType() {
        if (javaType.isSelected()) {
         moduleType = javaType.getText();
        } else if (writtenType.isSelected()) {
            moduleType = writtenType.getText();
        } else if (bothType.isSelected()) {
            moduleType = "Skriftlig og java";
        }
    }

    public void clearWindows() {
        moduleTitle.clear();
        learningGoal.clear();
        learningGoals.getItems().clear();
        moduleSpecifications.setHtmlText(
                "<html><head></head><body contenteditable=\"true\">"
                + "</body></html>");
    }

    private ModuleRemote lookupModuleBeanRemote() {
        try {
            Context c = new InitialContext();
            return (ModuleRemote) c.lookup("java:global/slit-bean/ModuleBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
