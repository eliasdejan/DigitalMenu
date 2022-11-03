package com.eliasdejan.digial_menu.repository;

import com.eliasdejan.digial_menu.model.MenuItem;
import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItem, Integer> {
}
