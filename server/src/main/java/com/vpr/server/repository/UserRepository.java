package com.vpr.server.repository;

import com.vpr.server.data.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT u.id, u.name, u.forename " +
            "FROM user u",
            nativeQuery = true)
    Object[] findAllUsernames();

    User findById(long id);

    User findByLogin(String login);

    User findByLoginAndPassword(String login, byte[] password);

    void deleteById(long id);

    User findByToken(String token);
}