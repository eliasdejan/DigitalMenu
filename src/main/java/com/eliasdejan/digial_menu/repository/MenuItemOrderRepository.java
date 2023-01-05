package com.eliasdejan.digial_menu.repository;

import com.eliasdejan.digial_menu.model.MenuItemOrder;
import com.eliasdejan.digial_menu.model.MenuItemOrderKey;
import org.springframework.data.repository.CrudRepository;

public interface MenuItemOrderRepository extends CrudRepository<MenuItemOrder, MenuItemOrderKey> {
}
