package com.vpr.server;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called eventListRepository
// CRUD refers Create, Read, Update, Delete

public interface UserEventRepository extends CrudRepository<UserEvent, Integer> {

}
