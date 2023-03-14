package com.example.softwareeng;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CreateTask extends Application{
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(com.example.softwareeng.HelloApplication.class.getResource("createTask-view.fxml"));
            String css = this.getClass().getResource("createTask.css").toExternalForm();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(css);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

        }

        public static void main(String[] args) {
            launch();
        }
    }

