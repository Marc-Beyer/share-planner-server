package com.vpr.server;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called eventRepository
// CRUD refers Create, Read, Update, Delete

public interface EventRepository extends CrudRepository<Event, Integer> {
    @Query(value = "SELECT e.id AS eid, e.name AS ename, u.id AS uid, u.forename, u.name AS uname " +
            "FROM event e " +
            "INNER JOIN user_event ue " +
            "ON e.id = ue.event_id " +
            "INNER JOIN user u " +
            "ON ue.user_id = u.id",
            nativeQuery = true)
    List<Object> test();
}