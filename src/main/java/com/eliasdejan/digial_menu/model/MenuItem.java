package com.eliasdejan.digial_menu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 200)
    private String description;


    @Column(nullable = false, precision=5, scale=2)
    private double price;

    @Column(name = "image", nullable = false)
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_item_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private MenuItemType menuItemType;

    @OneToMany(mappedBy = "menuItem")
    Set<MenuItemOrder> menuItemOrders;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MenuItemType getMenuItemType() {
        return menuItemType;
    }

    public void setMenuItemType(MenuItemType menuItemType) {
        this.menuItemType = menuItemType;
    }

    public Set<MenuItemOrder> getMenuItemOrders() {
        return menuItemOrders;
    }

    public void setMenuItemOrders(Set<MenuItemOrder> menuItemOrders) {
        this.menuItemOrders = menuItemOrders;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
