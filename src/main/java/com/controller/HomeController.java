package com.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ToggleButton menubutton;
    @FXML
    private AnchorPane slider;
    @FXML
    private StackPane contentScene = new StackPane();
    @FXML
    private Button usersBtn;
    @FXML
    private Button usersBtnText;
    @FXML
    private Button ordersBtn;
    @FXML
    private Button ordersBtnText;
    @FXML
    private Button coffeeBtn;
    @FXML
    private Button coffeeBtnText;

    @Override
    public void initialize(URL url, ResourceBundle rbl) {

        handleMenuPress();
        menuSwapAction();
    }
    private void handleMenuPress() {

        menubutton.selectedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if(t1) {
                slideout();
            }
            else {
                slidein();
            }
        }));
    }

    private void slidein() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToX(0);
        slide.play();
    }

    private void slideout() {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(-200);
            slide.play();
    }

    private void menuSwapAction() {
        //add action listener to the buttons of the menu
        usersBtn.setOnAction(e-> userScreen());
        usersBtnText.setOnAction(e-> userScreen());

        ordersBtn.setOnAction(e-> ordersScreen());
        ordersBtnText.setOnAction(e-> ordersScreen());

        coffeeBtn.setOnAction(e-> coffeeScreen());
        coffeeBtnText.setOnAction(e-> coffeeScreen());
    }

    //create user scene by calling userScreen.fxml
    private void userScreen() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("userScreen.fxml")));
            contentScene.getChildren().removeAll();
            contentScene.getChildren().setAll(fxml);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void ordersScreen() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ordersScreen.fxml")));
            contentScene.getChildren().removeAll();
            contentScene.getChildren().setAll(fxml);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void coffeeScreen() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("coffeeScreen.fxml")));
            contentScene.getChildren().removeAll();
            contentScene.getChildren().setAll(fxml);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
