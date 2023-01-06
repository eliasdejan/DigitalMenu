package com.eliasdejan.digial_menu.controller;

import com.eliasdejan.digial_menu.model.MenuItem;
import com.eliasdejan.digial_menu.model.MenuItemOrder;
import com.eliasdejan.digial_menu.model.Order;
import com.eliasdejan.digial_menu.repository.MenuItemOrderRepository;
import com.eliasdejan.digial_menu.repository.MenuItemRepository;
import com.eliasdejan.digial_menu.repository.MenuItemTypeRepository;
import com.eliasdejan.digial_menu.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
public class OrderController {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemTypeRepository menuItemTypeRepository;
    private final OrderRepository orderRepository;

    private final MenuItemOrderRepository menuItemOrderRepository;

    @Autowired
    public OrderController(MenuItemRepository menuItemRepository, MenuItemTypeRepository menuItemTypeRepository, OrderRepository orderRepository, MenuItemOrderRepository menuItemOrderRepository) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemTypeRepository = menuItemTypeRepository;
        this.orderRepository = orderRepository;
        this.menuItemOrderRepository = menuItemOrderRepository;
    }

    @GetMapping("")
    public String showMenu(Model model) {
        model.addAttribute("menuItems", menuItemRepository.findAll());
        model.addAttribute("MenuItem", new MenuItem());
        model.addAttribute("menuItemTypes", menuItemTypeRepository.findAll());
        model.addAttribute("message", model.asMap().get("message"));
        return "orders/menu";
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        model.addAttribute("message", model.asMap().get("message"));
        model.addAttribute("orders", orderRepository.findAll());
        return "orders/index";
    }

    @PostMapping("/orders/add")
    public ResponseEntity<String> addOrder(@RequestBody Set<MenuItemOrder> menuItemOrders, RedirectAttributes redirectAttributes) throws JsonProcessingException {

        Order order = new Order();
        order.setCreationTime(LocalDateTime.now());
        order.setFinishedTime(null);
        order = orderRepository.save(order);

        for (MenuItemOrder menuItemOrder : menuItemOrders) {
            menuItemOrder.setOrder(order);
            menuItemOrderRepository.save(menuItemOrder);
        }

        Map<String, Object> map = new HashMap<>();

        map.put("success", true);
        map.put("id", order.getId());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        // add success = true to answer
        return ResponseEntity.ok(json);
    }

    @PostMapping("/orders/finish/{id}")
    public String finishOrder(@PathVariable("id") long id, @Valid Order orderWithId, BindingResult result,
                              Model model) {
        Optional<Order> order = orderRepository.findById(orderWithId.getId());
        if(order.isPresent()) {
            order.get().setFinishedTime(LocalDateTime.now());
            orderRepository.save(order.get());
        }
        return "redirect:/orders";
    }
}
