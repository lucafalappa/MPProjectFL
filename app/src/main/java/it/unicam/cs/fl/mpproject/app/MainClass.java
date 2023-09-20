package it.unicam.cs.fl.mpproject.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Classe principale che consente di avviare l'interfaccia grafica per la
 * gestione dei robot
 */
public class MainClass extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull
                (getClass().getResource("/fxml/RobotSwarmGUI.fxml")));
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        primaryStage.setResizable(false);
        primaryStage.fullScreenProperty();
        primaryStage.setTitle("Robot Swarm - Falappa Luca");
        primaryStage.show();
    }

}
