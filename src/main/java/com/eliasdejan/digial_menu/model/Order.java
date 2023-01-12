package com.eliasdejan.digial_menu.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime creationTime;

    @Column(nullable = true)
    private LocalDateTime finishedTime;

    @OneToMany(mappedBy = "order")
    Set<MenuItemOrder> menuItemOrders;

    public double getTotalPrice() {
        double totalPrice = 0;
        for (MenuItemOrder item : menuItemOrders) {
            totalPrice += item.getQuantity() * item.getMenuItem().getPrice();
        }
        return totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(LocalDateTime finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Set<MenuItemOrder> getMenuItemOrders() {
        return menuItemOrders;
    }

    public void setMenuItemOrders(Set<MenuItemOrder> menuItemOrders) {
        this.menuItemOrders = menuItemOrders;
    }
}
