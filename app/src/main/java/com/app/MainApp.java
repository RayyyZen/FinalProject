package com.app;

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
import com.app.view.*;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Simulation simulation = new Simulation();

        MainController controller = new MainController(simulation);

        Scene scene = new Scene(controller.getRoot(), 1000, 700);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}