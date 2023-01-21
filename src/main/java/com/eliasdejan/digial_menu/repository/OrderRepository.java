package com.eliasdejan.digial_menu.repository;

import com.eliasdejan.digial_menu.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.finishedTime IS NOT NULL AND date(o.creationTime) = curdate()")
    ArrayList<Order> findAllFromTodayFinished();

    @Query("SELECT o FROM Order o WHERE o.finishedTime IS NULL AND date(o.creationTime) = curdate()")
    ArrayList<Order> findAllFromTodayNotFinished();
}
