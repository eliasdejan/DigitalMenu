package com.eliasdejan.digial_menu.model;

import javax.persistence.*;

@Entity
@Table(name = "menu_items_orders")
public class MenuItemOrder {
    @EmbeddedId
    MenuItemOrderKey id = new MenuItemOrderKey();

    @ManyToOne
    @MapsId("menuItemId")
    @JoinColumn(name = "menu_item_id")
    MenuItem menuItem;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    Order order;

    int quantity;

    public MenuItemOrderKey getId() {
        return id;
    }

    public void setId(MenuItemOrderKey id) {
        this.id = id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
