package com.mam.vpr.accessingdatamysql;

import org.springframework.data.repository.CrudRepository;

import com.mam.vpr.accessingdatamysql.Event;

// This will be AUTO IMPLEMENTED by Spring into a Bean called eventRepository
// CRUD refers Create, Read, Update, Delete

public interface EventRepository extends CrudRepository<Event, Integer> {

}