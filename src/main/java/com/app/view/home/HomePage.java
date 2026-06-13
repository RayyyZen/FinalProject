package com.app.view.home;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.app.controller.MainController;

public class HomePage extends VBox {

    public HomePage(MainController controller) {

        this.getStyleClass().add("home-page");

        Image img = new Image(getClass().getResourceAsStream("/logo.png"));

        ImageView imageView = new ImageView(img);

        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);

        Button newSimulation = new Button("New Simulation");
        newSimulation.setOnAction(e -> {
            controller.showSimulationName();
        });

        newSimulation.getStyleClass().add("primary-button");

        Button randomSimulation = new Button("Random Simulation");
        randomSimulation.setOnAction(e -> {
            controller.showRandomSimulationName();
        });

        randomSimulation.getStyleClass().add("primary-button");

        Button saves = new Button("See Saves");
        saves.setOnAction(e -> controller.showFilesPage());
        saves.getStyleClass().add("primary-button");

        getChildren().addAll(imageView, newSimulation, randomSimulation, saves);
    }
}