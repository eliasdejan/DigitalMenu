package com.eliasdejan.digial_menu;

import com.eliasdejan.digial_menu.model.Order;
import com.eliasdejan.digial_menu.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class OrderRepositoryTests {

    @Autowired private OrderRepository orderRepository;

    @Test
    public void testAddNew(){
        Order order = new Order();
        order.setCreationTime(LocalDateTime.now());
        order.setFinishedTime(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        Assertions.assertThat(savedOrder).isNotNull();
        Assertions.assertThat(savedOrder.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<Order> orders = orderRepository.findAll();
        Assertions.assertThat(orders).hasSizeGreaterThan(0);

        for (Order order : orders){
            System.out.println(order);
        }
    }

    @Test
    public void testUpdate(){
        Integer orderId = 2;
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.get();

        var currentTime = LocalDateTime.now();
        order.setFinishedTime(currentTime);
        orderRepository.save(order);

        Order updateddOrder = orderRepository.findById(orderId).get();
        Assertions.assertThat(updateddOrder.getFinishedTime()).isEqualTo(currentTime);
    }

    @Test
    public void testGet(){
        Integer orderId = 2;
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Assertions.assertThat(optionalOrder).isPresent();
        System.out.println(optionalOrder.get());
    }

    @Test
    public void testDelete(){
        Integer orderId = 2;
        orderRepository.deleteById(orderId);

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Assertions.assertThat(optionalOrder).isNotPresent();
    }

}
