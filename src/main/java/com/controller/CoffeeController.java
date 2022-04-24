package com.controller;

import com.domain.Size;
import com.domain.Type;
import com.entity.CoffeeEntity;
import com.entity.OrdersEntity;
import com.entity.UserEntity;
import com.misc.AlertBox;
import com.misc.Singleton;
import com.service.OrdersService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import java.lang.reflect.Field;
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

    HashMap<ImageView, Type> types = new HashMap<>();
    HashMap<ImageView, Size> sizes = new HashMap<>();

    //ArrayList that contain the images of clickable objects
    List<ImageView> imageViewList = new ArrayList<>();

    //price list of the individual items that are clickable on this scene
    double[] pricelist = {2.4, 3.4, 4.4, 1, 2, 3, 0.25, 0.5, 0.75, 1.75};

    private void createHashMap() {
        types.put(espresso, Type.ESPRESSO);
        types.put(latte, Type.LATTE);
        types.put(iced, Type.ICED);
        sizes.put(smallC, Size.SMALL);
        sizes.put(mediumC, Size.MEDIUM);
        sizes.put(largeC, Size.LARGE);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Collections.addAll(imageViewList, espresso, latte, iced, smallC, mediumC, largeC);
        createHashMap();
        selectCoffee();
        handleSliderEvent();
        handleChecboxEvent();
        handleBuyButtonPress();
        handlePlusMinusBtn();
    }

    //check and restrict the current quantity of coffees which can be purchased at a time
    private void quantityIncrease(int[] quantity) {
        quantity[0] = Integer.parseInt((qtyLabel.getText())) + 1;
        qtyLabel.setText(String.valueOf(quantity[0]));
        minusBtn.setDisable(false);
        if (quantity[0] == 9) {
            plusBtn.setDisable(true);
        }

    }

    private void quantityDecrease(int[] quantity) {
        quantity[0] = Integer.parseInt(qtyLabel.getText()) - 1;
        qtyLabel.setText(String.valueOf(quantity[0]));
        plusBtn.setDisable(false);
        if (quantity[0] == 1) {
            minusBtn.setDisable(true);
        }
    }

    private void handlePlusMinusBtn() {
        final int[] quantity = new int[1];
        minusBtn.setDisable(true);
        plusBtn.setOnMouseClicked(e -> quantityIncrease(quantity));
        minusBtn.setOnMouseClicked(e -> quantityDecrease(quantity));
    }

    private void assignType(CoffeeEntity coffeeEntity) {
        List<ImageView> list = Arrays.asList(espresso, latte, iced);
        for (ImageView iterate : list) {
            if (iterate.getOpacity() == 1) {
                coffeeEntity.setType(types.get(iterate));
            }
        }
    }

    private void assignSize(CoffeeEntity coffeeEntity) {
        List<ImageView> list = Arrays.asList(smallC, mediumC, largeC);
        for (ImageView iterate : list) {
            if (iterate.getOpacity() == 1) {
                coffeeEntity.setSize(sizes.get(iterate));
            }
        }
    }

    private void assignSugar(CoffeeEntity coffeeEntity) {
        switch ((int) slider.getValue()) {
            case (0) -> coffeeEntity.setSugar(1);
            case (33) -> coffeeEntity.setSugar(2);
            case (66) -> coffeeEntity.setSugar(3);
        }
    }

    private void assignDecaf(CoffeeEntity coffeeEntity) {
        if (decafCb.isSelected()) {
            coffeeEntity.setDecaf((byte) 1);
        } else {
            coffeeEntity.setDecaf((byte) 0);
        }
    }

    private void selectCoffee() {
        espresso.setOnMouseClicked(e -> handleImagePress(espresso, latte, iced));
        latte.setOnMouseClicked(e -> handleImagePress(latte, espresso, iced));
        iced.setOnMouseClicked(e -> handleImagePress(iced, latte, espresso));

        smallC.setOnMouseClicked(e -> handleImagePress(smallC, mediumC, largeC));
        mediumC.setOnMouseClicked(e -> handleImagePress(mediumC, smallC, largeC));
        largeC.setOnMouseClicked(e -> handleImagePress(largeC, mediumC, smallC));
    }

    //checks the current status of the slider and returns the corresponding value from the price list
    private double checkSliderStatus() {
        return switch ((int) slider.getValue()) {
            case (0) -> pricelist[6];
            case (33) -> pricelist[7];
            case (66) -> pricelist[8];
            default -> 0;
        };
    }

    private void handleSliderEvent() {
        slider.setOnMouseClicked(e -> price.setText(calculateOrderPrice() + "$"));
    }

    //checks if the checkbox is clicked or not and returns the corresponding price from the price list
    private double checkCafDecaf() {
        if (decafCb.isSelected()) {
            return pricelist[9];
        }
        return 0;
    }

    private void handleChecboxEvent() {
        decafCb.setOnMouseClicked(e -> price.setText(calculateOrderPrice() + "$"));
    }

    private StringBuilder iterateImages() {
        StringBuilder s = new StringBuilder();
        for (ImageView iterate : imageViewList) {
            if (iterate.getOpacity() == 1) {
                s.append(iterate.getId());
            }
        }
        return s;
    }

    //method which gets called everytime someone clicks any image contained in imageViewList
    private double calculateOrderPrice() {
        double sum = 0;
        for (int j = 0; j < iterateImages().length(); j++) {
            sum = sum + pricelist[Integer.parseInt(String.valueOf((Character.getNumericValue(iterateImages().charAt(j)))))];
        }
        return sum + checkSliderStatus() + checkCafDecaf();
    }

    //method called in initialize; Whenever the "buy button" is pressed it assigns multiple values to the orderEntity
    //to do: assign user id of current logged-in user
    private void handleBuyButtonPress() {
        buyBtn.setOnMouseClicked(e -> placeOrder());
    }

    private void checkIfNull() throws Exception {
        OrdersEntity ordersEntity = new OrdersEntity();
        CoffeeEntity coffeeEntity = new CoffeeEntity();
        confirmCoffee(coffeeEntity);
        Field[] fields = coffeeEntity.getClass().getDeclaredFields();
        StringBuilder name = new StringBuilder();
        boolean check = true;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(coffeeEntity) == null) {
                name.append(field.getName()).append(" ");
                check = false;
            }
        }
        if (!check) {
            AlertBox.display("Error", "Please select: " + name);
        } else {
            confirmOrder(ordersEntity);
            coffeeEntity.setOrdersId(ordersEntity.getOrdersId());
            System.out.println(coffeeEntity);
        }
    }

    private void placeOrder() {
        try {
            checkIfNull();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmCoffee(CoffeeEntity coffeeEntity) {
        assignType(coffeeEntity);
        assignSize(coffeeEntity);
        assignSugar(coffeeEntity);
        assignDecaf(coffeeEntity);
    }

    private void confirmOrder(OrdersEntity ordersEntity) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(truncToSec(date).getTime());
        OrdersService ordersService = new OrdersService();
        int i = Integer.parseInt(String.valueOf(ordersService.findID().get(0).getOrdersId() + 1));
        ordersEntity.setName("Order" + i);
        ordersEntity.setQuantity(Integer.parseInt(qtyLabel.getText()));
        ordersEntity.setPrice(calculateOrderPrice());
        ordersEntity.setDate(timestamp);
        ordersEntity.setUserId(getCurrentUserId());
        ordersEntity.setPrice(calculateOrderPrice());
        System.out.println(ordersEntity);
        ordersService.addOrder(ordersEntity);
    }

    private int getCurrentUserId() {
        UserEntity userEntity = new UserEntity();
        Singleton user = Singleton.getInstance(userEntity);
        return user.getUser().getUserId();
    }

    //truncates the time stamp's milliseconds
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
                price.setText(calculateOrderPrice() + "$");

            } else if (first.opacityProperty().doubleValue() == 1 && second.opacityProperty().doubleValue() == 0.5 && last.opacityProperty().doubleValue() == 0.5) {
                first.setOpacity(0.5);
                price.setText(calculateOrderPrice() + "$");
            }
        });
    }
}
