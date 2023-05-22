package com.example.softwareeng;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GroupTaskUserScreen extends Application {
        @Override
        public void start(Stage stage) throws IOException {
            ConnectionFile.Connection();
            FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("groupScreen-view.fxml"));
            String cssGroup = this.getClass().getResource("groupScreen-css.css").toExternalForm();
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(cssGroup);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        public static void main(String[] args) {
            launch();
        }
    }

