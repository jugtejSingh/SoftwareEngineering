package com.example.softwareeng;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreatingGroupViewController implements Initializable {
    GroupTaskUserDB db = new GroupTaskUserDB();
    @FXML
    TextField textFieldForGroup;
    @FXML
    ChoiceBox choiceBox;
    String choice = "";
    String choiceInAddingToChoiceBox = "";

    void AddingGroupsToChoiceBox() {
            choiceBox.getItems().clear();
            ArrayList<Group> groupList = new ArrayList<>(db.GetGroups());
            for (int i = 0; i < groupList.size(); i++) {
                Group newGroup = groupList.get(i);
                choiceBox.setValue(groupList.get(0).getName());
                choiceBox.getItems().add(groupList.get(i).getName());
            }
        if(groupList.size() == 0) {
            System.out.println("The size is 0");
        }else{
            choiceInAddingToChoiceBox = groupList.get(0).getName();
            choice = groupList.get(0).getName();
        }
    }


    @FXML
    void AddingGroups() {
        if (Pattern.matches("[a-zA-Z ]+",textFieldForGroup.getText())) {
            db.InsertingGroups(textFieldForGroup.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"The group has been added", ButtonType.CLOSE);
            alert.showAndWait();
            AddingGroupsToChoiceBox();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "The value you added is not alphabetical.", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    @FXML
    void removingUsersFromDatabase() {
        if (choice.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You haven't selected any group or there are no groups", ButtonType.CLOSE);
            alert.showAndWait();
        } else {
        db.DeleteGroup(choice);
        AddingGroupsToChoiceBox();
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"The group has been deleted", ButtonType.CLOSE);
        alert.showAndWait();
    }}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddingGroupsToChoiceBox();
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if((int)t1 == -1) {
                    choice = choiceInAddingToChoiceBox;
                }else{
                    choice = (String) choiceBox.getItems().get((Integer) t1);
                }}});
    }
}
