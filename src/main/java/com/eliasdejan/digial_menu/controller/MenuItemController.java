package com.eliasdejan.digial_menu.controller;

import com.eliasdejan.digial_menu.model.CustomUserDetails;
import com.eliasdejan.digial_menu.model.MenuItem;
import com.eliasdejan.digial_menu.repository.MenuItemRepository;
import com.eliasdejan.digial_menu.repository.MenuItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showIndex(Model model, RedirectAttributes redirectAttributes) {

        CustomUserDetails loggedInUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!loggedInUser.getIsAdmin()){
            redirectAttributes.addFlashAttribute("message", "You are not allowed to view menu items!");
            return "redirect:/orders";
        }

        model.addAttribute("message", model.asMap().get("message"));
        model.addAttribute("menuItems", menuItemRepository.findAll());
        model.addAttribute("MenuItem", new MenuItem());
        model.addAttribute("menuItemTypes", menuItemTypeRepository.findAll());

        return "menu-items/index";
    }

    @PostMapping("/add")
    public String addMenuItem(@Valid MenuItem menuItem, BindingResult result, Model model,  RedirectAttributes redirectAttributes) {

        CustomUserDetails loggedInUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!loggedInUser.getIsAdmin()){
            redirectAttributes.addFlashAttribute("message", "You are not allowed to add menu items!");
            return "redirect:/orders";
        }

        if (result.hasErrors()) {return "menu-items";}
        menuItemRepository.save(menuItem);
        redirectAttributes.addFlashAttribute("message", "Item was added!");
        return "redirect:/menu-items";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        CustomUserDetails loggedInUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!loggedInUser.getIsAdmin()){
            redirectAttributes.addFlashAttribute("message", "You are not allowed to edit menu items!");
            return "redirect:/orders";
        }

        model.addAttribute("message", model.asMap().get("message"));
        MenuItem menuItem = menuItemRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu item Id:" + id));
        model.addAttribute("menuItem", menuItem);

        model.addAttribute("message", model.asMap().get("message"));
        model.addAttribute("menuItemTypes", menuItemTypeRepository.findAll());
        return "/menu-items/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMenuItem(@PathVariable("id") long id, @Valid MenuItem menuItem, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        CustomUserDetails loggedInUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!loggedInUser.getIsAdmin()){
            redirectAttributes.addFlashAttribute("message", "You are not allowed to edit menu items!");
            return "redirect:/orders";
        }

        if (result.hasErrors()) {
            menuItem.setId((int) id);
            return "/menu-items/edit/"+id;
        }

        menuItemRepository.save(menuItem);
        redirectAttributes.addFlashAttribute("message", "Item was edited!");
        model.addAttribute("menuItems", menuItemRepository.findAll());
        return "redirect:/menu-items";
    }

    @GetMapping("/delete/{id}")
    public String deleteMenuItem(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        CustomUserDetails loggedInUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!loggedInUser.getIsAdmin()){
            redirectAttributes.addFlashAttribute("message", "You are not allowed to delete menu items!");
            return "redirect:/orders";
        }

        MenuItem menuItem = menuItemRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu item Id:" + id));
        menuItemRepository.delete(menuItem);
        redirectAttributes.addFlashAttribute("message", "Item was deleted!");
        return "redirect:/menu-items";
    }

}
