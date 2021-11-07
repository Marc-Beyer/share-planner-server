package com.mam.vpr.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

import com.mam.vpr.accessingdatamysql.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

}