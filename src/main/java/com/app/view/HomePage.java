package com.app.view;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import com.app.controller.MainController;

public class HomePage extends VBox {

    public HomePage(MainController controller) {

        this.getStyleClass().add("home-page");

        Button go = new Button("Go Graph");

        go.setOnAction(e -> controller.showGraph());
        go.getStyleClass().add("primary-button");

        getChildren().add(go);
    }
}