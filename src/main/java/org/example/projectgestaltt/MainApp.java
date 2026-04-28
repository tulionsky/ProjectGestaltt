package org.example.projectgestaltt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                MainApp.class.getResource("/org/example/projectgestaltt/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 700);
        scene.getStylesheets().add(
                MainApp.class.getResource("/org/example/projectgestaltt/style.css").toExternalForm());
        stage.setTitle("ProjectGestalt — Mini Compilador");
        Image icono = new Image(
                MainApp.class.getResourceAsStream("/org/example/projectgestaltt/icon.png"));
        stage.getIcons().add(icono);
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}