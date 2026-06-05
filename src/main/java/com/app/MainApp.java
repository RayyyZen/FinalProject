package com.app;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;

import com.app.controller.MainController;
import com.app.model.simulation.Simulation;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Simulation simulation = null;

        MainController controller = new MainController(simulation);

        Scene scene = new Scene(controller.getRoot(), 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}