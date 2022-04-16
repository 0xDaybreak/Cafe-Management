package com.controller;

import com.misc.AlertBox;
import com.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label loginLbl;
    @FXML
    private Label signupLbl;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button signInBtn = new Button();
    @FXML
    private ToggleButton lightBtn = new ToggleButton();
    @FXML
    private Polyline lightCast = new Polyline();
    @FXML
    private ImageView cfSmoke = new ImageView();
    @FXML
    private ToggleButton cfBtn = new ToggleButton();
    @FXML
    private Button close = new Button();
    @FXML
    private Label currentStageLbl = new Label();

    UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        swapLoginSignup();
        turnOnCoffeeSmoke();
        turnLightOnOff();
        login();
        close();
    }

    private void close() {
        close.setOnAction(e-> Platform.exit());
    }

    //method called when swapping to sign up scene
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

    private void playAudio() {
        Media note = new Media(new File("src/main/resources/sound/switch.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(note);
        mediaPlayer.play();
    }

    private void turnLightOnOff() {
        lightBtn.selectedProperty().addListener((observableValue, aBoolean, t1) -> {

            if(t1){
                playAudio();
                lightCast.setOpacity(0.6);

            }
            else {
                playAudio();
                lightCast.setOpacity(0);
            }

        });
    }

    private void turnOnCoffeeSmoke() {
        Image image = new Image(new File("src/main/resources/images/smoke.gif").toURI().toString());
        cfSmoke.setImage(image);
        cfBtn.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1) {
                cfSmoke.setOpacity(0.5);
            }
            else {
                cfSmoke.setOpacity(0);
            }
        });
    }

    //checks if log in button was pressed
    private void login() {
        signInBtn.setOnMouseClicked(e -> checklogin());
    }

    //checks if log in info is correct
    private void checklogin() {
        userService = new UserService();

        try {
            if((userService.findUser(tfUsername.getText(), tfPassword.getText()).getUsername() + userService.findUser(tfUsername.getText(), tfPassword.getText()).getPassword())
                    .equals(tfUsername.getText() + tfPassword.getText()))
            {
                System.out.print("Successfully logged in");
                //create a new stage, scene and open the home window
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("home.fxml"));
                Scene scene = new Scene(fxmlLoader.load(),1140,720);
                Stage stage = new Stage();
                stage.setTitle("Premium Coffee Shop");
                stage.setScene(scene);
                stage.show();

                //get the current Scene using a lable then close the current scene
                Stage currentStage;
                currentStage = (Stage) currentStageLbl.getScene().getWindow();
                currentStage.close();
            }
        }
        catch (Exception e) {
            AlertBox.display("Error",e.getMessage());
            e.printStackTrace();
        }
    }
}
