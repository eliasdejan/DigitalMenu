package com.eliasdejan.digial_menu.controller;

import javax.validation.Valid;

import com.eliasdejan.digial_menu.model.MenuItemType;
import com.eliasdejan.digial_menu.repository.MenuItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu-item-types")
public class MenuItemTypeController {

    private final MenuItemTypeRepository menuItemTypeRepository;

    @Autowired
    public MenuItemTypeController(MenuItemTypeRepository menuItemTypeRepository){
        this.menuItemTypeRepository = menuItemTypeRepository;
    }

    @GetMapping("")
    public String showIndex(Model model) {
        model.addAttribute("menuItemTypes", menuItemTypeRepository.findAll());
        model.addAttribute("MenuItemType", new MenuItemType());
        return "menu-item-types/index";
    }

    @PostMapping("/add")
    public String addMenuItemType(@Valid MenuItemType menuItemType, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "menu-item-types/index";
        }

        menuItemTypeRepository.save(menuItemType);
        return "redirect:/menu-item-types";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        MenuItemType menuItemType = menuItemTypeRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + id));

        model.addAttribute("menuItemType", menuItemType);
        return "/menu-item-types/edit";
    }

    @PostMapping("edit/{id}")
    public String updateMenuItemType(@PathVariable("id") long id, @Valid MenuItemType menuItemType, BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            menuItemType.setId((int) id);
            return "/menu-item-types/edit/"+id;
        }
        menuItemTypeRepository.save(menuItemType);
        return "redirect:/menu-item-types";
    }

    @GetMapping("delete/{id}")
    public String deleteMenuItemType(@PathVariable("id") long id, Model model) {
        MenuItemType menuItemType = menuItemTypeRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid type Id:" + id));
        menuItemTypeRepository.delete(menuItemType);
        return "redirect:/menu-item-types";
    }
}
