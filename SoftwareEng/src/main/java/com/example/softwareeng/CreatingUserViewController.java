package com.example.softwareeng;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreatingUserViewController implements Initializable {
    @FXML
ChoiceBox choiceBox;
    @FXML
    TextField userNameField;
    static int groupIDforUserIDInsert;

    String choice = "";
CreateTaskDB db = new CreateTaskDB();
    void AddingUsersToChoiceBox(){
        choiceBox.getItems().clear();
        ArrayList<Users> users = new ArrayList<>(db.GetUsers());
        for (int i = 0; i < users.size(); i++) {
            Users newUser = users.get(i);
            choiceBox.setValue(users.get(0).getUserName());
            choiceBox.getItems().add(newUser.getUserName());
        }
        choice = users.get(0).getUserName();
    }
    @FXML
    void addingUsersToDatabase(){
        System.out.println("Inserting ran");
        String name = userNameField.getText();
        db.InsertingUsers(groupIDforUserIDInsert,name);
        AddingUsersToChoiceBox();
    }
    @FXML
    void removingUsersFromDatabase(){
        db.DeleteUser(choice);
        AddingUsersToChoiceBox();
            }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddingUsersToChoiceBox();
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if((int)t1 <=0) {
                    t1 = 1;
                }
                choice = (String) choiceBox.getItems().get((Integer)t1);
            }});
    }
}
