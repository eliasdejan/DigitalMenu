package com.eliasdejan.digial_menu;

import com.eliasdejan.digial_menu.model.MenuItemType;
import com.eliasdejan.digial_menu.repository.MenuItemTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class MenuItemTypesRepositoryTests {

    @Autowired private MenuItemTypeRepository menuItemTypeRepository;

    @Test
    public void testAddNew(){
        MenuItemType menuItemType = new MenuItemType();
        menuItemType.setName("Test");

        MenuItemType savedMenuItemType = menuItemTypeRepository.save(menuItemType);

        Assertions.assertThat(savedMenuItemType).isNotNull();
        Assertions.assertThat(savedMenuItemType.getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdate(){
        Integer menuItemTypeId = 8;
        Optional<MenuItemType> optionalMenuItemType = menuItemTypeRepository.findById(menuItemTypeId);
        MenuItemType menuItemType = optionalMenuItemType.get();
        menuItemType.setName("updated menuItemType");

        MenuItemType updatedMenuItemType = menuItemTypeRepository.findById(menuItemTypeId).get();
        Assertions.assertThat(updatedMenuItemType.getName()).isEqualTo("updated menuItemType");
    }

    @Test
    public void testGet(){
        Integer menuItemTypeId = 8;
        Optional<MenuItemType> optionalMenuItemType = menuItemTypeRepository.findById(menuItemTypeId);
        Assertions.assertThat(optionalMenuItemType).isPresent();
        System.out.println(optionalMenuItemType.get());
    }

    @Test
    public void testDelete(){
        Integer menuItemTypeId = 8;
        menuItemTypeRepository.deleteById(menuItemTypeId);

        Optional<MenuItemType> optionalMenuItemType = menuItemTypeRepository.findById(menuItemTypeId);
        Assertions.assertThat(optionalMenuItemType).isNotPresent();
    }
}
