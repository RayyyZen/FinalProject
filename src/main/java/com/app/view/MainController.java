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

public class MainController {

    private Simulation simulation;
    private BorderPane root;

    public MainController(Simulation simulation) {
        this.simulation = simulation;
        this.root = new BorderPane();

        showHome();
    }

    public void showHome() {
        HomePage page = new HomePage(this, simulation);
        root.setCenter(page);
    }

    public void showGraph() {
        //GraphPage page = new GraphPage(this, simulation);
        //root.setCenter(page);
    }

    public Parent getRoot() {
        return root;
    }
}