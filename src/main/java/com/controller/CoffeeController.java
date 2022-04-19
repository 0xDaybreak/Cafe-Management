package com.controller;

import com.domain.Type;
import com.entity.CoffeeEntity;
import com.entity.OrdersEntity;
import com.entity.UserEntity;
import com.misc.Singleton;
import com.service.OrdersService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Timestamp;
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

    OrdersEntity ordersEntity = new OrdersEntity();
    CoffeeEntity coffeeEntity = new CoffeeEntity();
    OrdersService ordersService = new OrdersService();

    //price list of the individual items that are clickable on this scene
    double[] pricelist = {2.4, 3.4, 4.4, 1, 2, 3, 0.25, 0.5, 0.75, 1.75};

    //ArrayList that contain the images of clickable objects
    List<ImageView> imageViewList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Collections.addAll(imageViewList, espresso, latte, iced, smallC, mediumC, largeC);
        selectCoffee();
        handleSliderEvent();
        handleChecboxEvent();
        handleBuyButtonPress();
        handlePlusMinusBtn();
    }

    //check and restrict the current quantity of coffees whcich can be purchased at a time
    private void quantityIncrease(int[] quantity) {
            quantity[0] = Integer.parseInt((qtyLabel.getText())) + 1;
            qtyLabel.setText(String.valueOf(quantity[0]));
            minusBtn.setDisable(false);
            if(quantity[0]==9) {
                plusBtn.setDisable(true);
            }

    }
    private void quantityDecrease(int[] quantity) {
            quantity[0] = Integer.parseInt(qtyLabel.getText()) - 1;
            qtyLabel.setText(String.valueOf(quantity[0]));
            plusBtn.setDisable(false);
            if(quantity[0]==1) {
                minusBtn.setDisable(true);
            }
    }

    private void handlePlusMinusBtn() {
        final int[] quantity = new int[1];
        minusBtn.setDisable(true);
        plusBtn.setOnMouseClicked(e-> quantityIncrease(quantity));
        minusBtn.setOnMouseClicked(e-> quantityDecrease(quantity));
    }


    private void selectCoffee() {
        espresso.setOnMouseClicked(e -> {
            coffeeEntity.setType(Type.ESPRESSO);
            handleImagePress(espresso, latte, iced);
        });
        latte.setOnMouseClicked(e -> handleImagePress(latte, espresso, iced));
        iced.setOnMouseClicked(e -> handleImagePress(iced, latte, espresso));

        smallC.setOnMouseClicked(e -> handleImagePress(smallC, mediumC, largeC));
        mediumC.setOnMouseClicked(e -> handleImagePress(mediumC, smallC, largeC));
        largeC.setOnMouseClicked(e -> handleImagePress(largeC, mediumC, smallC));
    }

    //checks the current status of the slider and returns the corresponding value from the pricelist
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

    //checks if the checkbox is clicked or not and returns the corresponding price from the pricelist
    private double checkCafDecaf() {
        if (decafCb.isSelected())
            return pricelist[9];
        return 0;
    }

    private void handleChecboxEvent() {
        decafCb.setOnMouseClicked(e -> price.setText(calculateCoffeePrice() + "$"));
    }

    //method which gets called everytime someone clicks any image contained in imageViewList
    private double calculateCoffeePrice() {
        double sum = 0;
        StringBuilder s = new StringBuilder();
        for (ImageView iterate : imageViewList) {
            if (iterate.getOpacity() == 1) {
                s.append(iterate.getId());
            }
        }
        for (int j = 0; j < s.length(); j++) {
            sum = sum + pricelist[Integer.parseInt(String.valueOf((Character.getNumericValue(s.charAt(j)))))];
        }
        ordersEntity.setPrice(sum + checkSliderStatus() + checkCafDecaf());
        return ordersEntity.getPrice();
    }

    //method called in initialize; Whenever the "buy button" is pressed it assigns multiple values to the orderEntity
    //to do: assign user id of current logged in user
    private void handleBuyButtonPress() {
        buyBtn.setOnMouseClicked(e-> {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(truncToSec(date).getTime());
            ordersEntity.setName("Order" + ordersEntity.getOrdersId());
            ordersEntity.setQuantity(Integer.parseInt(qtyLabel.getText()));
            ordersEntity.setPrice(calculateCoffeePrice());
            ordersEntity.setDate(timestamp);
            ordersEntity.setUserId(getUserId());
            System.out.println(ordersEntity.toString());
            System.out.println(coffeeEntity.getType());
        });
    }

    private int getUserId() {
        UserEntity userEntity = new UserEntity();
        Singleton user = Singleton.getInstance(userEntity);
        return user.getUser().getUserId();
    }

    //truncates the time stamp's miliseconds
    public Date truncToSec(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
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
