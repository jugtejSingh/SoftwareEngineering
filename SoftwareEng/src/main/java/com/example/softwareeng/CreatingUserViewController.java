package com.example.softwareeng;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreatingUserViewController implements Initializable {
    @FXML
    ChoiceBox choiceBox;
    @FXML
    TextField userNameField;
    static int groupIDforUserIDInsert;
    String choice = "";
    String choiceInAddingToChoiceBox = "";
CreateTaskDB db = new CreateTaskDB();
    void AddingUsersToChoiceBox(){
        choiceBox.getItems().clear();
        ArrayList<Users> users = new ArrayList<>(db.GetUsers());
        for (int i = 0; i < users.size(); i++) {
            Users newUser = users.get(i);
            choiceBox.setValue(users.get(0).getUserName());
            choiceBox.getItems().add(newUser.getUserName());
        }
        if(users.size() == 0) {
            System.out.println("The size is 0");
        }else{
            choiceInAddingToChoiceBox = users.get(0).getUserName();
            choice = users.get(0).getUserName();
        }
    }
    @FXML
    void addingUsersToDatabase(){
        if (Pattern.matches("[a-zA-Z ]+",userNameField.getText())) {
            db.InsertingUsers(groupIDforUserIDInsert,userNameField.getText());
            ArrayList<Integer> taskIDList = db.GetTaskIDs(groupIDforUserIDInsert);
            int userID = db.GettingUserIds(userNameField.getText());
            for (int i = 0; i < taskIDList.size();i++){
            db.InsertIntoTaskAndUsers(userID,groupIDforUserIDInsert,0,taskIDList.get(i));
            }
            AddingUsersToChoiceBox();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"The user has been added", ButtonType.CLOSE);
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "The value you added is not alphabetical.", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    @FXML
    void removingUsersFromDatabase() {
        if (choice.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You haven't selected anyone or there are no users", ButtonType.CLOSE);
            alert.showAndWait();
        } else {
            db.DeleteUser(choice);
            AddingUsersToChoiceBox();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"The user has been deleted", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddingUsersToChoiceBox();
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println(choiceBox.getItems().size());
                System.out.println("Size of items");
                if((int)t1 == -1) {
                    choice = choiceInAddingToChoiceBox;
                }else{
                    choice = (String) choiceBox.getItems().get((Integer) t1);
                }
            }});
    }
}
