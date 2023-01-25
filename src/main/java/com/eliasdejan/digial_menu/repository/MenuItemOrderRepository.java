package com.eliasdejan.digial_menu.repository;

import com.eliasdejan.digial_menu.model.MenuItemOrder;
import com.eliasdejan.digial_menu.model.MenuItemOrderKey;
import com.eliasdejan.digial_menu.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MenuItemOrderRepository extends CrudRepository<MenuItemOrder, MenuItemOrderKey> {
    Optional<MenuItemOrder> findById(MenuItemOrderKey id);

}
