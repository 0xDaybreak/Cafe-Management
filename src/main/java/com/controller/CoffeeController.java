package com.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CoffeeController implements Initializable {

    @FXML
    private ImageView espresso;
    @FXML
    private ImageView latte;
    @FXML
    private ImageView iced;
    @FXML
    private ImageView smallC;
    @FXML
    private ImageView mediumC;
    @FXML
    private ImageView largeC;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectCoffee();
    }

    private void selectCoffee() {
        espresso.setOnMouseClicked(e->checkOpacity(espresso, latte, iced));
        latte.setOnMouseClicked(e->checkOpacity(latte, espresso, iced));
        iced.setOnMouseClicked(e->checkOpacity(iced, latte, espresso));

        smallC.setOnMouseClicked(e-> checkOpacity(smallC,mediumC,largeC));
        mediumC.setOnMouseClicked(e-> checkOpacity(mediumC,smallC,largeC));
        largeC.setOnMouseClicked(e-> checkOpacity(largeC,mediumC,smallC));
    }

    private void checkOpacity(ImageView first, ImageView second, ImageView last) {
        first.setOnMouseClicked(e-> {
            if(first.opacityProperty().doubleValue() == 0.5 && second.opacityProperty().doubleValue() == 0.5 && last.opacityProperty().doubleValue() == 0.5)
                first.setOpacity(1);
            else if(first.opacityProperty().doubleValue() == 1 && second.opacityProperty().doubleValue() == 0.5 && last.opacityProperty().doubleValue() == 0.5) {
                first.setOpacity(0.5);
            }
        });
    }
}
