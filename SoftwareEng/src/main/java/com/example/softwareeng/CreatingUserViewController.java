package com.example.softwareeng;

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
CreateTaskDB db = new CreateTaskDB();
    void AddingUsersToChoiceBox(){
        ArrayList<Users> users = new ArrayList<>(db.GetUsers());
        for (int i = 0; i < users.size(); i++) {
            Users newUser = users.get(i);
            choiceBox.setValue(users.get(0).getUserName());
            choiceBox.getItems().add(newUser.getUserName());
        }
    }
    @FXML
    void addingUsersToDatabase(){
        System.out.println("Inserting ran");
        String name = userNameField.getText();
        db.InsertingUsers(groupIDforUserIDInsert,name);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddingUsersToChoiceBox();
    }
}
