package com.vpr.server;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT u.id, u.name, u.forename " +
            "FROM user u",
            nativeQuery = true)
    Object[] findAllUsernames();

    com.vpr.server.User findById(long id);
}