package com.controller;

import com.entity.UserEntity;
import com.misc.Singleton;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    @FXML
    private VBox vbIcons;
    @FXML
    private Label wbLabel;
    @FXML
    private Label userLabel;

    @Override
    public void initialize(URL url, ResourceBundle rbl) {

        handleMenuPress();
        menuSwapAction();
        greetUser();
        setUserLabel();
    }

    private void handleMenuPress() {

        menubutton.selectedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (t1) {
                slideout();
            } else {
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
        vbIcons.setEffect(null);

    }

    private void slideout() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToX(-200);
        slide.play();
        vbIcons.setEffect(new Bloom(0.88));
    }

    private void menuSwapAction() {
        //add action listener to the buttons of the menu
        usersBtn.setOnAction(e -> userScreen());
        usersBtnText.setOnAction(e -> userScreen());

        ordersBtn.setOnAction(e -> ordersScreen());
        ordersBtnText.setOnAction(e -> ordersScreen());

        coffeeBtn.setOnAction(e -> coffeeScreen());
        coffeeBtnText.setOnAction(e -> coffeeScreen());
    }

    //create user scene by calling userScreen.fxml
    private void userScreen() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("userScreen.fxml")));
            contentScene.getChildren().removeAll();
            contentScene.getChildren().setAll(fxml);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void ordersScreen() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ordersScreen.fxml")));
            contentScene.getChildren().removeAll();
            contentScene.getChildren().setAll(fxml);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void coffeeScreen() {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("coffeeScreen.fxml")));
            contentScene.getChildren().removeAll();
            contentScene.getChildren().setAll(fxml);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void greetUser() {
        UserEntity userEntity = new UserEntity();
        Singleton user = Singleton.getInstance(userEntity);
        wbLabel.setText("Welcome back " + user.getUser().getUsername() + " we missed you :(");
    }

    private void setUserLabel() {
        UserEntity userEntity = new UserEntity();
        Singleton user = Singleton.getInstance(userEntity);
        userLabel.setText("Logged in as " + user.getUser().getUsername());
    }

}
