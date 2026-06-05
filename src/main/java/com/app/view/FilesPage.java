package com.app.view;

import java.util.List;

import com.app.controller.MainController;
import com.app.file.SaveLoadManager;
import com.app.model.exception.AppException;
import com.app.model.simulation.Simulation;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilesPage extends BorderPane {

    private final MainController controller;

    private Simulation simulation;

    /**
     * Creates the node details page.
     * @param controller the main controller used for navigation
     * @param node the selected node
     */
    public FilesPage(MainController controller, Simulation simulation) {
        this.controller = controller;
        this.simulation = simulation;
        buildPage();
    }

    /**
     * Builds the page layout.
     */
    private void buildPage() {
        String[] filesName = SaveLoadManager.getFilesName();

        Button back = new Button("Back");
        back.setOnAction(e -> controller.showHome());

        if(filesName == null){
            BorderPane pane = new BorderPane();
            Label label = new Label("No text found");
            pane.setCenter(new VBox(back,label));
            setCenter(pane);
        }
        else{
            VBox pane = new VBox();
            pane.getChildren().add(back);
            for(String fileName : filesName){
                Button button = new Button(fileName);
                button.setOnAction(e -> {
                    try{
                        simulation = SaveLoadManager.restoreFromFile(fileName);
                        controller.setSimulation(simulation);
                        controller.showGraph();
                    } catch(Exception ex) {

                    }
                });
                pane.getChildren().addAll(button);
            }
            setCenter(pane);
        }
    }
}
