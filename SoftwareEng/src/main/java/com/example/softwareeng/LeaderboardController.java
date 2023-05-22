package com.example.softwareeng;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class    LeaderboardController implements Initializable {

    @FXML
    VBox VboxLeaderboard;
    Button button;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    VboxLeaderboard.getChildren().add(button); //telling vbox to get entire list and add wahtever is in add (button).
    }
}
