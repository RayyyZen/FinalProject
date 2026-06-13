package com.app.view.file;

import com.app.controller.MainController;
import com.app.model.file.SaveLoadManager;
import com.app.model.simulation.Simulation;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * The files view class
 * @version 3.0
 * @since 2.0
 * @author Rayane, Alexis
 */
public class FilesPage extends BorderPane {

    /**
     * The main controller
     */
    private final MainController controller;

    /**
     * The simulation
     */
    private Simulation simulation;

    /**
     * Creates the node details page
     * @param controller the main controller used for navigation
     * @param simulation the simulation
     */
    public FilesPage(MainController controller, Simulation simulation) {
        this.controller = controller;
        this.simulation = simulation;
        buildPage();
    }

    /**
     * Builds the page layout
     */
    private void buildPage() {
        String[] filesName = SaveLoadManager.getFilesName();

        BorderPane topBar = new BorderPane();

        Button back = new Button("Back");
        back.setOnAction(e -> controller.showHome());
        back.setPadding(new Insets(15));
        back.getStyleClass().add("primary-button");
        topBar.setLeft(back);
        topBar.setPadding(new Insets(15));

        setTop(topBar);

        if(filesName == null){
            BorderPane pane = new BorderPane();
            Label label = new Label("No text found");
            pane.setCenter(new VBox(label));
            setCenter(pane);
        }
        else{
            VBox pane = new VBox(20);

            Label title = new Label("Select an existing graph:");
            pane.getChildren().add(title);

            for(String fileName : filesName){
                Button button = new Button(fileName);
                button.getStyleClass().add("primary-button");
                button.setOnAction(e -> {
                    try{
                        simulation = SaveLoadManager.restoreFromFile(fileName);
                        controller.setSimulation(simulation);
                        controller.showGraph();
                    } catch(Exception ex) {

                    }
                });
                pane.getChildren().add(button);
            }
            setCenter(pane);
            pane.setAlignment(Pos.CENTER);
        }
    }
}