package com.mam.vpr.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

import com.mam.vpr.accessingdatamysql.EventList;

// This will be AUTO IMPLEMENTED by Spring into a Bean called eventListRepository
// CRUD refers Create, Read, Update, Delete

public interface EventListRepository extends CrudRepository<EventList, Integer> {
}
