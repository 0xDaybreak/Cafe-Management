package com.controller;

import com.entity.OrdersEntity;
import com.entity.UserEntity;
import com.misc.OrdersComparator;
import com.misc.Singleton;
import com.service.OrdersService;
import com.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.criterion.Order;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class OrdersController implements Initializable {
    @FXML
    private TableView<OrdersEntity> tvOrders;

    @FXML
    private Label highestLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTable();
        obtainHighestPrice();

    }

    private ObservableList<OrdersEntity> getOrdersForUsers() {
        ObservableList<OrdersEntity> ol = FXCollections.observableArrayList();
        UserEntity userEntity = new UserEntity();
        Singleton user = Singleton.getInstance(userEntity);
        Integer id = user.getUser().getUserId();
        OrdersService ordersService = new OrdersService();
        ObservableList<OrdersEntity> userList = FXCollections.observableList(ordersService.getAllOrders());
        for(OrdersEntity ordersEntity: userList) {
            if(ordersEntity.getUserId() == id) {
                ol.add(ordersEntity);
            }
        }
        return ol;
    }

    private void createTable() {

        TableColumn<OrdersEntity, String> tcName = new TableColumn<>();
        tcName.setText("Order Name");
        tcName.setMinWidth(100);
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<OrdersEntity, String> tcQty = new TableColumn<>();
        tcQty.setText("Quantity");
        tcQty.setMinWidth(100);
        tcQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<OrdersEntity, String> tcPrice = new TableColumn<>();
        tcPrice.setText("Price($)");
        tcPrice.setMinWidth(100);
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<OrdersEntity, String> tcDate = new TableColumn<>();
        tcDate.setText("Date");
        tcDate.setMinWidth(130);
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tvOrders.getColumns().addAll(tcName, tcQty, tcPrice, tcDate);
        tvOrders.setItems(getOrdersForUsers());
    }

    private void obtainHighestPrice() {
        List<OrdersEntity> listOfOrders = new ArrayList<>(getOrdersForUsers());
        OrdersComparator ordersComparator = new OrdersComparator();
        Collections.sort(listOfOrders, ordersComparator);
        Collections.reverse(listOfOrders);
        listOfOrders.forEach(System.out::println);
        highestLbl.setText((listOfOrders.get(0).getPrice()) + " $");

    }


    }

