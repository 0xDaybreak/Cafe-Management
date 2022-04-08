package com.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "users")
public class OrdersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "ordername")
    private String ordername;
    @Basic
    @Column(name = "orderquantity")
    private Integer orderquantity;
    @Basic
    @Column(name = "orderprice")
    private Double orderprice;
    @Basic
    @Column(name = "orderdate")
    private String orderdate;
    @Basic
    @Column(name = "user_id")
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public Integer getOrderquantity() {
        return orderquantity;
    }

    public void setOrderquantity(Integer orderquantity) {
        this.orderquantity = orderquantity;
    }

    public Double getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(Double orderprice) {
        this.orderprice = orderprice;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return id == that.id && userId == that.userId && Objects.equals(ordername, that.ordername) && Objects.equals(orderquantity, that.orderquantity) && Objects.equals(orderprice, that.orderprice) && Objects.equals(orderdate, that.orderdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ordername, orderquantity, orderprice, orderdate, userId);
    }
}
