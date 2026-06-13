package com.app;

import com.app.controller.MainController;
import com.app.model.simulation.Simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Simulation simulation = null;
        MainController controller = new MainController(simulation);

        Scene scene = new Scene(controller.getRoot());
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Récupère les dimensions de l'écran principal

        stage.setFullScreen(true);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
