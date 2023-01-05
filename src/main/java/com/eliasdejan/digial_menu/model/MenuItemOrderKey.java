package com.eliasdejan.digial_menu.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MenuItemOrderKey implements Serializable {
    @Column(name = "menu_item_id")
    Integer menuItemId;

    @Column(name = "order_id")
    Integer orderId;

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
