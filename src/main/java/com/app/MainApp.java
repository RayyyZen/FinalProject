package com.app;

import javafx.application.*;
import javafx.geometry.Rectangle2D;
import javafx.stage.*;
import javafx.scene.*;

import com.app.controller.MainController;
import com.app.model.simulation.Simulation;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Simulation simulation = null;

        MainController controller = new MainController(simulation);

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();

        Scene scene = new Scene(controller.getRoot(), screen.getWidth(), screen.getHeight());
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setX(screen.getMinX());
        stage.setY(screen.getMinY());
        stage.setWidth(screen.getWidth());
        stage.setHeight(screen.getHeight());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}