package com.app;

import com.app.controller.MainController;
import com.app.model.simulation.Simulation;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Simulation simulation = null;
        MainController controller = new MainController(simulation);

        Scene scene = new Scene(controller.getRoot());
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Récupère les dimensions de l'écran principal
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        stage.setMaximized(true); // maximisé sans cacher la barre des tâches

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
