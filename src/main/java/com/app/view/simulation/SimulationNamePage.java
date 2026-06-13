package com.app.view.simulation;

import com.app.controller.MainController;
import com.app.model.graph.Graph;
import com.app.model.simulation.Simulation;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SimulationNamePage extends BorderPane {
    
    Simulation simulation;

    public SimulationNamePage(MainController controller, Simulation simulation, Graph graph) {
        this.simulation = simulation;

        Button back = new Button("Back");
        back.setOnAction(e -> controller.showHome());

        back.getStyleClass().add("primary-button");

        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(15));
        topBar.setLeft(back);
        setTop(topBar);

        VBox vbox = new VBox(20);

        Label title = new Label("Name your simulation:");


        TextField nameField = new TextField();
        nameField.setMaxWidth(250);

        Button start = new Button("Start");
        start.setOnAction(e -> {
            String name = nameField.getText();
            if(name != null && !name.isEmpty()){
                this.simulation = new Simulation(name, graph);
                controller.setSimulation(this.simulation);
                controller.showGraph();
            }
        });
        start.getStyleClass().add("primary-button");

        vbox.getChildren().addAll(title, nameField, start);


        vbox.setPadding(new Insets(15));

        setCenter(vbox);
        vbox.setAlignment(Pos.CENTER);

    }

    public SimulationNamePage(MainController controller, Simulation simulation){
        this(controller, simulation, new Graph());
    }
}