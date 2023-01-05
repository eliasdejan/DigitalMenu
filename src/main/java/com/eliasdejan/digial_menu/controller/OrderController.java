package com.eliasdejan.digial_menu.controller;

import com.eliasdejan.digial_menu.model.MenuItem;
import com.eliasdejan.digial_menu.model.MenuItemOrder;
import com.eliasdejan.digial_menu.model.Order;
import com.eliasdejan.digial_menu.repository.MenuItemOrderRepository;
import com.eliasdejan.digial_menu.repository.MenuItemRepository;
import com.eliasdejan.digial_menu.repository.MenuItemTypeRepository;
import com.eliasdejan.digial_menu.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
        return "orders/menu";
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders/index";
    }

    @PostMapping("/orders/add")
    public ResponseEntity<String> addOrder(@RequestBody Set<MenuItemOrder> menuItemOrders) {

        Order order = new Order();
        order.setCreationTime(LocalDateTime.now());
        order.setFinishedTime(null);
        order = orderRepository.save(order);

        for (MenuItemOrder menuItemOrder : menuItemOrders) {
            menuItemOrder.setOrder(order);
            menuItemOrderRepository.save(menuItemOrder);
        }

        return ResponseEntity.ok("Order added");
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
