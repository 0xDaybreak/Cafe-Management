package com.controller;

import com.entity.UserEntity;
import com.misc.AlertBox;
import com.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TableView<UserEntity> tvUsers;
    @FXML
    private Button insertBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfCity;

    UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTable();
        insertUser();
        deleteBtn.setOnMouseClicked(e->System.out.println(tvUsers.getSelectionModel().getSelectedItem()));
        deleteUser();

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

    private void refreshTable() {
        //update list after insertion
        ObservableList<UserEntity> userList = FXCollections.observableList(userService.getAllUsers());
        //set new user in table
        tvUsers.setItems(userList);
    }

    private void insertUser() {

        insertBtn.setOnMouseClicked(e -> {
            if (tfUsername.getText().isEmpty() || tfPassword.getText().isEmpty() || tfEmail.getText().isEmpty() || tfCity.getText().isEmpty()) {
                AlertBox.display("Error", "Please fill in all the fields");
            } else {
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(tfUsername.getText());
                userEntity.setPassword(tfPassword.getText());
                userEntity.setEmail(tfEmail.getText());
                userEntity.setCity(tfCity.getText());
                userService.addUser(userEntity);
                AlertBox.display("Success", "User inserted successfully");
                refreshTable();
            }
        });
    }

    private void deleteUser() {
            deleteBtn.setOnMouseClicked(e -> {
                if(tvUsers.getSelectionModel().getSelectedItem() != null) {
                    userService.removeUser(tvUsers.getSelectionModel().getSelectedItem(),
                            tvUsers.getSelectionModel().getSelectedItem().getUserId());
                    refreshTable();
                }
                else {
                    AlertBox.display("Error", "Please select a user before trying to delete");
                }
            });
        }
}

