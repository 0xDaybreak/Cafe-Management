package com.controller;

import com.entity.OrdersEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;

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
    @FXML
    private Button buyBtn;
    @FXML
    private Button plusBtn;
    @FXML
    private Button minusBtn;
    @FXML
    private Label qtyLabel;
    @FXML
    private Label price;
    @FXML
    private Slider slider;
    @FXML
    private CheckBox decafCb;

    double sum = 0;
    OrdersEntity ordersEntity = new OrdersEntity();
    double[] pricelist = {2.4, 3.4, 4.4, 1, 2, 3, 0.25, 0.5, 0.75, 1.75};

    List<ImageView> imageViewList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Collections.addAll(imageViewList, espresso, latte, iced, smallC, mediumC, largeC);
        selectCoffee();
        handleSliderEvent();
        handleChecboxEvent();
        quantityIncreaseDecrease();
    }

    private void quantityIncreaseDecrease() {
        final int[] value = new int[1];
            plusBtn.setOnMouseClicked(e -> {
                    if(value[0]<9) {
                        value[0] = Integer.parseInt((qtyLabel.getText())) + 1;
                        qtyLabel.setText(String.valueOf(value[0]));
                    }
            });
            minusBtn.setOnMouseClicked(e -> {
                    if(value[0]>1) {
                        value[0] = Integer.parseInt(qtyLabel.getText()) - 1;
                        qtyLabel.setText(String.valueOf(value[0]));
                    }
            });
        }

    private void selectCoffee() {
        espresso.setOnMouseClicked(e -> handleImagePress(espresso, latte, iced));
        latte.setOnMouseClicked(e -> handleImagePress(latte, espresso, iced));
        iced.setOnMouseClicked(e -> handleImagePress(iced, latte, espresso));

        smallC.setOnMouseClicked(e -> handleImagePress(smallC, mediumC, largeC));
        mediumC.setOnMouseClicked(e -> handleImagePress(mediumC, smallC, largeC));
        largeC.setOnMouseClicked(e -> handleImagePress(largeC, mediumC, smallC));
    }

    private double checkSliderStatus() {
        return switch ((int) slider.getValue()) {
            case (0) -> pricelist[6];
            case (33) -> pricelist[7];
            case (66) -> pricelist[8];
            default -> 0;
        };
    }

    private void handleSliderEvent() {
        slider.setOnMouseClicked(e -> price.setText(calculateCoffeePrice() + "$"));
    }

    private double checkCafDecaf() {
        if (decafCb.isSelected())
            return pricelist[9];
        return 0;
    }

    private void handleChecboxEvent() {
        decafCb.setOnMouseClicked(e -> price.setText(calculateCoffeePrice() + "$"));
    }

    private double calculateCoffeePrice() {
        sum = 0;
        String s = "";
        for (ImageView iterate : imageViewList) {
            if (iterate.getOpacity() == 1) {
                s = s + iterate.getId();
            }
        }
        for (int j = 0; j < s.length(); j++) {
            sum = sum + pricelist[Integer.parseInt(String.valueOf((Character.getNumericValue(s.charAt(j)))))];
        }
        ordersEntity.setPrice(sum + checkSliderStatus() + checkCafDecaf());
        return ordersEntity.getPrice();
    }


    private void handleImagePress(ImageView first, ImageView second, ImageView last) {
        first.setOnMouseClicked(e -> {
            if (first.opacityProperty().doubleValue() == 0.5 && second.opacityProperty().doubleValue() == 0.5 && last.opacityProperty().doubleValue() == 0.5) {
                first.setOpacity(1);
                price.setText(calculateCoffeePrice() + "$");
            } else if (first.opacityProperty().doubleValue() == 1 && second.opacityProperty().doubleValue() == 0.5 && last.opacityProperty().doubleValue() == 0.5) {
                first.setOpacity(0.5);
                price.setText(calculateCoffeePrice() + "$");

            }
        });
    }
}
