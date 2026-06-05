package com.app.view;

import com.app.controller.MainController;
import com.app.model.simulation.Simulation;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SimulationNamePage extends BorderPane {
    
    Simulation simulation;

    public SimulationNamePage(MainController controller, Simulation simulation) {
        this.simulation = simulation;

        VBox vbox = new VBox();

        TextField nameField = new TextField();

        Button go = new Button("Go Graph");
        go.setOnAction(e -> {
            String name = nameField.getText();
            if(name != null && !name.isEmpty()){
                this.simulation = new Simulation(name);
                controller.setSimulation(this.simulation);
                controller.showGraph();
            }
        });
        go.getStyleClass().add("primary-button");

        Button back = new Button("Back");
        back.setOnAction(e -> controller.showHome());

        vbox.getChildren().addAll(back, nameField, go);

        setCenter(vbox);
    }
}