package com.eliasdejan.digial_menu.repository;

import com.eliasdejan.digial_menu.model.MenuItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MenuItemRepository extends CrudRepository<MenuItem, Integer> {

    @Query("SELECT mi from MenuItem mi where mi.menuItemType.id = ?1")
    ArrayList<MenuItem> findByMenuItemTypeId(int menuItemTypeId);
}
