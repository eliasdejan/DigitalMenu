package com.eliasdejan.digial_menu.repository;

import com.eliasdejan.digial_menu.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
