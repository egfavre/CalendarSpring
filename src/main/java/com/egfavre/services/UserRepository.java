package com.egfavre.services;

import com.egfavre.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by user on 6/23/16.
 */
public interface UserRepository extends CrudRepository<User, Integer>{
    public User findByName(String name);
}
