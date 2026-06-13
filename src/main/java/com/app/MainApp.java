package com.app;

import com.app.controller.MainController;
import com.app.model.simulation.Simulation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main application class
 * @version 3.0
 * @since 1.0
 * @author Rayane, Alexis, Remis
 */
public class MainApp extends Application {

    /**
     * The main app constructor
     */
    public MainApp(){}

    /**
     * The start method of the application
     * @param stage The main stage
     */
    @Override
    public void start(Stage stage) {
        Simulation simulation = null;
        MainController controller = new MainController(simulation);

        Scene scene = new Scene(controller.getRoot());
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setFullScreen(true);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method
     * @param args The arguments of the main method
     */
    public static void main(String[] args) {
        launch();
    }
}
