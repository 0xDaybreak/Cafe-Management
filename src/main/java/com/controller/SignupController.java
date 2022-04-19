package com.controller;

import com.entity.UserEntity;
import com.misc.AlertBox;
import com.service.UserService;
import com.validator.EmailValidate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    @FXML
    private Label loginLbl;
    @FXML
    private Label signupLbl;
    @FXML
    private Button signUpBtn;
    @FXML
    private TextField tfsupUsername;
    @FXML
    private TextField tfsupPassword;
    @FXML
    private TextField tfsupEmail;
    @FXML
    private TextField tfsupCity;

    UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle rbl) {
        signup();
        swapLoginSignup();

    }
    private void changeSceneToSignUp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signupScreen.fxml"));
        Stage signupStage = (Stage) signupLbl.getScene().getWindow();
        signupStage.setScene(new Scene(fxmlLoader.load(), 1000, 574));
    }

    //method called when swapping to log in scene
    private void changeSceneToLogIn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginScreen.fxml"));
        Stage loginStage = (Stage) loginLbl.getScene().getWindow();
        loginStage.setScene(new Scene(fxmlLoader.load(),1000,574));
    }

    //swaps the style and scenes between the sign in and sign up text of the bottom left corner

    private void swapLoginSignup() {

        loginLbl.setOnMouseClicked(e -> {
            loginLbl.setUnderline(true);
            loginLbl.setOpacity(1);
            signupLbl.setOpacity(0.6);
            signupLbl.setUnderline(false);
            try {
                changeSceneToLogIn();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        signupLbl.setOnMouseClicked(e -> {
            signupLbl.setUnderline(true);
            signupLbl.setOpacity(1);
            loginLbl.setOpacity(0.6);
            loginLbl.setUnderline(false);
            try {
                changeSceneToSignUp();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void signup() {
        signUpBtn.setOnMouseClicked(e->checksignup());
    }

    private void checksignup() {
        userService = new UserService();
        if (tfsupUsername.getText().isEmpty()||tfsupPassword.getText().isEmpty()||tfsupEmail.getText().isEmpty()||tfsupCity.getText().isEmpty()) {
            AlertBox.display("Error","Please fill in all the fields");
        } else if (retrieveUsername(tfsupUsername.getText())) {
            AlertBox.display("Error", "Username already taken");
        }
        else if(retrieveEmail(tfsupEmail.getText())) {
            AlertBox.display("Error", "e-mail address already registered");
        }
        else if(!(EmailValidate.validate(tfsupEmail.getText()))) {
            AlertBox.display("Error", "Please enter a valid e-mail address");
        }
        else {
            createUser();
        }

    }

    private boolean retrieveUsername(String username) {
        for(UserEntity iterate : userService.getAllUsers()) {
            if(username.equals(iterate.getUsername())) {
                return true;
            }
        }
        return false;
    }

    private boolean retrieveEmail(String email) {
        for(UserEntity iterate : userService.getAllUsers()) {
            if(email.equals(iterate.getEmail())) {
                return true;
            }
        }
        return false;
    }

    private void createUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(tfsupUsername.getText());
        userEntity.setPassword(tfsupPassword.getText());
        userEntity.setEmail(tfsupEmail.getText());
        userEntity.setCity(tfsupCity.getText());
        userService.addUser(userEntity);
        AlertBox.display("Success","User created successfully you can log in now");
    }
}
