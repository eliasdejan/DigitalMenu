package com.eliasdejan.digial_menu.controller;

import com.eliasdejan.digial_menu.model.MenuItem;
import com.eliasdejan.digial_menu.model.MenuItemType;
import com.eliasdejan.digial_menu.model.User;
import com.eliasdejan.digial_menu.repository.MenuItemRepository;
import com.eliasdejan.digial_menu.repository.MenuItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemTypeRepository menuItemTypeRepository;

    @Autowired
    public MenuItemController(MenuItemRepository menuItemRepository, MenuItemTypeRepository menuItemTypeRepository) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemTypeRepository = menuItemTypeRepository;
    }

    @GetMapping("")
    public String showUpdateForm(Model model) {
        model.addAttribute("menuItems", menuItemRepository.findAll());
        model.addAttribute("MenuItem", new MenuItem());
        model.addAttribute("menuItemTypes", menuItemTypeRepository.findAll());

        return "menu-items/index";
    }

    @PostMapping("/add")
    public String addMenuItem(@Valid MenuItem menuItem, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "menu-items";
        }

        menuItemRepository.save(menuItem);
        return "redirect:/menu-items";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        MenuItem menuItem = menuItemRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu item Id:" + id));
        model.addAttribute("menuItem", menuItem);
        model.addAttribute("menuItemTypes", menuItemTypeRepository.findAll());
        return "/menu-items/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMenuItem(@PathVariable("id") long id, @Valid MenuItem menuItem, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            menuItem.setId((int) id);
            return "/menu-items/edit/"+id;
        }

        menuItemRepository.save(menuItem);
        model.addAttribute("menuItems", menuItemRepository.findAll());
        return "redirect:/menu-items";
    }

    @GetMapping("/delete/{id}")
    public String deleteMenuItem(@PathVariable("id") long id, Model model) {
        MenuItem menuItem = menuItemRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu item Id:" + id));
        menuItemRepository.delete(menuItem);
        return "redirect:/menu-items";
    }

}
