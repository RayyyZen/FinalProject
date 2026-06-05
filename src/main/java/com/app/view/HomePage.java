package com.app.view;

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

        Button go = new Button("Go Graph");
        go.setOnAction(e -> {
            controller.showSimulationName();
        });
        go.getStyleClass().add("primary-button");

        Button saves = new Button("Go Saves");
        saves.setOnAction(e -> controller.showFilesPage());
        saves.getStyleClass().add("primary-button");

        getChildren().addAll(imageView, go, saves);
    }
}