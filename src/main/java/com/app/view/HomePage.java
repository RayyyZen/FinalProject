package com.app.view;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.util.*;

import com.app.model.*;

public class HomePage extends VBox {

    private MainController controller;
    private Simulation simulation;

    public HomePage(MainController controller, Simulation simulation) {

        this.controller = controller;
        this.simulation = simulation;

        Button go = new Button("Go Graph");

        //go.setOnAction(e -> controller.showGraph());

        getChildren().add(go);
    }
}