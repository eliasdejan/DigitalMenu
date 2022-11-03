package com.eliasdejan.digial_menu.repository;

import com.eliasdejan.digial_menu.model.MenuItemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemTypeRepository extends JpaRepository<MenuItemType, Long> {
}
