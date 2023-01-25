package com.eliasdejan.digial_menu;

import com.eliasdejan.digial_menu.model.MenuItem;
import com.eliasdejan.digial_menu.model.MenuItemOrder;
import com.eliasdejan.digial_menu.model.MenuItemType;
import com.eliasdejan.digial_menu.model.Order;
import com.eliasdejan.digial_menu.repository.MenuItemRepository;
import com.eliasdejan.digial_menu.repository.MenuItemTypeRepository;
import com.eliasdejan.digial_menu.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class MenuItemRepositoryTests {

    @Autowired private MenuItemRepository menuItemRepository;

    @Autowired private MenuItemTypeRepository menuItemTypeRepository;

    @Test
    public void testAddNew(){
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Test name");
        menuItem.setDescription("Test description");
        menuItem.setPrice(Double.parseDouble("123"));
        menuItem.setImagePath("");
        Integer menuTypeId = 5;
        Optional<MenuItemType> optionalMenuItemType = menuItemTypeRepository.findById(menuTypeId);
        MenuItemType menuItemType = optionalMenuItemType.get();
        menuItem.setMenuItemType(menuItemType);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        Assertions.assertThat(savedMenuItem).isNotNull();
        Assertions.assertThat(savedMenuItem.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<MenuItem> menuItems = menuItemRepository.findAll();
        Assertions.assertThat(menuItems).hasSizeGreaterThan(0);

        for (MenuItem menuItem: menuItems){
            System.out.println(menuItem);
        }
    }

    @Test
    public void testUpdate(){
        Integer menuId = 1;
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuId);
        MenuItem menuItem = optionalMenuItem.get();
        menuItem.setName("Updated menu");
        menuItemRepository.save(menuItem);

        MenuItem updatedMenuItem = menuItemRepository.findById(menuId).get();
        Assertions.assertThat(updatedMenuItem.getName()).isEqualTo("Updated menu");
    }

    @Test
    public void testGet(){
        Integer menuId = 1;
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuId);
        Assertions.assertThat(optionalMenuItem).isPresent();
        System.out.println(optionalMenuItem.get());
    }

    @Test
    public void testDelete(){
        Integer menuID = 1;
        menuItemRepository.deleteById(menuID);

        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuID);
        Assertions.assertThat(optionalMenuItem).isNotPresent();
    }
}
