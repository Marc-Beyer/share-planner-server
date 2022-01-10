package com.vpr.server.repository;

import com.vpr.server.data.UserEvent;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called eventListRepository
// CRUD refers Create, Read, Update, Delete

public interface UserEventRepository extends CrudRepository<UserEvent, Integer> {

}
