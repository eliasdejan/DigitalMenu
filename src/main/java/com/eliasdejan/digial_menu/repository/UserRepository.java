package com.eliasdejan.digial_menu.repository;

import com.eliasdejan.digial_menu.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    long count();
}
