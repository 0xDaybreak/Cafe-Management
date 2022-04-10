package com.controller;

import com.entity.UserEntity;
import com.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    TableView<UserEntity> tvUsers;

    UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTable();
    }

    private void createTable() {
        userService = new UserService();
        ObservableList<UserEntity> userList = FXCollections.observableList(userService.getAllUsers());

        TableColumn<UserEntity, Integer> tcID = new TableColumn<>();
        tcID.setText("ID");
        tcID.setMinWidth(100);
        tcID.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<UserEntity, String> tcUserName = new TableColumn<>();
        tcUserName.setText("Username");
        tcUserName.setMinWidth(100);
        tcUserName.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UserEntity, String> tcEmail = new TableColumn<>();
        tcEmail.setText("Email");
        tcEmail.setMinWidth(100);
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<UserEntity, String> tcCity = new TableColumn<>();
        tcCity.setText("City");
        tcCity.setMinWidth(100);
        tcCity.setCellValueFactory(new PropertyValueFactory<>("city"));


        tvUsers.getColumns().addAll(tcID, tcUserName, tcEmail, tcCity);
        tvUsers.setItems(userList);

    }
}
