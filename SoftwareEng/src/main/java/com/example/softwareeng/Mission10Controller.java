package com.example.softwareeng;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Mission10Controller implements Initializable {
    @FXML
    PieChart pieChartForUsers;
    @FXML
    BarChart<String,Number> barchartForRanks;

    ArrayList<Users> userListWithCount;
    ArrayList<Ranks> ranksArrayList;
    Mission10DB M10DB = new Mission10DB();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            userListWithCount= M10DB.getListOfUsersInTempTask();
            ranksArrayList = M10DB.getRanksForMission10();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                        //new PieChart.Data("Grapefruit", 13),;

        for (int i = 0; i < userListWithCount.size();i++){
            String name = userListWithCount.get(i).getUserName();
            int value = userListWithCount.get(i).getTempTaskCount();
            pieChartData.add(new PieChart.Data(name,value));
            System.out.println(name);
            System.out.println(value);
        }
        pieChartForUsers.setData(pieChartData);

        XYChart.Series<String,Number> seriesOfRanks = new XYChart.Series<>();
        seriesOfRanks.setName("Rank BarChart");
        for (int i = 0; i < ranksArrayList.size();i++){
            seriesOfRanks.getData().add(new XYChart.Data<>(ranksArrayList.get(i).getName()+ " #"+ranksArrayList.get(i).getRank(),ranksArrayList.get(i).getPoints()));
        }
        barchartForRanks.getData().add(seriesOfRanks);
    }
}
