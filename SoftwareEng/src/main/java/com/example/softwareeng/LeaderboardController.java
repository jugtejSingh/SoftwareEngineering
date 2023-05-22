package com.example.softwareeng;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LeaderboardController extends Application implements Initializable {

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("leaderboardPage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Group Screen");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
