package com.example.softwareeng;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreatingGroupViewController implements Initializable {
    GroupTaskUserDB db = new GroupTaskUserDB();

    @FXML
    ChoiceBox choiceBox;
    void AddingGroupsToChoiceBox(){
        ArrayList<Group> groupList = new ArrayList<>(db.GetGroups());
        for (int i = 0; i < groupList.size(); i++) {
            choiceBox.setValue(groupList.get(0).getName());
            choiceBox.getItems().add(groupList.get(i).getName());
        }
    }
    void GettingGroups(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    AddingGroupsToChoiceBox();
    }
}
