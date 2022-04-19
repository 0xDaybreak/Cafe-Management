package com.entity;

import com.domain.Size;
import com.domain.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coffee", schema = "users")
public class CoffeeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "coffee_id")
    private int coffeeId;
    @Basic
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
    @Basic
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Size size;
    @Basic
    @Column(name = "sugar")
    private Integer sugar;
    @Basic
    @Column(name = "decaf")
    private Byte decaf;
    @Basic
    @Column(name = "orders_id")
    private int ordersId;

    public int getCoffeeId() {
        return coffeeId;
    }

    public void setCoffeeId(int coffeeId) {
        this.coffeeId = coffeeId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Integer getSugar() {
        return sugar;
    }

    public void setSugar(Integer sugar) {
        this.sugar = sugar;
    }

    public Byte getDecaf() {
        return decaf;
    }

    public void setDecaf(Byte decaf) {
        this.decaf = decaf;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoffeeEntity that = (CoffeeEntity) o;
        return coffeeId == that.coffeeId && ordersId == that.ordersId && Objects.equals(type, that.type) && Objects.equals(size, that.size) && Objects.equals(sugar, that.sugar) && Objects.equals(decaf, that.decaf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coffeeId, type, size, sugar, decaf, ordersId);
    }
}
